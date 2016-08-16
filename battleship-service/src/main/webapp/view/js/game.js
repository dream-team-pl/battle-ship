var oponnentTurnsUpdateInterval;
var thisPlayerStartsGameFirst = false;
var column1Id = '#column1_id';
var numberOfShotsId = '#numberOfShots_id';
var delayBetweenSendingRequest = 1000;
//variables for salut mode
var numberOfShots = 0;
var shootsToSend = [];
//when all ships are placed are components shold be removed and additonal board should show
function goToPlayAfterPlacingShips() {
    $.get("/html/play", function (data) {
        $(column1Id).append(data);
        loadOpponentBoard();
        lockTable(opponentBoardId);

        removeActionsForPlacingShipFromPlayerBoard();
    });
};

function updateShotCounterOnPage(value) {
    $(numberOfShotsId).text(value);
}
// this metho load oponent board. it is invoked after placing all ships
function loadOpponentBoard() {
    console.log("loading opponent board");
    loadHtmlTableAsGameBoard(boardSize, opponentBoardId);
    $(opponentBoardId + ' td.cell_empty').each(function (index, elem) {
        //cell action after click
        $(this).on("click", function () {
            if (numberOfShots > 0) {
                numberOfShots--;
                updateShotCounterOnPage(numberOfShots);
                shootsToSend.push($(this).attr('id'));
                $(this).attr('class', 'cell_mark');
                $(this).off('click');
            }
            if (numberOfShots == 0) {
                console.log('sending shoots: ' + shootsToSend);
                if (gameMode == GAME_MODE_ENUM.GUN_SALUTE_MODE) {
                    lockTable(opponentBoardId);
                }
                sendShotRequest($(this).attr('id'), $(this));
            }
        });
    });
};
//this method is used for checking it is possiblility to play
//posiblity means that oponnent put all its ships on board
function isOpponentReady() {
lockTable(opponentBoardId);
    $.ajax({
        method: "GET"
        , dataType: 'json'
        , url: "/service/prepare"
        , data: {
            "gameMode": gameMode
        }
        , success: function (data) {
            if (data.readyToPlay) {

                sendInitShotRequest();
                clearInterval(readyToPlayInterval);
                removeSelectOptionList();
                $('#waitModal').modal('hide');
                goToPlayAfterPlacingShips();
            }
            else {
                thisPlayerStartsGameFirst = true;
            }

        }
    });
};

function isGameOver(data) {
    if (data == true) {
        clearInterval(oponnentTurnsUpdateInterval);
        var message = '<strong>Game over</strong> ';
        showEndOfTheGameModal(message);
        return true;
    }
    return false;
}
//method for checing who is winner. it depends only on returning status
function isWinner(data) {
    if ((data.hasOwnProperty('winner') && data.winner != null) || (data.hasOwnProperty('status') && data.status == PLACING_STATUS_ENUM.WON)) {
        clearInterval(oponnentTurnsUpdateInterval);
        var message = '<strong>The winner is:</strong> ' + data.winner.name + ' ' + data.winner.surname;
        showEndOfTheGameModal(message);
        return true;
    }
    return false;
}
//this method read oponnent moves and add it to opponentsBoardMap
function prepareOpponentShootsMap(items) {
    var opponentsBoardMap = {};
    for (var i in items) {
        var index = parseInt(i);
        opponentsBoardMap[index] = Boolean(items[index]);
    }
    return opponentsBoardMap;
};
//this method send shoot request after click on opponent board table cell
function sendInitShotRequest(position) {
    lockTable(opponentBoardId);
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
            unLockTable(opponentBoardId);
        }
    });
};
//this method is used for preparing url arguments ex.fieldNumber=1&fieldNumber=2&fieldNumber=3&fieldNumber=4
function prepareShootUrl() {
    var urlArgs = "";
    if (shootsToSend.length > 0) {
        urlArgs = "?"
    }
    var fieldNumber = "fieldNumber=";
    for (var i = 0; i < shootsToSend.length; i++) {
        urlArgs = urlArgs + fieldNumber + shootsToSend[i];
        if (i + 1 != shootsToSend.length) {
            urlArgs += "&";
        }
    }
    return urlArgs;
}
//this method send shoot request after click on opponent board table cell
function sendShotRequest(position, tableCell) {
    var urlArgs=prepareShootUrl();
    $.ajax({
        method: "GET"
        , dataType: 'json'
        , url: "/service/shoot" +urlArgs
        , async: false
        , success: function (data) {
            var shootsResult = prepareOpponentShootsMap(data.resultFromOpponentBoard);
            placeOpponentsShootsOnBoard($(opponentBoardId), shootsResult);
            //function isWinner(status,winner,name,surname) 
            if (gameMode == GAME_MODE_ENUM.GUN_SALUTE_MODE && !isWinner(data)) {
                oponnentTurnsUpdateInterval = setInterval(sendReuqestForOponnentTurns, delayBetweenSendingRequest);
            }
            else if (gameMode == GAME_MODE_ENUM.NORMAL_MODE) {
                if (data.status == PLACING_STATUS_ENUM.SUCCESS || data.status == PLACING_STATUS_ENUM.WON && gameMode == GAME_MODE_ENUM.NORMAL_MODE) {
                    tableCell.attr('class', 'cell_hit');
                    tableCell.off('click');
                    var winnerExists = isWinner(data);
                    numberOfShots++;
                    updateShotCounterOnPage(numberOfShots);
                }
                else if (data.status == PLACING_STATUS_ENUM.TRY_AGAIN) {
                    tableCell.attr('class', 'cell_missed');
                    lockTable(opponentBoardId);
                    oponnentTurnsUpdateInterval = setInterval(sendReuqestForOponnentTurns, delayBetweenSendingRequest);
                }
            }
            shootsToSend = [];
        }
    });
};
// this method place returned by ajax oponnent shoots and place it on player board
function placeOpponentsShootsOnBoard(htmlTable, opponentsBoardMap) {
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
            var opponentsBoardMap = prepareOpponentShootsMap(data.myDamages);
            placeOpponentsShootsOnBoard($(myBoardId), opponentsBoardMap);
            numberOfShots = data.numberOfShots;
            updateShotCounterOnPage(numberOfShots);
            var isEndOfTheGame = isGameOver(data.gameOver);
            if (data.hasOwnProperty('winner') && data.winner != null) {
                var winnerExists = isWinner(data);
            }
            else if (data.isMyTurn == true) {
                clearInterval(oponnentTurnsUpdateInterval);
                unLockTable(opponentBoardId);
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