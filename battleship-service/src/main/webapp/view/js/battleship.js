/***** logic. js**************/



function goToRegister() {
    $("#content_id").load("/html/register");

};

function goToPlacingBoard() {
    $("#content_id").load("/html/placing", function () {
        loadPlacingShipsProperites();
    });
};

function sendRegisterRequest() {
    $.ajax({
        url: '/service/register',
        type: 'GET',
        data: $("#form").serialize(),
        success: function (data) {
            //called when successful
            goToPlacingBoard();
        },
        error: function (e) {
            alert("Permission denied");
        }
    });
};

/*****end logic. js**************/


/***** Variables****************************************/
var boardSize = 10;
var opponentBoardId = '#id_opponentBoard';
var myBoardId = '#id_myBoard';
/**** placing ships variables****************************/
/**variable helps with placing ship on board*****/
var myBoardMap = {};
var opponentsBoardMap = {};

var thisPlayerStartsGameFirst=false;

//this method is invkoed when page is changing from registration to placing ships
function loadPlacingShipsProperites() {
    sendRequestforShipList();
    loadHtmlTableAsGameBoard(boardSize, myBoardId);
    setActionForPlacingShipOnPlayerBoard();
    setListenerOnShipsSelectLists();
}


//this metoh create labels for board.
//it is neccesary when we have different size board
function getLetterFromAlphabet(position) {
    if (position == 0) {
        return "";
    }
    return String.fromCharCode(64 + position);
};

function prepareLettersLabel(position) {
    var tableHead = '<th >' + getLetterFromAlphabet(position) + '</th>';
    return tableHead;
};

function loadHtmlTableAsGameBoard(size, boardId) {
    var htmlTable = $(boardId);
    var lettersLabels = htmlTable.find("tr#id_lettersLabel");
    var tableBody = htmlTable.find("tbody#id_tableBody");
    for (i = 1; i <= size; i++) {
        if (i - 1 == 0) {
            lettersLabels.append(prepareLettersLabel(i - 1));
        }
        lettersLabels.append(prepareLettersLabel(i));
        var row = '<tr>';
        for (j = 1; j <= size; j++) {
            if (j == 1) {
                row += '<th>' + i * j + '</th>';
            }
            row += '<td id="' + ((i - 1) * boardSize + j) + '" class="cell_empty"><div class="cell_content"><span class="point"></span>&nbsp;</div></td>';
        }
        row += '</tr>';
        tableBody.append(row);
    }
};
/***********This metod allow us to lock board. Not used yet but tested*************/
//it can be used when we will wait for oponnent turn
function exitGame() {
    lockTable(opponentBoardId);
    lockTable(myBoardId);
}

function lockTable(boardId) {
    console.log("loking " + boardId);
    $(boardId).attr('class', 'player-board wait');
};

function unLockTable(boardId) {
    console.log("unloking " + boardId);
    $(boardId).attr('class', 'player-board');
};




/**************************placing ships*******************************/

var DIRECTION_ENUM = {
    VERTICAL: "VERTICAL"
    , HORIZONTAL: "HORIZONTAL"
};
var PLACING_SHIP_STATUS_ENUM = {
    TRY_AGAIN: "TRY_AGAIN"
    , SUCCESS: "SUCCESS"
};
var PLACING_STATUS_ENUM = {
    INVALID_MOVEMENT: "INVALID_MOVEMENT"
    , TRY_AGAIN: "TRY_AGAIN"
    , SUCCESS: "SUCCESS"
    , WON: "WON"
};
//default settings for ships preview when we click on ship item in list
var direction = DIRECTION_ENUM.HORIZONTAL;
//some ids
var shipTemplateBoardId = '#id_shipTemplate';
var shipsSelectListsId = '#shipsSelectLists_id';
var orientationBtnId = '#orientationBtn_id';
var column1Id = '#column1_id';
var shipSize = 0;
//it is used when we send request to place ship on board
var shipType = "";
//when ship is placed it is need for not alowwing puting it again
var placingShipCondition = true;
// clearing  board for ship(to place) preview
function clearShipToPlacePreview() {
    var opponentBoard = $(shipTemplateBoardId);
    var tableBody = opponentBoard.find("tbody#id_tableBody");
    tableBody.empty();
};
// creating ship preview
function createShipPreview(shipSize) {
    var opponentBoard = $(shipTemplateBoardId);
    var tableBody = opponentBoard.find("tbody#id_tableBody");
    for (i = 1; i <= shipSize; i++) {
        var row = "";
        if ($(orientationBtnId).text() == DIRECTION_ENUM.VERTICAL) {
            row = '<tr>';
            row += '<td  class="cell_ship"></td>';
            row += '</tr>';
        }
        else {
            row += '<td  class="cell_ship"></td>';
        }
        tableBody.append(row);
    }
}
//this method is used by button to change ship orientation
function changeShipOrientation() {
    if ($(orientationBtnId).text().toString() == DIRECTION_ENUM.VERTICAL) {
        direction = "" + $(orientationBtnId).text(DIRECTION_ENUM.HORIZONTAL).toString();
    }
    else {
        direction = "" + $(orientationBtnId).text(DIRECTION_ENUM.VERTICAL).toString();
    }
    shipSize = $(shipsSelectListsId + " option:selected").attr('shipSize');
    clearShipToPlacePreview();
    createShipPreview(shipSize);
}

