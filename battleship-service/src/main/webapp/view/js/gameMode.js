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
    gameMode=$('input[name=gameModeRadioOption]:checked').val();
    $('#gameModeModal').modal('hide');
}