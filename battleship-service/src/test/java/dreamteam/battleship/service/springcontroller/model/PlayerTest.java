package dreamteam.battleship.service.springcontroller.model;

import dreamteam.battleship.logic.ship.Ship;
import dreamteam.battleship.logic.ship.ShipFactory;
import dreamteam.battleship.logic.ship.ShipType;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by daniel on 12.08.16.
 */
public class PlayerTest {

    @Test
    public void checkEquals() {
        Player player1 = new Player("name", "surname", "123", null);
        Player player2 = new Player("name1", "surname2", "123", null);

        assertEquals(player1, player2);
    }

    @Test
    public void checkHashCode() {
        Player player1 = new Player("name", "surname", "123", null);
        Player player2 = new Player("name1", "surname2", "123", null);

        assertEquals(player1.hashCode(), player2.hashCode());
    }

    @Test
    public void checkShipList() {

        List<Ship> shipList = new ArrayList<>(Arrays.asList(ShipFactory.create(ShipType.fourMast), ShipFactory.create(ShipType.threeMast)));

        Player player1 = new Player("name", "surname", "123", shipList);
        Player player2 = new Player("name1", "surname2", "123", shipList);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(player2.shipList(), shipList);

        Ship shipToAdd = ShipFactory.create(ShipType.oneMast);
        shipList.add(shipToAdd);
        player1.addShip(shipToAdd);

        softAssert.assertEquals(player1.shipList(), shipList);
        softAssert.assertAll();
    }
}