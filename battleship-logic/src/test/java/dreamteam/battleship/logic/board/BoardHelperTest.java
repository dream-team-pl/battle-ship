package dreamteam.battleship.logic.board;

import dreamteam.battleship.logic.board.Board;
import dreamteam.battleship.logic.board.BoardHelper;
import dreamteam.battleship.logic.board.Direction;
import static org.testng.Assert.*;

import dreamteam.battleship.logic.ship.Ship;
import dreamteam.battleship.logic.ship.ShipFactory;
import dreamteam.battleship.logic.ship.ShipType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tests for BoardHelper
 */
public class BoardHelperTest {

    /**
     *
     * @return board,position,direction,expected test result
     */

    @DataProvider
    public Object[][] itIsOnMaxBorderSiteProvider() {
        Board boardWithSize4 = new Board(4);
        return new Object[][]{
                {boardWithSize4,4,Direction.VERTICAL,true},
                {boardWithSize4,8,Direction.VERTICAL,true},
                {boardWithSize4,12,Direction.VERTICAL,true},
                {boardWithSize4,16,Direction.VERTICAL,true},
                {boardWithSize4,13,Direction.HORIZONTAL,true},
                {boardWithSize4,14,Direction.HORIZONTAL,true},
                {boardWithSize4,15,Direction.HORIZONTAL,true},
                {boardWithSize4,16,Direction.HORIZONTAL,true},
                {boardWithSize4,6,Direction.VERTICAL,false},
                {boardWithSize4,7,Direction.VERTICAL,false},
                {boardWithSize4,10,Direction.VERTICAL,false},
                {boardWithSize4,11,Direction.HORIZONTAL,false},
        };
    }

    /**
     * Test checking it position is on max board . It depends on direction
     * @param board
     * @param position
     * @param direction
     * @param expectedResult
     */
    @Test(dataProvider = "itIsOnMaxBorderSiteProvider")
    public void itIsOnMaxBorderSite(Board board,int position,Direction direction,boolean expectedResult){
        BoardHelper boardHelper =new BoardHelper(board);
        assertEquals(boardHelper.itIsOnMaxBorderSite(position,direction),expectedResult);
    }

    /**
     *
     * @return board,position,direction,expected test result
     */
    @DataProvider
    public Object[][] itIsOnMinBorderSiteProvider() {
        Board boardWithSize4 = new Board(4);
        return new Object[][]{
                {boardWithSize4,1,Direction.HORIZONTAL,true},
                {boardWithSize4,2,Direction.HORIZONTAL,true},
                {boardWithSize4,3,Direction.HORIZONTAL,true},
                {boardWithSize4,4,Direction.HORIZONTAL,true},
                {boardWithSize4,1,Direction.VERTICAL,true},
                {boardWithSize4,5,Direction.VERTICAL,true},
                {boardWithSize4,9,Direction.VERTICAL,true},
                {boardWithSize4,13,Direction.VERTICAL,true},
                {boardWithSize4,6,Direction.VERTICAL,false},
                {boardWithSize4,7,Direction.VERTICAL,false},
                {boardWithSize4,10,Direction.VERTICAL,false},
                {boardWithSize4,11,Direction.HORIZONTAL,false},
        };
    }

    /**
     * Test checking it position is on min board . It depends on direction
     * @param board
     * @param position
     * @param direction
     * @param expectedResult
     */
    @Test(dataProvider = "itIsOnMinBorderSiteProvider")
    public void itIsOnMinBorderSite(Board board,int position,Direction direction,boolean expectedResult){
        BoardHelper boardHelper =new BoardHelper(board);
        assertEquals(boardHelper.itIsOnMinBorderSite(position,direction),expectedResult);
    }

    /**
     *
     * @return board,position,expected positions before row
     */
    @DataProvider
    public Object[][] positionsBeforeRowProvider() {
        Board boardWithSize3 = new Board(3);
        BoardHelper boardHelper = new BoardHelper(boardWithSize3);
        return new Object[][]{
                {boardWithSize3,1,Arrays.asList()},
                {boardWithSize3,2,Arrays.asList()},
                {boardWithSize3,3,Arrays.asList()},
                {boardWithSize3,7,Arrays.asList(4,5)},
                {boardWithSize3,8,Arrays.asList(4,5,6)},
                {boardWithSize3,9,Arrays.asList(5,6)},
        };
    }

    /**
     * Testing positons before row
     * @param board
     * @param position
     * @param expectedPositonsToCheck
     */
    @Test(dataProvider = "positionsBeforeRowProvider")
    public void positionsBeforeRow(Board board, int position, List<Integer> expectedPositonsToCheck){
        BoardHelper boardHelper = new BoardHelper(board);
        List<Integer> positionsToCheck=boardHelper.positionsBeforeRow(position);
        assertTrue(positionsToCheck.containsAll(expectedPositonsToCheck)&&positionsToCheck.size()==expectedPositonsToCheck.size());
    }

