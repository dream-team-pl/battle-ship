var oponnentTurnsUpdateInterval;
var thisPlayerStartsGameFirst = false;
var column1Id = '#column1_id';
var numberOfShotsId = '#numberOfShots_id';
var delayBetweenSendingRequest = 1000;
//variables for salut mode
var numberOfShots = 0;
var shootToSend = [];
//shootToSend.push(1);
//shootToSend.push(2);
//alert(JSON.stringify({ "shoots": shootToSend}));
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

function updateShotCounterOnPage(value){
    $(numberOfShotsId).text(value);
}

// this metho load oponent board. it is invoked after placing all ships
function loadOpponentBoard() {
    console.log("loading opponent board");
    loadHtmlTableAsGameBoard(boardSize, opponentBoardId);
    $(opponentBoardId + ' td.cell_empty').each(function (index, elem) {
        $(this).on("click", function () {
            if (numberOfShots > 0){
                numberOfShots--;
                updateShotCounterOnPage(numberOfShots);
            } 
            shootToSend.push($(this).attr('id'));
            $(this).attr('class', 'cell_mark');
            $(this).off('click');
            if (numberOfShots == 0) {
                //send list of shoots to server
                console.log('sending shoots: ' + shootToSend);
                if(gameMode== GAME_MODE_ENUM.GUN_SALUTE_MODE){
                    lockTable(opponentBoardId);
                }
                //sendShotRequest($(opponentBoardId));
                sendShotRequest($(this).attr('id'), $(this));
                shootToSend=[];
            }
            //to remove
            //fieldNumber=1&fieldNumber=2&fieldNumber=3&fieldNumber=4
            //sendShotRequest($(this).attr('id'), $(this));
        });
    });
};
//this method is used for checking it is possiblility to play
//posiblity means that oponnent put all its ships on board
function isOpponentReady() {
    $.ajax({
        method: "GET"
        , dataType: 'json'
        , url: "/service/prepare?gameMode=" + gameMode
        , success: function (data) {
            if (data.readyToPlay) {
                sendInitShotRequest();
                clearInterval(readyToPlayInterval);
                removeSelectOptionList();
                $('#waitModal').modal('hide');
                goToPlayAfterPlacingShips();
                if (thisPlayerStartsGameFirst == true&&gameMode== GAME_MODE_ENUM.NORMAL_MODE) {
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
    if (data.hasOwnProperty('winner') && data.winner != null || data.hasOwnProperty('status') && data.status == PLACING_STATUS_ENUM.WON) {
        clearInterval(oponnentTurnsUpdateInterval);
        //        exitGame();
        showEndOfTheGameModal(data);
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
//fieldNumber=1&fieldNumber=2&fieldNumber=3&fieldNumber=4
function prepareShootUrl() {
    var fieldNumber="fieldNumber="; 
    var urlArgs="?";
    for (var i = 0; i < shootToSend.length; i++) {
        urlArgs=urlArgs+fieldNumber+shootToSend[i];
        if(i+1!=shootToSend.length){
            urlArgs+="&";
        }
    }
    return urlArgs;
}
//this method send shoot request after click on opponent board table cell
function sendShotRequest(position, tableCell) {
    $.ajax({
        method: "GET"
        , dataType: 'json'
        , url: "/service/shoot"+prepareShootUrl()
        , async: false
        , success: function (data) {
            var shootsResult=prepareOpponentShootsMap(data.resultFromOpponentBoard);
            placeOpponentsShootsOnBoard($(opponentBoardId),shootsResult);
            
            if(gameMode == GAME_MODE_ENUM.GUN_SALUTE_MODE&&!isWinner(data)){
                oponnentTurnsUpdateInterval = setInterval(sendReuqestForOponnentTurns, delayBetweenSendingRequest);
            }
            if (data.status == PLACING_STATUS_ENUM.SUCCESS || data.status == PLACING_STATUS_ENUM.WON&&gameMode == GAME_MODE_ENUM.NORMAL_MODE) {
                tableCell.attr('class', 'cell_hit');
                tableCell.off('click');
                var winnerExists=isWinner(data);
            }
            else if (data.status == PLACING_STATUS_ENUM.TRY_AGAIN) {
                tableCell.attr('class', 'cell_missed');
                oponnentTurnsUpdateInterval = setInterval(sendReuqestForOponnentTurns, delayBetweenSendingRequest);
            }
        }
    });
};
// this method place returned by ajax oponnent shoots and place it on player board
function placeOpponentsShootsOnBoard(htmlTable,opponentsBoardMap) {
   // var htmlTable = $(myBoardId);
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
            placeOpponentsShootsOnBoard($(myBoardId),opponentsBoardMap);
            numberOfShots = data.numberOfShots;
            updateShotCounterOnPage(numberOfShots);
                    
            
             if (data.hasOwnProperty('winner') && data.winner != null) {
                var winnerExists=isWinner(data);
            } else if (data.isMyTurn == true) {
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