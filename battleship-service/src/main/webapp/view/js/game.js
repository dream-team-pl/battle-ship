var oponnentTurnsUpdateInterval;
var thisPlayerStartsGameFirst = false;
var column1Id = '#column1_id';

var delayBetweenSendingRequest=1000;

//when all ships are placed are components shold be removed and additonal board should show
function goToPlayAfterPlacingShips() {
    $.get("/html/play", function (data) {
        $(column1Id).append(data);
        loadOpponentBoard();
        removeActionsForPlacingShipFromPlayerBoard();
        if (thisPlayerStartsGameFirst == false) {
            lockTable(opponentBoardId);
        }
    });
};
// this metho load oponent board. it is invoked after placing all ships
function loadOpponentBoard() {
    console.log("loading opponent board");
    loadHtmlTableAsGameBoard(boardSize, opponentBoardId);
    $(opponentBoardId + ' td.cell_empty').each(function (index, elem) {
        $(this).on("click", function () {
            sendShotRequest($(this).attr('id'), $(this));
        });
    });
};
//this method is used for checking it is possiblility to play
//posiblity means that oponnent put all its ships on board
function isOpponentReady() {
    $.ajax({
        method: "GET"
        , dataType: 'json'
        , url: "/service/prepare"
        , success: function (data) {
            if (data.readyToPlay) {
                sendInitShotRequest();
                clearInterval(readyToPlayInterval);
                removeSelectOptionList();
                $('#myPleaseWait').modal('hide');
                goToPlayAfterPlacingShips();
                if (thisPlayerStartsGameFirst == true) {
                    alert('Your turn');
                }
            }
            else {
                thisPlayerStartsGameFirst = true;
            }
        }
    });
};



//method for checing who is winner. it depends only on returning status
function isWinner(data) {
    if (data.hasOwnProperty('winner') && data.winner != null||data.hasOwnProperty('status') && data.status == PLACING_STATUS_ENUM.WON) {
        clearInterval(oponnentTurnsUpdateInterval);
//        exitGame();
        showEndOfTheGameModal(data);
            
    }
}
//this method read oponnent moves and add it to opponentsBoardMap
function prepareOpponentShootsMap(response) {
    var opponentsBoardMap = {};
    var items = response.myDamages;
    for (var i in items) {
        var index = parseInt(i);
        opponentsBoardMap[index] = Boolean(items[index]);
    }
    return opponentsBoardMap;
};

//this method send shoot request after click on opponent board table cell
function sendInitShotRequest(position) {
    $.ajax({
        method: "GET"
        , dataType: 'json'
        , url: "/service/shoot"
        , async: false
        , data: {
            "fieldNumber": 0
        }
        , success: function (data) {
             oponnentTurnsUpdateInterval = setInterval(sendReuqestForOponnentTurns, delayBetweenSendingRequest);
        }
    });
};

//this method send shoot request after click on opponent board table cell
function sendShotRequest(position, tableCell) {
    $.ajax({
        method: "GET"
        , dataType: 'json'
        , url: "/service/shoot"
        , async: false
        , data: {
            "fieldNumber": position
        }
        , success: function (data) {
            if (data.status == PLACING_STATUS_ENUM.SUCCESS || data.status == PLACING_STATUS_ENUM.WON) {
                tableCell.attr('class', 'cell_hit');
                tableCell.off('click');
                isWinner(data);
            }
            else if (data.status == PLACING_STATUS_ENUM.TRY_AGAIN) {
                tableCell.attr('class', 'cell_missed');
                oponnentTurnsUpdateInterval = setInterval(sendReuqestForOponnentTurns, delayBetweenSendingRequest);
            }

        }
    });
};
// this method place returned by ajax oponnent shoots and place it on player board
function placeOpponentsShootsOnBoard(opponentsBoardMap) {
    var htmlTable = $(myBoardId);
    for (var i in opponentsBoardMap) {
        var tableCell = htmlTable.find('#' + i);
        var position = $(tableCell).attr('id');
        console.log("Shot on position: %s", i);
        if (true == Boolean(opponentsBoardMap[i])) {
            tableCell.attr('class', 'cell_hit');
        }
        else {
            tableCell.attr('class', 'cell_missed');
        }
    }
};

function sendReuqestForOponnentTurns() {
    $.ajax({
        method: "GET"
        , dataType: 'json'
        , async: false
        , url: "/service/turnstatus", //       data: { },
        success: function (data) {
            var opponentsBoardMap = prepareOpponentShootsMap(data);
            placeOpponentsShootsOnBoard(opponentsBoardMap);
            if (data.isMyTurn == true) {
                unLockTable(opponentBoardId);
                clearInterval(oponnentTurnsUpdateInterval);
            }
            else if (data.hasOwnProperty('winner') && data.winner != null) {
                isWinner(data)
            }
            else {
                lockTable(opponentBoardId);
            }
        }
    });
};
//locking tables after game
function exitGame() {
    lockTable(opponentBoardId);
    lockTable(myBoardId);
}
