var boardSize = 10;
var opponentBoardId = '#id_opponentBoard';
var myBoardId = '#id_myBoard';

function lockTable(boardId) {
    console.log("loking " + boardId);
    $(boardId).attr('class', 'player-board wait');
};

function unLockTable(boardId) {
    console.log("unloking " + boardId);
    $(boardId).attr('class', 'player-board');
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