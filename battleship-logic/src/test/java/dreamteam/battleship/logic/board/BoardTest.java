package dreamteam.battleship.logic.board;

import dreamteam.battleship.logic.board.Board;
import dreamteam.battleship.logic.board.Direction;
import dreamteam.battleship.logic.ship.Ship;
import dreamteam.battleship.logic.ship.ShipFactory;
import dreamteam.battleship.logic.ship.ShipType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


/**
 * Tests for Board
 */
public class BoardTest {

    /**
     * Tests checking if board doesn't contain any ship after creation
     */
    @Test
    public void shipOnPositionEmptyBoardShouldBeNull() {
        Board board=new Board(3);
        for(int i=1;i<board.maxSize();i++) {
            assertEquals(board.shipOn(i), null);
        }
    }

    /**
     *Test for placing ship horizontal on board and checking it positions
     */
    @Test
    public void shipOnPositionAfterPlacingShipOnBoardHorizontalShouldBeNotNull() {
        Board board=new Board(10);
        Ship threeMast= ShipFactory.create(ShipType.threeMast);
        assertTrue(board.placeShip(1,threeMast, Direction.HORIZONTAL));
        assertEquals(board.shipOn(1), threeMast);
        assertEquals(board.shipOn(2), threeMast);
        assertEquals(board.shipOn(3), threeMast);
        assertEquals(board.shipOn(4), null);
    }

    /**
     *Test for placing ship vertical on board and checking it positions
     */
    @Test
    public void shipOnPositionAfterPlacingShipOnBoardVerticalShoudBeNotNull() {
        Board board=new Board(5);
        Ship threeMast=ShipFactory.create(ShipType.threeMast);
        assertTrue(board.placeShip(1,threeMast,Direction.VERTICAL));
        assertEquals(board.shipOn(1), threeMast);
        assertEquals(board.shipOn(6), threeMast);
        assertEquals(board.shipOn(11), threeMast);
        assertEquals(board.shipOn(16), null);
    }

    /**
     *
     * @return data for checking if it possibility to place ship on board
     */
    @DataProvider
    public Object[][] isPlaceForTheShipProvider() {
        Board boardWithSize5=new Board(5);
        Ship shipWithSize3= ShipFactory.create(ShipType.threeMast);
        return new Object[][]{
                {boardWithSize5,0, shipWithSize3,Direction.HORIZONTAL,false},
                {boardWithSize5,1, shipWithSize3,Direction.HORIZONTAL,true},
                {boardWithSize5,2, shipWithSize3,Direction.HORIZONTAL,true},
                {boardWithSize5,3, shipWithSize3,Direction.HORIZONTAL,true},
                {boardWithSize5,11, shipWithSize3,Direction.VERTICAL,true},
                {boardWithSize5,16, shipWithSize3,Direction.VERTICAL,false},
        };
    }

    /**
     * Test checking it is place on board for ship
     * @param board
     * @param position
     * @param ship
     * @param direction
     * @param expectedValue expected test result
     */
    @Test(dataProvider = "isPlaceForTheShipProvider")
    public void isPlaceForTheShip(Board board,int position,Ship ship,Direction direction,boolean expectedValue){
        assertEquals( board.isPlaceForTheShip(position,ship,direction),expectedValue);

    }

    @DataProvider
    public Object[][] placeOneMastShipOnPositon5InBoardWithSize3Provider() {
        Board boardWithSize3=new Board(3);
        Ship shipWithSize1= ShipFactory.create(ShipType.oneMast);

        return new Object[][]{
                {boardWithSize3,5, shipWithSize1,Direction.HORIZONTAL,true},
                {boardWithSize3,1, shipWithSize1,Direction.HORIZONTAL,false},
                {boardWithSize3,2, shipWithSize1,Direction.HORIZONTAL,false},
                {boardWithSize3,3, shipWithSize1,Direction.HORIZONTAL,false},
                {boardWithSize3,4, shipWithSize1,Direction.HORIZONTAL,false},
                {boardWithSize3,6, shipWithSize1,Direction.HORIZONTAL,false},
                {boardWithSize3,7, shipWithSize1,Direction.HORIZONTAL,false},
                {boardWithSize3,8, shipWithSize1,Direction.HORIZONTAL,false},
                {boardWithSize3,9, shipWithSize1,Direction.HORIZONTAL,false},
        };
    }
    @Test(dataProvider = "placeOneMastShipOnPositon5InBoardWithSize3Provider")
    public void placeOneMastShipOnPosion5InBoardWithSize3(Board board,int position, Ship ship,Direction direction,boolean expectedValue) {
        assertEquals(board.placeShip( position,ship, direction), expectedValue);
    }





}
