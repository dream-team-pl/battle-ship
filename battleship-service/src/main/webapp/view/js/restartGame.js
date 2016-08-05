function showEndOfTheGameModal(data) {
    $("#winner_id").append(data.winner.name + " " + data.winner.surname);
    $('#endOfTheGameModal').modal('show');
}

function restartGame() {
    $("#winner_id").empty();
    $('#endOfTheGameModal').modal('hide');
    goToPlacingBoard();
}