//it allows to place ship on board.
//this action will be later removed becouse after placing ships it isn't possible to prepare action on board
function setActionForPlacingShipOnPlayerBoard() {
    console.log("setActionForPlacingShipOnPlayerBoard");
    $(myBoardId + ' td.cell_empty').each(function (index, elem) {
        $(this).on("click", function () {
            if (placingShipCondition == true) {
                var position = parseInt($(this).attr('id'));
                sendRequestforPlacingShip(shipType, position, direction);
            }
        });
    });
};
//after placing ship it must be removed from list
function removeShipFromSelectLists() {
    $(shipsSelectListsId + " option:selected").remove();
    clearShipToPlacePreview();
    placingShipCondition = false;
}
//this method is used when we click on board to place ship there
//it uses global variable like shipSize
function putShipOnBoard(position) {
    if (placingShipCondition == true) {
        var htmlTable = $(myBoardId);
        var dir = $(orientationBtnId).text();
        for (i = 1; i <= shipSize; i = i + 1) {
            var tableCell = htmlTable.find('#' + position);
            myBoardMap[position] = true;
            console.log("putting ship at: " + position);
            tableCell.attr('class', 'cell_ship');
            tableCell.off('click');
            if (dir == DIRECTION_ENUM.HORIZONTAL) {
                position = position + 1;
            }
            else {
                position = position + boardSize;
            }
        }
    }
}

//this request is send for ship list
//it will be better when we can get ship name and it size
function sendRequestforShipList() {
    $.ajax({
        method: "GET"
        , dataType: 'json'
        , url: "/service/place"
        , data: {
            "type": "non"
            , "fieldNumber": 1
            , direction: "HORIZONTAL"
        }
        , success: function (data) {
            addRequestedShipsToSelectOptionList(data);
        }
    });
};
//listener for select list option - action needed when w click for ex. one mast to see preview
function setListenerOnShipsSelectLists() {
    $(shipsSelectListsId).change(function () {
        console.log("shipsSelectListsId changed");
        shipSize = $(shipsSelectListsId + " option:selected").attr('shipSize');
        shipType = $(shipsSelectListsId + " option:selected").text();
        clearShipToPlacePreview();
        createShipPreview(shipSize);
        placingShipCondition = true;
    });
};
//method for adding ships wihich was returend in ajax response
function addRequestedShipsToSelectOptionList(shipsToPlace) {
    for (var i in shipsToPlace.availableShips) {
        var size = parseInt(i) + 1;
        $(shipsSelectListsId).append('<option shipSize="' + size + '">' + shipsToPlace.availableShips[i] + '</option>');
    }
};

var readyToPlayInterval;

var oponnentTurnsUpdateInterval;

//request is send to check it is possiblity to place ship on board
function sendRequestforPlacingShip(shipType, fieldNumber) {
    $.ajax({
        method: "GET"
        , dataType: 'json'
        , url: "/service/place"
        , data: {
            "type": shipType
            , "fieldNumber": fieldNumber
            , direction: $(orientationBtnId).text().toString()
        }
        , success: function (data) {
            if (data.status == PLACING_SHIP_STATUS_ENUM.SUCCESS) {
                putShipOnBoard(fieldNumber);
                removeShipFromSelectLists();
                var length = $(shipsSelectListsId + ' > option').length;
                if (length == 0) {
                    $('#myPleaseWait').modal('show');

                    readyToPlayInterval=setInterval(isOpponentReady, 1000);
//                    isOpponentReady();
                }
            }
        }
    });
};
//this method remove select option which was used to select ship and place it on board
function removeSelectOptionList() {
    $(column1Id).empty();
};

