package dreamteam.battleship.logic.arbiter;

import dreamteam.battleship.logic.ship.Ship;
import dreamteam.battleship.logic.ship.ShipFactory;
import dreamteam.battleship.logic.ship.ShipImpl;
import dreamteam.battleship.logic.ship.ShipType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Tests for ArbiterImpl class
 */
public class ArbiterImplTest {

    /**
     *
     * @return ships that will be removed
     */
    @DataProvider
    public Object[][] removedShips() {
        return new Object[][] {
                {ShipFactory.create(ShipType.fourMast), null},
                {ShipFactory.create(ShipType.threeMast), null},
                {ShipFactory.create(ShipType.fourMast), ShipFactory.create(ShipType.threeMast)},
                {ShipFactory.create(ShipType.fourMast), ShipFactory.create(ShipType.oneMast)},
                {ShipFactory.create(ShipType.twoMast), ShipFactory.create(ShipType.oneMast)},
                {ShipFactory.create(ShipType.twoMast), ShipFactory.create(ShipType.oneMast)}
        };
    }

    /**
     *
     * @param ship1 - ship to removed
     * @param ship2 - ship to removed
     * checking removeShip method functionality
     */
    @Test(dataProvider = "removedShips", expectedExceptions = IllegalArgumentException.class)
    public void allowRemovingShip(ShipImpl ship1, ShipImpl ship2) {
        // given
        List<Ship> shipList = createShipList();
        Arbiter arbiter = new ArbiterImpl(shipList);

        // when
        arbiter.removeShip(ship1);
        shipList.remove(ship1);

        arbiter.removeShip(ship2);
        shipList.remove(ship2);

        // then
        assertEquals(shipList, arbiter.shipList);
    }

    /**
     * checking winning condition when we have empty list of ships
     */
    @Test
    public void checkingWinningConditionSucceed() {
        // given
        List<Ship> shipList = new LinkedList();
        Arbiter arbiter = new ArbiterImpl(shipList);

        // then
        assertTrue(arbiter.isWinner());
    }

    /**
     * checking winning condition when we didn't remove ships from list
     */
    @Test
    public void checkingWinningConditionFailed() {
        // given
        List<Ship> shipList = createShipList();
        Arbiter arbiter = new ArbiterImpl(shipList);

        // when
        // ...

        // then
        assertFalse(arbiter.isWinner());
    }

    /**
     * initialize new List that is consist of 4 ships
     */
    public List<Ship> createShipList() {
        List<Ship> shipList = new LinkedList<>();

        shipList.add(ShipFactory.create(ShipType.fourMast));
        shipList.add(ShipFactory.create(ShipType.threeMast));
        shipList.add(ShipFactory.create(ShipType.twoMast));
        shipList.add(ShipFactory.create(ShipType.oneMast));

        return shipList;
    }
}