    /**
     *
     * @return board,position,expected positions in row
     */
    @DataProvider
    public Object[][] positionsInRowProvider() {
        Board boardWithSize3 = new Board(3);
        BoardHelper boardHelper = new BoardHelper(boardWithSize3);
        return new Object[][]{
                {boardWithSize3,1,Arrays.asList(1,2)},
                {boardWithSize3,2,Arrays.asList(1,2,3)},
                {boardWithSize3,3,Arrays.asList(2,3)},
        };
    }

    @Test(dataProvider = "positionsInRowProvider")
    public void positionsInRow(Board board,int position,List<Integer> expectedPositonsToCheck){
        BoardHelper boardHelper = new BoardHelper(board);
        List<Integer> positionsToCheck=boardHelper.positionsInRow(position);
        assertTrue(positionsToCheck.containsAll(expectedPositonsToCheck)&&positionsToCheck.size()==expectedPositonsToCheck.size());
    }

    /**
     *
     * @return board,position,expected positions after row
     */
    @DataProvider
    public Object[][] positionsAfterRowProvider() {
        Board boardWithSize3 = new Board(3);
        BoardHelper boardHelper = new BoardHelper(boardWithSize3);
        return new Object[][]{
                {boardWithSize3,1,Arrays.asList(4,5)},
                {boardWithSize3,2,Arrays.asList(4,5,6)},
                {boardWithSize3,3,Arrays.asList(5,6)},
                {boardWithSize3,7,Arrays.asList()},
                {boardWithSize3,8,Arrays.asList()},
                {boardWithSize3,9,Arrays.asList()},
        };
    }

    @Test(dataProvider = "positionsAfterRowProvider")
    public void positionsAfterRow(Board board,int position,List<Integer> expectedPositonsToCheck){
        BoardHelper boardHelper = new BoardHelper(board);
        List<Integer> positionsToCheck=boardHelper.positionsAfterRow(position);
        assertTrue(positionsToCheck.containsAll(expectedPositonsToCheck)&&positionsToCheck.size()==expectedPositonsToCheck.size());
    }

    /**
     *
     * @return board,position,expected positions before column
     */
    @DataProvider
    public Object[][] positionsBeforeColumnProvider() {
        Board boardWithSize3 = new Board(3);
        BoardHelper boardHelper = new BoardHelper(boardWithSize3);
        return new Object[][]{
                {boardWithSize3,1,Arrays.asList()},
                {boardWithSize3,4,Arrays.asList()},
                {boardWithSize3,7,Arrays.asList()},
                {boardWithSize3,3,Arrays.asList(2,5)},
                {boardWithSize3,6,Arrays.asList(2,5,8)},
                {boardWithSize3,9,Arrays.asList(5,8)},
        };
    }

    @Test(dataProvider = "positionsBeforeColumnProvider")
    public void positionsBeforeColumn(Board board,int position,List<Integer> expectedPositonsToCheck){
        BoardHelper boardHelper = new BoardHelper(board);
        List<Integer> positionsToCheck=boardHelper.positionsBeforeColumn(position);
        assertTrue(positionsToCheck.containsAll(expectedPositonsToCheck)&&positionsToCheck.size()==expectedPositonsToCheck.size());
    }

    /**
     *
     * @return board,position,expected positions in column
     */
    @DataProvider
    public Object[][] positionsInColumnProvider() {
        Board boardWithSize3 = new Board(3);
        BoardHelper boardHelper = new BoardHelper(boardWithSize3);
        return new Object[][]{
                {boardWithSize3,2,Arrays.asList(2,5)},
                {boardWithSize3,5,Arrays.asList(2,5,8)},
                {boardWithSize3,8,Arrays.asList(5,8)},
        };
    }

    @Test(dataProvider = "positionsInColumnProvider")
    public void positionsInColumn(Board board,int position,List<Integer> expectedPositonsToCheck){
        BoardHelper boardHelper = new BoardHelper(board);
        List<Integer> positionsToCheck=boardHelper.positionsInColumn(position);
        assertTrue(positionsToCheck.containsAll(expectedPositonsToCheck)&&positionsToCheck.size()==expectedPositonsToCheck.size());
    }

    /**
     *
     * @return board,position,expected positions after column
     */
    @DataProvider
    public Object[][] positionsAfterColumnProvider() {
        Board boardWithSize3 = new Board(3);
        BoardHelper boardHelper = new BoardHelper(boardWithSize3);
        return new Object[][]{
                {boardWithSize3,2,Arrays.asList(3,6)},
                {boardWithSize3,5,Arrays.asList(3,6,9)},
                {boardWithSize3,8,Arrays.asList(6,9)},
                {boardWithSize3,3,Arrays.asList()},
                {boardWithSize3,6,Arrays.asList()},
                {boardWithSize3,9,Arrays.asList()},
        };
    }

