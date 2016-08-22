package dreamteam.battleship.service.springcontroller.gamecontroller;

import dreamteam.battleship.logic.movement.DamageManager;
import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.model.Player;
import dreamteam.battleship.service.springcontroller.model.response.ModelResponseTestUtil;
import dreamteam.battleship.service.springcontroller.model.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
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
        gc.startGame();


        // when
        boolean myTurn = gc.isMyTurn(player1);
        boolean myTurn2 = gc.isMyTurn(player2);

        // then
        assertTrue("It's player's turn", myTurn);
        assertTrue("It's player's turn", myTurn2);

        // when
        myTurn = gc.isMyTurn(player1);
        myTurn2 = gc.isMyTurn(player2);

        // then
        assertTrue("It's player's turn", myTurn);
        assertTrue("It's player's turn", myTurn2);
    }

    @Test
    public void checkResponseAfterShot() {
        // given
        when(manager1.isThePlayerWon()).thenReturn(false);
        when(manager1.damage(anyInt())).thenReturn(MovementStatus.SUCCESS);


        when(manager2.isThePlayerWon()).thenReturn(false);
        when(manager2.damage(anyInt())).thenReturn(MovementStatus.TRY_AGAIN);

        IGameController gc = new GunSaluteController(player1, manager1);
        gc.addPlayer2(player2, manager2);

        Response shoot = gc.handleShot(arg, player1);

        // then
        assertEquals(ModelResponseTestUtil.shootingStatus(shoot), MovementStatus.GUN_SALUTE_MODE);

        //when
        shoot = gc.handleShot(arg, player2);

        // then
        assertEquals(ModelResponseTestUtil.shootingStatus(shoot), MovementStatus.GUN_SALUTE_MODE);
    }

    @Test
    public void checkIfTurnStatusWorks() {
        IGameController gc = new GunSaluteController(player1, manager1);
        gc.addPlayer2(player2, manager2);

        Map<Integer, Boolean> movements = getMovements();

        when(manager1.numberOfPlayerShots()).thenReturn(4);
        when(manager2.numberOfPlayerShots()).thenReturn(4);
        when(manager1.getMovements()).thenReturn(movements);
        when(manager2.getMovements()).thenReturn(movements);

        PlayerQueue playerQueue = new PlayerQueue();
        playerQueue.add(player1, player2, manager2.numberOfPlayerShots(), manager1.numberOfPlayerShots());
        ((GunSaluteController)gc).playerQueue = playerQueue;

        Response response1 = gc.turnStatus(player1);
        Response response2 = gc.turnStatus(player2);

        assertEquals(ModelResponseTestUtil.numberOfShots(response1), 4);
        assertEquals(ModelResponseTestUtil.numberOfShots(response2), 4);

        assertEquals(ModelResponseTestUtil.myDamages(response1), movements);
        assertEquals(ModelResponseTestUtil.myDamages(response2), movements);
    }

    private Map<Integer, Boolean> getMovements() {
        Map<Integer, Boolean> movements = new HashMap<>();
        movements.put(1, true);
        movements.put(2, false);
        movements.put(8, true);
        movements.put(22, true);

        return movements;
    }
}
