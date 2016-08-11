package dreamteam.battleship.service.springcontroller.preparation;

import dreamteam.battleship.logic.board.Direction;
import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.logic.movement.PlaceShipManager;
import dreamteam.battleship.logic.ship.Ship;
import dreamteam.battleship.logic.ship.ShipType;
import dreamteam.battleship.service.springcontroller.model.response.ModelResponseTestUtil;
import dreamteam.battleship.service.springcontroller.model.Player;
import dreamteam.battleship.service.springcontroller.model.response.Response;
import org.springframework.mock.web.MockHttpSession;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by egolesor on 18.07.16.
 */
public class PlacingShipTest {

    @Test
    public void placing3SizeShipOnField1(){
        PlacingShip placingShip = new PlacingShip();
        MockHttpSession session = new MockHttpSession();
        placingShip.player = new Player("","", "1", new LinkedList<Ship>());
        placingShip.session = session;
        Response response = placingShip.place(ShipType.threeMast, 1, Direction.VERTICAL);

        assertTrue(ModelResponseTestUtil.placeStatus(response).equals(MovementStatus.SUCCESS));
    }

    @Test
    public void placing3SizeShipOnField1TwoTimesWillError(){
        PlacingShip placingShip = new PlacingShip();

        MockHttpSession session = new MockHttpSession();
        placingShip.player = new Player("","", "1", new LinkedList<Ship>());
        placingShip.session = session;
        placingShip.place(ShipType.threeMast, 1, Direction.VERTICAL);
        Response response = placingShip.place(ShipType.threeMast, 1, Direction.VERTICAL);

        assertEquals(ModelResponseTestUtil.placeStatus(response), MovementStatus.TRY_AGAIN);
    }

    @Test
    public void checkIfThePlacedShipIsDeleted(){
        PlacingShip placingShip = new PlacingShip();

        MockHttpSession session = new MockHttpSession();

        // mock the manager
        placingShip.manager = mock(PlaceShipManager.class);
        when(placingShip.manager.tryPutShip(any(Ship.class),anyInt(), any(Direction.class))).thenReturn(MovementStatus.SUCCESS);

        // set the list as we want
        List<ShipType> shipLists = new LinkedList<>();
        shipLists.add(ShipType.fourMast);
        shipLists.add(ShipType.fourMast);
        placingShip.availableShips = shipLists;
        placingShip.player = new Player("","", "1", new LinkedList<Ship>());

        // placing the result list must be empty
        placingShip.session = session;
        placingShip.place(ShipType.fourMast, 1, Direction.VERTICAL);
        placingShip.place(ShipType.fourMast, 1, Direction.VERTICAL);

        assertTrue(placingShip.availableShips.isEmpty());

    }
}
