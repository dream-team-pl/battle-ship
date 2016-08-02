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

QUnit.test( "Register", function( assert ) {

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

QUnit.test( "Request for Ships", function( assert ) {

var done = assert.async();
var preparedShipList = [];
var ss = new Ship("twoMast");
preparedShipList.push(ss.type);
preparedShipList.push(new Ship("twoMast"));
preparedShipList.push(new Ship("threeMast"));
preparedShipList.push(new Ship("fourMast"));

    var shipsJSON = '{"status": "TRY_AGAIN", "availableShips": ["oneMast", "twoMast", "threeMast", "fourMast"]}';

    $.mockjax({
        url: '/service/place',
        responseText: shipsJSON
    });


    $.ajax({
        url: '/service/place',
        success: function(response) {
            var listOfShips = getListOfShipsFromJSON(response);
            assert.equal(ss.type, listOfShips, "Ships lists are equal");
        },

        error: function () {
        assert.ok(false, 'error callback executed');
        },
        complete: done
    });
});