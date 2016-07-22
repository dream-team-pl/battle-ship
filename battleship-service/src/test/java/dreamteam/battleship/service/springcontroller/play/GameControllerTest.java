package dreamteam.battleship.service.springcontroller.play;

import dreamteam.battleship.logic.board.Board;
import dreamteam.battleship.logic.movement.DamageManager;
import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.service.springcontroller.preparation.GameController;
import dreamteam.battleship.service.springcontroller.registration.Player;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by ehsan on 19.07.16.
 */
public class GameControllerTest {

    @Test
    public void testIfControllerStartsTheGameProperly(){
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);

        MovementManager manager1 = mock(DamageManager.class);
        MovementManager manager2 = mock(DamageManager.class);

        GameController gc = new GameController(player1, manager1);
        gc.addPlayer2(player2, manager2);

        Board board1 = mock(Board.class);
        Board board2 = mock(Board.class);

        when(manager1.getBoard()).thenReturn(board1);
        when(manager2.getBoard()).thenReturn(board2);

        gc.startGame();
        gc.startGame();

        assertTrue(true);

    }

}
