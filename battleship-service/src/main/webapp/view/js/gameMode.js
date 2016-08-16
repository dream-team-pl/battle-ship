var GAME_MODE_ENUM = {
    NORMAL_MODE: "NORMAL_MODE"
    ,GUN_SALUTE_MODE: "GUN_SALUTE_MODE"
};
var gameMode = GAME_MODE_ENUM.NORMAL_MODE;

function chooseGameMode() {
    $('.tooltip-test').tooltip();
    $('.popover-test').popover();
    $('#gameModeModal').modal('show');
}

function chooseGame() {
    gameMode=$('input[name=gameModeRadioOption]:checked').val();
    $('#gameModeModal').modal('hide');
}