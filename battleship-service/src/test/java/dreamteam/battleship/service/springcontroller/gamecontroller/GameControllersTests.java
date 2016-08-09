package dreamteam.battleship.service.springcontroller.gamecontroller;

import dreamteam.battleship.logic.board.Board;
import dreamteam.battleship.logic.movement.DamageManager;
import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.model.Player;
import dreamteam.battleship.service.springcontroller.model.response.Shoot;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by daniel on 09.08.16.
 */
public class GameControllersTests {
    Player player1;
    Player player2;

    MovementManager manager1;
    MovementManager manager2;

    @BeforeClass
    public void initialize() {
        player1 = mock(Player.class);
        player2 = mock(Player.class);

        manager1 = mock(DamageManager.class);
        manager2 = mock(DamageManager.class);
    }


    @Test
    public void testIfControllerStartsTheGameProperly(){
        IGameController gc = new NormalController(player1, manager1);
        gc.addPlayer2(player2, manager2);

        Board board1 = mock(Board.class);
        Board board2 = mock(Board.class);

        when(manager1.getBoard()).thenReturn(board1);
        when(manager2.getBoard()).thenReturn(board2);

        gc.startGame();

        assertTrue(true);
    }

    /**
     * Checking the winner
     */
    @Test
    public void testIfWinnerIsCachedWhenPlayer1Won(){
        when(manager1.isThePlayerWon()).thenReturn(true);
        when(manager1.damage(anyInt())).thenReturn(MovementStatus.INVALID_MOVEMENT);


        when(manager2.isThePlayerWon()).thenReturn(false);
        when(manager2.damage(anyInt())).thenReturn(MovementStatus.INVALID_MOVEMENT);

        IGameController gc = new NormalController(player1, manager1);
        gc.addPlayer2(player2, manager2);

        ((NormalController)gc).currentManager = manager1;
        ((NormalController)gc).currentPlayer=player1;

        gc.shotResponse(1,player1);

        assertTrue(gc.getWinner().equals(player1));
    }

    @Test
    public void checkResponseAfterShot() {
        // given
        when(manager1.isThePlayerWon()).thenReturn(false);
        when(manager1.damage(anyInt())).thenReturn(MovementStatus.SUCCESS);


        when(manager2.isThePlayerWon()).thenReturn(false);
        when(manager2.damage(anyInt())).thenReturn(MovementStatus.TRY_AGAIN);

        IGameController gc = new NormalController(player1, manager1);
        gc.addPlayer2(player2, manager2);

        // when
        ((NormalController)gc).currentManager = manager1;
        ((NormalController)gc).currentPlayer=player1;
        Shoot shoot = gc.handleShot(7, player1);

        // then
        assertEquals(shoot.status, MovementStatus.SUCCESS);

        //when
        ((NormalController)gc).currentManager = manager2;
        ((NormalController)gc).currentPlayer=player2;
        shoot = gc.handleShot(7, player2);

        // then
        assertEquals(shoot.status, MovementStatus.TRY_AGAIN);
    }

    @Test
    public void checkIfIsMyTurn() {
        // given

        IGameController gc = new NormalController(player1, manager1);
        gc.addPlayer2(player2, manager2);
        ((NormalController)gc).currentPlayer = player1;

        // when

        boolean myTurn = gc.isMyTurn(player1);

        //then

        assertTrue("It's player's turn", myTurn);


        // when

        myTurn = gc.isMyTurn(player2);

        //then

        assertFalse("It's not player's turn", myTurn);
    }
}
