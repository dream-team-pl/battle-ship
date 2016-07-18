
/***** Variables****************************************/
var boardSize = 10;
var opponentBoardId='#id_opponentBoard';
var myBoardId='#id_myBoard';

/*************Turn generator***************/
var uniqueRandoms = [];
var numRandoms = boardSize*boardSize;
function generatePosition() {
    // refill the array if needed
    if (!uniqueRandoms.length) {
        for (var i = 1; i <=numRandoms; i++) {
            uniqueRandoms.push(i);
        }
    }
    var index = Math.floor(Math.random() * uniqueRandoms.length);
    var val = uniqueRandoms[index];

    // now remove that value from the array
    uniqueRandoms.splice(index, 1);

    return val;

}


/**************BOARDS MOCKS ***************************/
var opponentsBoardMap = {
    1: true, 2: true,
    27: true, 37: true,
    89: true, 90: true,
    15: true,
    23: true,
    56: true,
    60: true,
    41: true, 42: true,43: true,44: true,
    62: true, 63: true,64: true,
    85: true,86: true, 87: true
};
var myBoardMap = {
    1: true, 2: true,
    27: true, 37: true,
    89: true, 90: true,
    15: true,
    23: true,
    56: true,
    60: true,
    41: true, 42: true,43: true,44: true,
    62: true, 63: true,64: true,
    85: true,86: true, 87: true
};
/******************************************************/



function isWinner(turns,board){
    return turns==Object.keys(board).length;
};


var myCorrectTrurn=0;
function loadOpponentBoard() {
    console.log("loading opponent board");
    loadBoard(boardSize, opponentBoardId);
    $('td.cell_empty').each(function (index, elem) {
        $(this).on("click", function () {
            if(shot($(this), opponentsBoardMap)==false){
               opponentsTurn();
            }else{
                myCorrectTrurn++;
                if(isWinner(myCorrectTrurn,opponentsBoardMap)){
                    alert('You won game');
                    exitGame();
                }
            }
        });
    });
};

function putShipsOnBoard(board, idHtmlTable) {
    var htmlTable = $(idHtmlTable);
    for (var position in board) {
        if (board.hasOwnProperty(position)) {
            var tableCell = htmlTable.find('#' + position);
            if (board[position] == true) {
                console.log("putting ship at: " + position);
                tableCell.attr('class', 'cell_ship');
            }
        }
    }
}

function loadMyBoard() {
    console.log("loading my board");
    loadBoard(boardSize, myBoardId);
    putShipsOnBoard(myBoardMap, myBoardId);
};

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

function loadBoard(size, boardId) {
    var opponentBoard = $(boardId);
    var lettersLabels = opponentBoard.find("tr#id_lettersLabel");
    var tableBody = opponentBoard.find("tbody#id_tableBody");
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

/***********Game logic*************/

function exitGame(){
    lockTable(opponentBoardId);
    lockTable(myBoardId);
}

function lockTable(boardId){
    console.log("loking "+boardId);
    $(boardId).attr('class', 'player-board wait');
};
function unLockTable(boardId){
    console.log("unloking "+boardId);
    $(boardId).attr('class', 'player-board');
};


/**service information about opponent shoot. it is hitted or not***/

var opoonentsTurs=0;
function opponentsTurn() {
    var htmlTable = $(myBoardId);
    do {
        var rand = generatePosition();
        var tableCell = htmlTable.find('#' + rand);
        var isCorrectTurn = shot(tableCell, opponentsBoardMap);
        if(isCorrectTurn){
            opoonentsTurs++;
        }
        if (isWinner(opoonentsTurs, myBoardMap)) {
            alert('Oponent won game');
            exitGame();
            return ;
        }
    } while (isCorrectTurn);
};


/***********Mock for services shooting*************/
function shot(tableCell, boardMap) {
    var position = $(tableCell).attr('id');
    console.log("Shot on position: %s", position);
    //sending request using ajax to service
    if (true == boardMap[position]) {
        tableCell.attr('class', 'cell_hit');
        tableCell.off('click');
        return true;
    }
    else {
        tableCell.attr('class', 'cell_missed');
    }
    tableCell.off('click');
    return false;
};
/***********Mock for opponents turns*************/
