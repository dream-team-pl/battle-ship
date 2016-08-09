var gameMode = 0;
var GAME_MODE_ENUM = {
    STANDARD: "STANDARD"
    ,GUN_SALUTE: "GUN_SALUTE"
};

function chooseGameMode() {
    $('.tooltip-test').tooltip();
    $('.popover-test').popover();
    $('#gameModeModal').modal('show');
}

function chooseGame() {
    $('#gameModeModal').modal('hide');
}