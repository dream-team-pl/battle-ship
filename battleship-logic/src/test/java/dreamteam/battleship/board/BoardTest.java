package dreamteam.battleship.board;

import dreamteam.battleship.ship.Ship;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by grzegorz_sledz on 11.07.16.
 */
public class BoardTest {

    Board board;

    @BeforeMethod
    public void initialize() {
        this.board=new Board(10);
    }

    @Test
    public void shipOn(){
        Assert.assertEquals(board.shipOn(1),null);
    }

    @Test
    private void isPlaceForTheShipTest(){
        //Ship ship=new Ship();

//        Assert.assertEquals(board.isCorrectPosition(30,ship,Direction.VERTICAL),true);
    //Assert.assertEquals(board.isCorrectPosition(100,ship,Direction.HORIZONTAL),true);

    }

}
