QUnit.test( "hello test", function( assert ) {
  assert.ok( 1 == "1", "Passed!" );
});

QUnit.test( "changing field with number to field with character", function( assert ) {
  assert.ok(getLetterFromAlphabet(0) == "", "No letter here");
  assert.ok(getLetterFromAlphabet(1) == "A", "letter: A");
  assert.ok(getLetterFromAlphabet(2) == "B", "letter: B");
  assert.ok(getLetterFromAlphabet(3) == "C", "letter: C");
  assert.ok(getLetterFromAlphabet(4) == "D", "letter: D");
  assert.ok(getLetterFromAlphabet(5) == "E", "letter: E");
  assert.ok(getLetterFromAlphabet(6) == "F", "letter: F");
  assert.ok(getLetterFromAlphabet(7) == "G", "letter: G");
  assert.ok(getLetterFromAlphabet(8) == "H", "letter: H");
  assert.ok(getLetterFromAlphabet(9) == "I", "letter: I");
  assert.ok(getLetterFromAlphabet(10) == "J", "letter: J");
});

QUnit.test( "register user", function( assert ) {

var done = assert.async();

    var playerID = '{ "player": { "name": "Daniel", "surname": "Zaucha", "identification": "14701447792250.72200961974147847" } }';

    $.mockjax({
        url: '/service/register',
        responseText: playerID
    });


    $.ajax({
        url: '/service/register',
        success: function(response) {
        assert.equal(response, playerID, "User get Register Data.");
        },

        error: function () {
        assert.ok(false, 'error callback executed');
        },
        complete: done
    });
});

QUnit.test( "save json response to array of ships", function( assert ) {

    var done = assert.async();

    var preparedShipList = [];
    preparedShipList.push(new Ship("oneMast", 1, 1));
    preparedShipList.push(new Ship("twoMast", 2, 2));
    preparedShipList.push(new Ship("threeMast", 3, 3));
    preparedShipList.push(new Ship("fourMast", 4, 4));

    var test_listOfShips = [];

    var shipsJSON = '{"status": "TRY_AGAIN", "availableShips": ["oneMast", "twoMast", "threeMast", "fourMast"]}';

    $.mockjax({
        url: '/service/place',
        dataType: 'json',
        responseText: shipsJSON
    });


    $.ajax({
        url: '/service/place',
        dataType: 'json',
        success: function(response) {
            test_listOfShips = getListOfShipsFromJSON(response);
            assert.deepEqual(test_listOfShips, preparedShipList, "Ships lists are equal");
        },

        error: function () {
            assert.ok(false, 'error callback executed');
        },
        complete: done
    });
});

QUnit.test( "take ship from shipList", function( assert ) {

    var preparedShipList = [];
    preparedShipList.push(new Ship("oneMast", 1, 1));
    preparedShipList.push(new Ship("twoMast", 2, 2));
    preparedShipList.push(new Ship("threeMast", 3, 3));
    preparedShipList.push(new Ship("fourMast", 4, 4));
    preparedShipList.push(new Ship("fourMast", 4, 5));


    assert.deepEqual(getShipFromShipList("oneMast", preparedShipList), preparedShipList[0], "Ships with name 'one mast' are equal");
    assert.deepEqual(getShipFromShipList("twoMast", preparedShipList), preparedShipList[1], "Ships with name 'two mast' are equal");
    assert.deepEqual(getShipFromShipList("threeMast", preparedShipList), preparedShipList[2], "Ships with name 'three mast' are equal");
    assert.deepEqual(getShipFromShipList("fourMast", preparedShipList), preparedShipList[3], "Ships with name 'four mast' are equal");
    assert.notDeepEqual(getShipFromShipList("fourMast", preparedShipList), preparedShipList[4], "Ships with name 'four mast' aren't equal");
});

QUnit.test( "prepared opponent board based on server response", function( assert ) {

    var done = assert.async();
    var testingBoard = {11:true, 16:true, 26:true, 31:true, 32:true, 36:true, 46:true, 51:true, 52:true, 53:true};

    var serverResponse = '{"status":null,"winner":{"name":"x","surname":"x","identification":"14703151276810.72200961291804046"},"myDamages":{"32":true,"16":true,"51":true,"52":true,"36":true,"53":true,"26":true,"11":true,"46":true,"31":true},"isMyTurn":false}';

    $.mockjax({
        url: '/service/turnstatus',
        dataType: 'json',
        responseText: serverResponse
    });

 $.ajax({
        method: "GET"
        , dataType: 'json'
        , async: false
        , url: "/service/turnstatus",
        success: function (data) {
            var opponentsBoardMap = prepareOpponentShootsMap(data.myDamages);
            assert.equal(data.status, null, "statuses are equal");
            assert.equal(data.winner.name, "x", "names are equal");
            assert.equal(data.winner.surname, "x", "names are equal");
            assert.equal(data.winner.identification, "14703151276810.72200961291804046", "ids are equal");
            assert.deepEqual(opponentsBoardMap, testingBoard, "list of damaged ships are equal");
        },
        error: function () {
            assert.ok(false, 'error callback executed');
        },
        complete: done
    });
});
