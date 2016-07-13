package dreamteam.battleship.movement;

import dreamteam.battleship.board.Board;
import dreamteam.battleship.board.Direction;
import dreamteam.battleship.ship.BattleShip;
import dreamteam.battleship.ship.Ship;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Created by ehsan on 12.07.16.
 */
public class PlaceShipManagementTest {

    /**
     * test_if_recognize_that_there_is_no_place_for_the_ship
     */
    @Test
    public void putShipIfThereIsNoPlace(){
        Board board = mock(Board.class);
        MovementManager manager = new PlaceShipManager(board);
        Ship ship = mock(Ship.class);
        when(board.isPlaceForTheShip(ship, 12, Direction.HORIZONTAL)).thenReturn(false);

        assertEquals(manager.tryPutShip(ship, 12, Direction.HORIZONTAL), MovementStatus.TRY_AGAIN);
    }
    /**
     * test_if_recognize_that_the_ship_is_placed
     */
    @Test
    public void putShipSuccessfully(){
        Board board = mock(Board.class);
        MovementManager manager = new PlaceShipManager(board);
        Ship ship = new BattleShip(12321);

        when(board.isPlaceForTheShip(ship, 2, Direction.HORIZONTAL)).thenReturn(true);
        when(board.maxSize()).thenReturn(10);
        assertEquals(manager.tryPutShip(ship, 2, Direction.HORIZONTAL), MovementStatus.SUCCESS);
    }

    /**
     * test_if_recognize_that_there_is_no_place_for_the_ship
     */
    @Test
    public void putFieldNumberIsInvalid(){
        Board board = mock(Board.class);
        Ship ship = mock(Ship.class);

        MovementManager manager = new PlaceShipManager(board);

        assertEquals(manager.tryPutShip(ship, -1, Direction.HORIZONTAL), MovementStatus.INVALID_MOVEMENT);
    }

}
