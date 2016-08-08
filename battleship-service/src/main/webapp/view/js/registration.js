function goToRegister() {
    $("#content_id").load("/html/register");
};

function savePlayerName(){
    var name=$('#name_id').val();
    var surname=$('#surname_id').val();
    $('#playerName_id').text(name+' '+surname);
}

function sendRegisterRequest() {
    $.ajax({
        url: '/service/register'
        , type: 'GET'
        , data: $("#form").serialize()
        , success: function (data) {
            //method from placingShips.js
            goToPlacingBoard();
            savePlayerName();
        }
        , error: function (e) {
            alert("Registration error");
        }
    });
};