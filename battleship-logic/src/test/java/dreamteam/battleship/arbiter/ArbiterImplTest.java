package dreamteam.battleship.arbiter;

import dreamteam.battleship.ship.Ship;
import dreamteam.battleship.ship.ShipImpl;
import dreamteam.battleship.ship.ShipType;
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
                {ShipImpl.shipFactory(ShipType.fourMast), null},
                {ShipImpl.shipFactory(ShipType.threeMast), null},
                {ShipImpl.shipFactory(ShipType.fourMast), ShipImpl.shipFactory(ShipType.threeMast)},
                {ShipImpl.shipFactory(ShipType.fourMast), ShipImpl.shipFactory(ShipType.oneMast)},
                {ShipImpl.shipFactory(ShipType.twoMast), ShipImpl.shipFactory(ShipType.oneMast)},
                {ShipImpl.shipFactory(ShipType.twoMast), ShipImpl.shipFactory(ShipType.oneMast)}
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
        ArbiterImpl arbiter = new ArbiterImpl(shipList);

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
        ArbiterImpl arbiter = new ArbiterImpl(shipList);

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
        ArbiterImpl arbiter = new ArbiterImpl(shipList);

        // when
        // ...

        // then
        assertFalse(arbiter.isWinner());
    }

    /**
     * initialize new List that is consist of 4 ships
     */
    public List<Ship> createShipList() {
        List<Ship> shipList = new LinkedList<Ship>();

        shipList.add(ShipImpl.shipFactory(ShipType.fourMast));
        shipList.add(ShipImpl.shipFactory(ShipType.threeMast));
        shipList.add(ShipImpl.shipFactory(ShipType.twoMast));
        shipList.add(ShipImpl.shipFactory(ShipType.oneMast));

        return shipList;
    }
}
