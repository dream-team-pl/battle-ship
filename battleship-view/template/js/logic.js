$(document).ready(function () {
    // jQuery methods go here...
});

function goToRegister() {
    $("#content_id").load("register.html");
};

function goToPlacingBoard() {
    //$("#content_id").load("placingShip.html");  
    goToPlay();
};

function goToPlay() {
    $(".container").load("play.html", function () {
        loadComponentInGameMode();
    });
};

function loadComponentInGameMode() {
    loadOpponentBoard();
    loadMyBoard();
};

function sendRegisterRequest() {
    $.ajax({
        url: 'http://localhost:8080/register'
        , type: 'GET'
        , data: $("#form").serialize()
        , success: function (data) {
            //called when successful
            goToPlacingBoard();
        }
        , error: function (e) {
            alert("Permission denied");
        }
    });
};