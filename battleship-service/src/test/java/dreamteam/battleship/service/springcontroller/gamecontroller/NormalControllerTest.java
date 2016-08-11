package dreamteam.battleship.service.springcontroller.gamecontroller;

import dreamteam.battleship.logic.movement.DamageManager;
import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.model.Player;
import dreamteam.battleship.service.springcontroller.model.response.Response;
import dreamteam.battleship.service.springcontroller.model.response.ShootingResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by daniel on 09.08.16.
 */
public class NormalControllerTest {
    Player player1;
    Player player2;

    MovementManager manager1;
    MovementManager manager2;

    List<Integer> arg;

    @BeforeClass
    public void initialize() {
        player1 = mock(Player.class);
        player2 = mock(Player.class);

        manager1 = mock(DamageManager.class);
        manager2 = mock(DamageManager.class);

        arg = Arrays.asList(7);
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
        Response shootingResult = gc.handleShot(arg, player1);

        // then
        assertEquals(((ShootingResult)shootingResult).status, MovementStatus.SUCCESS);

        //when
        ((NormalController)gc).currentManager = manager2;
        ((NormalController)gc).currentPlayer=player2;
        shootingResult = gc.handleShot(arg, player2);

        // then
        assertEquals(((ShootingResult)shootingResult).status, MovementStatus.TRY_AGAIN);
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

    @Test
    public void checkIfIsReadyToPlay() {
        // given
        IGameController gc = new NormalController(player1, manager1);

        // when
        boolean result = gc.isReadyToPlay();

        //then
        assertFalse("Game is not ready to play", result);

        //given
        gc.addPlayer2(player2, manager2);

        // when
        result = gc.isReadyToPlay();

        //then
        assertTrue("Game is ready to play", result);

    }

    @Test
    public void checkIfWeCanSetTheWinner() {
        // given
        IGameController gc = new NormalController(player1, manager1);
        gc.addPlayer2(player2, manager2);

        // when
        when(manager1.isThePlayerWon()).thenReturn(true);
        gc.trySetWinner();

        // then
        assertEquals(gc.getWinner(), player1);
    }
}
