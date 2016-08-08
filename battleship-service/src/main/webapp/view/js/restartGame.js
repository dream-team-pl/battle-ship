function showEndOfTheGameModal(data) {
    $("#winner_id").append(data.winner.name + " " + data.winner.surname);
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
        }
        , error: function (e) {
            alert("Restart error");
        }
    });
}