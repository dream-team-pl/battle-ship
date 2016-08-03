var oponnentTurnsUpdateInterval;
var thisPlayerStartsGameFirst = false;
var opponentsBoardMap = {};
//when all ships are placed are components shold be removed and additonal board should show
function goToPlayAfterPlacingShips() {
    $.get("/html/play", function (data) {
        $("#column1_id").append(data);
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
                //init game
                $.ajax({
                    method: "GET"
                    , dataType: 'json'
                    , url: "/service/shoot"
                    , data: {
                        "fieldNumber": 0
                    }
                    , success: function (data) {}
                });
                oponnentTurnsUpdateInterval = setInterval(sendReuqestForOponnentTurns, 1000);
                clearInterval(readyToPlayInterval);
                removeSelectOptionList();
                goToPlayAfterPlacingShips();
                $('#myPleaseWait').modal('hide');
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
    if (data.status == PLACING_STATUS_ENUM.WON) {
        clearInterval(oponnentTurnsUpdateInterval);
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
                oponnentTurnsUpdateInterval = setInterval(sendReuqestForOponnentTurns, 1000);
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

function sendReuqestForOponnentTurns() {
    $.ajax({
        method: "GET"
        , dataType: 'json'
        , url: "/service/turnstatus", //       data: { },
        success: function (data) {
            prepareOpponentShootsMap(data);
            placeOpponentsShootsOnBoard();
            if (data.isMyTurn == true) {
                unLockTable(opponentBoardId);
                clearInterval(oponnentTurnsUpdateInterval);
            }
            else if (data.winner != null) {
                alert('The winner is: ' + data.winner.name + ' ' + data.winner.surname);
                clearInterval(oponnentTurnsUpdateInterval);
            }
            else {
                lockTable(opponentBoardId);
            }
        }
    });
};
//it can be used when we will wait for oponnent turn
function exitGame() {
    lockTable(opponentBoardId);
    lockTable(myBoardId);
}