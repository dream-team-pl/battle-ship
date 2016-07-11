package arbiter;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ship.Ship;

import java.util.LinkedList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by daniel on 11.07.16.
 * Tests for Arbiter class
 */
public class ArbiterTest {

    /**
     * local variable used in almost every test
     */
    List<Ship> shipList;

    /**
     * initialize new List before every executed method
     */
    @BeforeMethod
    public void initializeShipList() {
        shipList = new LinkedList<Ship>();

        shipList.add(new Ship("fourMasted", (byte) 4));
        shipList.add(new Ship("threeMasted", (byte) 3));
        shipList.add(new Ship("twoMasted", (byte) 2));
        shipList.add(new Ship("oneMasted", (byte) 1));
    }

    @DataProvider
    public Object[][] removedShips() {
        return new Object[][] {
                {new Ship("fourMasted", (byte) 4), null},
                {new Ship("threeMasted", (byte) 3), null},
                {new Ship("threeMasted", (byte) 4), new Ship("threeMasted", (byte) 3)},
                {new Ship("threeMasted", (byte) 4), new Ship("threeMasted", (byte) 1)},
                {new Ship("threeMasted", (byte) 2), new Ship("threeMasted", (byte) 1)},
                {new Ship("threeMasted", (byte) 2), new Ship("threeMasted", (byte) 2)}
        };
    }

    /**
     *
     * @param ship1 - ship to removed
     * @param ship2 - ship to removed
     * checking removeShip method functionality
     */
    @Test(dataProvider = "removedShips", expectedExceptions = IllegalArgumentException.class)
    public void allowRemovingShip(Ship ship1, Ship ship2) {
        // given
        Arbiter arbiter = new Arbiter(shipList);

        // when
        arbiter.removeShip(ship1);
        shipList.remove(ship1);

        arbiter.removeShip(ship2);
        shipList.remove(ship2);

        // then
        assertEquals(shipList, arbiter.shipList);
    }

    /**
     * checking winning condition when we removed all ships from list
     */
    @Test
    public void checkingWinningConditionSucceed() {
        // given
        Arbiter arbiter = new Arbiter(shipList);

        // when
        arbiter.removeShip(new Ship("oneMasted", (byte) 1));
        arbiter.removeShip(new Ship("twoMasted", (byte) 2));
        arbiter.removeShip(new Ship("threeMasted", (byte) 3));
        arbiter.removeShip(new Ship("fourMasted", (byte) 4));

        // then
        assertTrue(arbiter.isWinner());
    }

    /**
     * checking winning condition when we didn't remove all ships from list
     */
    @Test(dataProvider = "removedShips", expectedExceptions = IllegalArgumentException.class)
    public void checkingWinningConditionFailed(Ship ship1, Ship ship2) {
        // given
        Arbiter arbiter = new Arbiter(shipList);

        // when
        arbiter.removeShip(ship1);
        shipList.remove(ship1);

        arbiter.removeShip(ship2);
        shipList.remove(ship2);

        // then
        assertFalse(arbiter.isWinner());
    }
}