//after ship placing board can't handle any click action
function removeActionsForPlacingShipFromPlayerBoard() {
    console.log("remove click actions after placing ships");
    $(myBoardId + ' td.cell_empty').each(function (index, elem) {
        $(this).off('click');
    });
};
//when all ships are placed are components shold be removed and additonal board should show
function goToPlayAfterPlacingShips() {
    $.get("/html/play", function (data) {
        $("#column1_id").append(data);
        loadOpponentBoard();
        removeActionsForPlacingShipFromPlayerBoard();
        if(thisPlayerStartsGameFirst==false){
         lockTable(opponentBoardId);
        }
    });
};
//method for checing who is winner. it depends only on returning status
function isWinner(data) {
    if (data.status == PLACING_STATUS_ENUM.WON) {
        alert('The winner is: ' + data.winner.name + ' ' + data.winner.surname);
    }
}
//this method read oponnent moves and add it to opponentsBoardMap
function prepareOpponentShootsMap(response) {
    var items = response.myDamages;
    for (var i in items) {
        var index = parseInt(i);
        opponentsBoardMap[index] = Boolean(items[index]);
    }
};
//this method send shoot request after click on opponent board table cell
function sentShotRequest(tableCell) {
    var position = $(tableCell).attr('id');
    $.ajax({
        method: "GET"
        , dataType: 'json'
        , url: "/service/shoot"
        , data: {
            "fieldNumber": position
        }
        , success: function (data) {
            if (data.status == PLACING_STATUS_ENUM.SUCCESS || data.status == PLACING_STATUS_ENUM.WON) {
                tableCell.attr('class', 'cell_hit');
                tableCell.off('click');
            }
            else if (data.status == PLACING_STATUS_ENUM.TRY_AGAIN) {
                tableCell.attr('class', 'cell_missed');
            }
//            prepareOpponentShootsMap(data);
//            placeOpponentsShootsOnBoard();
            isWinner(data);
        }
    });
};
// this method place returned by ajax oponnent shoots and place it on player board
function placeOpponentsShootsOnBoard() {
    var htmlTable = $(myBoardId);
    for (var i in opponentsBoardMap) {
        var tableCell = htmlTable.find('#' + i);
        var position = $(tableCell).attr('id');
        console.log("Shot on position: %s", i);
        //sending request using ajax to service
        if (true == Boolean(opponentsBoardMap[i])) {
            tableCell.attr('class', 'cell_hit');
        }
        else {
            tableCell.attr('class', 'cell_missed');
        }
    }
};
// this metho load oponent board. it is invoked after placing all ships
function loadOpponentBoard() {
    console.log("loading opponent board");
    loadHtmlTableAsGameBoard(boardSize, opponentBoardId);
    $(opponentBoardId+' td.cell_empty').each(function (index, elem) {
        $(this).on("click", function () {
            sentShotRequest($(this));
        });
    });
};






//this method will be used to checkin it is possobolity to play
// it will be nice to get also who is turn next and last ship placed
//example i am asking about turns and also get information it opponents ends shooting
function isOpponentReady() {
    $.ajax({
        method: "GET"
        , dataType: 'json'
        , url: "/service/prepare", //       data: { },
        success: function (data) {
            if (data.readyToPlay) {
                clearInterval(readyToPlayInterval);
                removeSelectOptionList();
                goToPlayAfterPlacingShips();
                $('#myPleaseWait').modal('hide');
                if(thisPlayerStartsGameFirst==true){
                    alert('Your tourn');
                }else{
//                    lockTable(opponentBoardId);
                    oponnentTurnsUpdateInterval=setInterval(sendReuqestForOponnentTurns, 1000);
                    //clearInterval(sendReuqestForOponnentTurns);
                }
            }
            else {
                thisPlayerStartsGameFirst = true;
            }
        }
    });
};


function sendReuqestForOponnentTurns() {
    $.ajax({
        method: "GET"
        , dataType: 'json'
        , url: "/service/turnstatus", //       data: { },
        success: function (data) {
           prepareOpponentShootsMap(data);
           placeOpponentsShootsOnBoard();
            alert(data.isMyTurn);
        }
    });
};