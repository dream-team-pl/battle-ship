package dreamteam.battleship.logic.movement;

import dreamteam.battleship.logic.arbiter.Arbiter;
import dreamteam.battleship.logic.arbiter.MovementContainer;
import dreamteam.battleship.logic.board.Board;
import org.testng.annotations.Test;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;


/**
 *
 */
public class DamageManagerTest {

    @Test
    public void test_if_invalid_movements_are_recognized(){

        Board boardMock = mock(Board.class);
        MovementContainer containerMock = mock(MovementContainer.class);
        Arbiter arbiterMock = mock(Arbiter.class);

        when(containerMock.containsMovement(anyInt())).thenReturn(true);

        DamageManager manager = new DamageManager(boardMock, containerMock, arbiterMock);

        MovementStatus result = manager.damage(12);

        assertEquals(result, MovementStatus.INVALID_MOVEMENT);
    }

    @Test
    public void test_if_winner_is_recognized(){

        Board boardMock = mock(Board.class);
        MovementContainer containerMock = mock(MovementContainer.class);
        Arbiter arbiterMock = mock(Arbiter.class);

        when(containerMock.containsMovement(anyInt())).thenReturn(false);
        when(arbiterMock.isWinner()).thenReturn(true);

        DamageManager manager = new DamageManager(boardMock, containerMock, arbiterMock);

        MovementStatus result = manager.damage(12);

        assertEquals(result, MovementStatus.WON);
    }

    @Test
    public void test_if_not_damaged_returns_tryAgain(){

        Board boardMock = mock(Board.class);
        MovementContainer containerMock = mock(MovementContainer.class);
        Arbiter arbiterMock = mock(Arbiter.class);

        when(containerMock.containsMovement(anyInt())).thenReturn(false);
        when(boardMock.shipOn(anyInt())).thenReturn(null);

        DamageManager manager = new DamageManager(boardMock, containerMock, arbiterMock);

        MovementStatus result = manager.damage(12);

        assertEquals(result, MovementStatus.TRY_AGAIN);
    }


}
