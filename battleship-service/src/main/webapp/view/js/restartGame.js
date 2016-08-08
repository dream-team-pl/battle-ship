function showEndOfTheGameModal(data) {
    $("#winner_id").append(data.winner.name + " " + data.winner.surname);
    $('#endOfTheGameModal').modal('show');
}

function chooseGameMode() {
    $('.tooltip-test').tooltip();
    $('.popover-test').popover();
    $('#gameModeModal').modal('show');
}

function chooseGame() {
    $('#gameModeModal').modal('hide');
}

function restartGame() {
    $.ajax({
        url: '/service/restart'
        , type: 'GET'
        , success: function (data) {
            $("#winner_id").empty();
            $('#endOfTheGameModal').modal('hide');
            goToPlacingBoard();
            chooseGameMode();
        }
        , error: function (e) {
            alert("Restart error");
        }
    });
}