    @Test(dataProvider = "positionsAfterColumnProvider")
    public void positionsAfterColumn(Board board,int position,List<Integer> expectedPositonsToCheck){
        BoardHelper boardHelper = new BoardHelper(board);
        List<Integer> positionsToCheck=boardHelper.positionsAfterColumn(position);
        assertTrue(positionsToCheck.containsAll(expectedPositonsToCheck)&&positionsToCheck.size()==expectedPositonsToCheck.size());
    }

    /**
     *
     * @return board, board helper, ship, position,Direction,expected positions to check
     */
    @DataProvider
    public Object[][] positionToCheckProvider() {

        Board boardWithSize5 = new Board(5);
        Ship shipWithSize3 = ShipFactory.create(ShipType.threeMast);
        BoardHelper boardHelper = new BoardHelper(boardWithSize5);
        return new Object[][]{
                {boardWithSize5,boardHelper, shipWithSize3, 2,Direction.HORIZONTAL, Arrays.asList(1,2,3,4,5,6,7,8,9,10)},
                {boardWithSize5,boardHelper, shipWithSize3, 1,Direction.HORIZONTAL, Arrays.asList(1,2,3,4,6,7,8,9)},
                {boardWithSize5,boardHelper, shipWithSize3, 3,Direction.HORIZONTAL, Arrays.asList(2,3,4,5,7,8,9,10)},
                {boardWithSize5,boardHelper, shipWithSize3, 7,Direction.HORIZONTAL, Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15)},
                {boardWithSize5,boardHelper, shipWithSize3, 6,Direction.HORIZONTAL, Arrays.asList(1,2,3,4,6,7,8,9,11,12,13,14)},
                {boardWithSize5,boardHelper, shipWithSize3, 8,Direction.HORIZONTAL, Arrays.asList(2,3,4,5,7,8,9,10,12,13,14,15)},
                {boardWithSize5,boardHelper, shipWithSize3, 22,Direction.HORIZONTAL, Arrays.asList(16,17,18,19,20,21,22,23,24,25)},
                {boardWithSize5,boardHelper, shipWithSize3, 21,Direction.HORIZONTAL, Arrays.asList(16,17,18,19,21,22,23,24)},
                {boardWithSize5,boardHelper, shipWithSize3, 23,Direction.HORIZONTAL, Arrays.asList(17,18,19,20,22,23,24,25)},

                {boardWithSize5,boardHelper, shipWithSize3, 6,Direction.VERTICAL, Arrays.asList(1,2,6,7,11,12,16,17,21,22)},
                {boardWithSize5,boardHelper, shipWithSize3, 1,Direction.VERTICAL, Arrays.asList(1,2,6,7,11,12,16,17)},
                {boardWithSize5,boardHelper, shipWithSize3, 11,Direction.VERTICAL, Arrays.asList(6,7,11,12,16,17,21,22)},
                {boardWithSize5,boardHelper, shipWithSize3, 7,Direction.VERTICAL, Arrays.asList(1,2,3,6,7,8,11,12,13,16,17,18,21,22,23)},
                {boardWithSize5,boardHelper, shipWithSize3, 2,Direction.VERTICAL, Arrays.asList(1,2,3,6,7,8,11,12,13,16,17,18)},
                {boardWithSize5,boardHelper, shipWithSize3, 12,Direction.VERTICAL, Arrays.asList(6,7,8,11,12,13,16,17,18,21,22,23)},
                {boardWithSize5,boardHelper, shipWithSize3, 10,Direction.VERTICAL, Arrays.asList(4,5,9,10,14,15,19,20,24,25)},
                {boardWithSize5,boardHelper, shipWithSize3, 5, Direction.VERTICAL, Arrays.asList(4,5,9,10,14,15,19,20)},
                {boardWithSize5,boardHelper, shipWithSize3, 15,Direction.VERTICAL, Arrays.asList(9,10,14,15,19,20,24,25)},

        };
    }

    /**
     * Test for required postions to place ship on board
     * @param board
     * @param boardHelper
     * @param ship
     * @param position
     * @param direction
     * @param expectedPositionsToCheck
     */
    @Test(dataProvider = "positionToCheckProvider")
    public void positionToCheckTest(Board board,BoardHelper boardHelper,Ship ship,int position,Direction direction,List<Integer> expectedPositionsToCheck){
        List<Integer> positionToCheck=new ArrayList<Integer>();
        for (int i=0;i<ship.size();i++){
            if (Direction.HORIZONTAL==direction){
                positionToCheck.addAll(boardHelper.positionsToCheckAtPartOfShip(position+i,ship,i+1,direction));
            }else  if (Direction.VERTICAL==direction){
                positionToCheck.addAll(boardHelper.positionsToCheckAtPartOfShip(position+i*board.size() ,ship,i+1,direction));
            }
        }
        assertTrue(expectedPositionsToCheck.containsAll(positionToCheck)&&expectedPositionsToCheck.size()==positionToCheck.size());
    }

}