//some ids
var shipTemplateBoardId = '#id_shipTemplate';
var shipsSelectListsId = '#shipsSelectLists_id';
var orientationBtnId = '#orientationBtn_id';

var currentShip;
var listOfShips = [];
//when ship is placed it is need for not alowwing puting it again
var placingShipCondition = true;
//interval need to checking it opponent sets ships and it is ready to play
var readyToPlayInterval;
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
// ***  SHIP  *** //
var globalShipID = 0;

function Ship(p_type, p_size, p_globalShipID) {
    this.type = p_type;
    this.size = p_size;
    this.direction = "HORIZONTAL";
    this.id = p_globalShipID;
    return this;
}

function getListOfShipsFromJSON(shipsToPlace) {
    var ships = [];
    for (var i in shipsToPlace.availableShips) {
        var size = 0;
        switch (shipsToPlace.availableShips[i]) {
        case "oneMast":
            size = 1;
            break;
        case "twoMast":
            size = 2;
            break;
        case "threeMast":
            size = 3;
            break;
        case "fourMast":
            size = 4;
            break;
        }
        globalShipID = globalShipID + 1;
        var s = new Ship(shipsToPlace.availableShips[i], size, globalShipID);
        ships.push(s);
    }
    return ships;
}

function goToPlacingBoard() {
    $("#content_id").load("/html/placing", function () {
        loadPlacingShipsProperties();
    });
};
//this method is invkoed when page is changing from registration to placing ships
function loadPlacingShipsProperties() {
    sendRequestForShipList();
    loadHtmlTableAsGameBoard(boardSize, myBoardId);
    setActionForPlacingShipOnPlayerBoard();
    setListenerOnShipsSelectLists();
}
//this request is send for ship list
//it will be better when we can get ship name and it size
function sendRequestForShipList() {
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
            listOfShips = getListOfShipsFromJSON(data);
            addRequestedShipsToSelectOptionList(listOfShips);
        }
    });
};

function addRequestedShipsToSelectOptionList(ships) {
    for (var i in ships) {
        $(shipsSelectListsId).append('<option shipSize="' + ships[i].size + '">' + ships[i].type + '</option>');
    }
};
//it allows to place ship on board.
//this action will be later removed becouse after placing ships it isn't possible to prepare action on board
function setActionForPlacingShipOnPlayerBoard() {
    console.log("setActionForPlacingShipOnPlayerBoard");
    $(myBoardId + ' td.cell_empty').each(function (index, elem) {
        $(this).on("click", function () {
            if (placingShipCondition == true) {
                var position = parseInt($(this).attr('id'));

                if(currentShip!=null)
                    sendRequestForPlacingShip(currentShip, position);
            }
        });
    });
};
//request is send to check it is possiblity to place ship on board
function sendRequestForPlacingShip(p_ship, fieldNumber) {
    $.ajax({
        method: "GET"
        , dataType: 'json'
        , url: "/service/place"
        , data: {
            "type": p_ship.type
            , "fieldNumber": fieldNumber
            , direction: p_ship.direction
        }
        , success: function (data) {
            if (data.status == PLACING_SHIP_STATUS_ENUM.SUCCESS) {
                putShipOnBoard(currentShip, fieldNumber);
                removeShipFromSelectLists();
                checkPlacingShipEndCondition();
            }
        }
    });
};

function checkPlacingShipEndCondition() {
    var length = $(shipsSelectListsId + ' > option').length;
    if (length == 0) {
        $('#waitModal').modal('show');
        readyToPlayInterval = setInterval(isOpponentReady, 1000);
    }
}
//after placing ship it must be removed from list
function removeShipFromSelectLists() {
    $(shipsSelectListsId + " option:selected").remove();
    clearShipToPlacePreview();
    placingShipCondition = false;
}
//this method is used when we click on board to place ship there
//it uses global variable like shipSize
function putShipOnBoard(shipToPut, position) {
    if (placingShipCondition == true) {
        var htmlTable = $(myBoardId);
        var dir = $(orientationBtnId).text();
        for (i = 1; i <= shipToPut.size; i = i + 1) {
            var tableCell = htmlTable.find('#' + position);
            tableCell.attr('class', 'cell_ship');
            tableCell.off('click');
            if (dir == DIRECTION_ENUM.HORIZONTAL) {
                position = position + 1;
            }
            else {
                position = position + boardSize;
            }
        }
        console.log("Ship is putted correctly");
    }
}
// clearing  board for ship(to place) preview
function clearShipToPlacePreview() {
    var opponentBoard = $(shipTemplateBoardId);
    var tableBody = opponentBoard.find("tbody#id_tableBody");
    tableBody.empty();
};
// creating ship preview
function createShipPreview(p_ship) {
    var opponentBoard = $(shipTemplateBoardId);
    var tableBody = opponentBoard.find("tbody#id_tableBody");
    for (i = 1; i <= p_ship.size; i++) {
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
    var t_shipName = $(shipsSelectListsId + " option:selected").text();
    currentShip = getShipFromShipList(t_shipName, listOfShips);
    var dir;

    if ($(orientationBtnId).text().toString() == DIRECTION_ENUM.VERTICAL) {
        dir = DIRECTION_ENUM.HORIZONTAL;
    }
    else {
        dir = DIRECTION_ENUM.VERTICAL;
    }

    $(orientationBtnId).text(dir);
    clearShipToPlacePreview();
    if(currentShip!=null) {
        currentShip.direction = dir;
        createShipPreview(currentShip);
    }

}

function setListenerOnShipsSelectLists() {
    $(shipsSelectListsId).change(function () {
        var shipName = $(shipsSelectListsId + " option:selected").text();
        currentShip = getShipFromShipList(shipName, listOfShips);
        if ($(orientationBtnId).text().toString() == DIRECTION_ENUM.VERTICAL) {
            currentShip.direction = DIRECTION_ENUM.VERTICAL;
        }
        else {
            currentShip.direction = DIRECTION_ENUM.HORIZONTAL;
        }

        clearShipToPlacePreview();
        if(currentShip!=null)
            createShipPreview(currentShip);
        placingShipCondition = true;
    });
};

function getShipFromShipList(shipName, shipList) {
    for (var i in shipList) {
        if (shipList[i].type == shipName) return shipList[i];
    }
    return null;
}
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