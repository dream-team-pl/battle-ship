package dreamteam.battleship.service.springcontroller.gamecontroller;

import dreamteam.battleship.logic.movement.DamageManager;
import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.model.Player;
import dreamteam.battleship.service.springcontroller.model.response.Shoot;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by daniel on 09.08.16.
 */
public class GunSaluteControllerTest {
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

        arg = Arrays.asList(7, 18, 19, 21, 77);
    }

    @Test
    public void checkIfIsMyTurn() {
        // given
        IGameController gc = new GunSaluteController(player1, manager1);
        gc.addPlayer2(player2, manager2);


        // when
        boolean myTurn = gc.isMyTurn(player1);
        boolean myTurn2 = gc.isMyTurn(player2);

        // then
        assertTrue("It's player's turn", myTurn);
        assertTrue("It's player's turn", myTurn2);

        // when
        when(manager1.getNumberOfTurn()).thenReturn(1);
        myTurn = gc.isMyTurn(player1);
        myTurn2 = gc.isMyTurn(player2);

        // then
        assertFalse("It's not player's turn", myTurn);
        assertFalse("It's not player's turn", myTurn2);
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
        Shoot shoot = gc.handleShot(arg, player1);

        // then
        assertEquals(shoot.status, MovementStatus.SUCCESS);

        //when
        ((NormalController)gc).currentManager = manager2;
        ((NormalController)gc).currentPlayer=player2;
        shoot = gc.handleShot(arg, player2);

        // then
        assertEquals(shoot.status, MovementStatus.TRY_AGAIN);
    }
}
