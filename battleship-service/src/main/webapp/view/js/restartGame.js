function showEndOfTheGameModal(data) {
    $("#winner_id").append(data);
    $('#endOfTheGameModal').modal('show');
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