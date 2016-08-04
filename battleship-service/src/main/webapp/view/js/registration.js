function goToRegister() {
    $("#content_id").load("/html/register");
};

function sendRegisterRequest() {
    $.ajax({
        url: '/service/register'
        , type: 'GET'
        , data: $("#form").serialize()
        , success: function (data) {
            //method from placingShips.js
            goToPlacingBoard();
        }
        , error: function (e) {
            alert("Registration error");
        }
    });
};