package dreamteam.battleship.service.springcontroller.gamecontroller;

import dreamteam.battleship.logic.movement.DamageManager;
import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.service.springcontroller.model.GameMode;
import dreamteam.battleship.service.springcontroller.model.Player;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.mockito.Mockito.mock;

/**
 * Created by daniel on 12.08.16.
 */
public class GameControllerBuilderTest {

    @Test
    public void checkGameControllerInstanceCreator() {
        Player player = mock(Player.class);
        MovementManager movementManager = mock(DamageManager.class);

        IGameController gameController = GameControllerBuilder.gameControllerInstance(player, movementManager, GameMode.NORMAL_MODE);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(gameController instanceof NormalController);

        gameController = GameControllerBuilder.gameControllerInstance(player, movementManager, GameMode.GUN_SALUTE_MODE);

        softAssert.assertTrue(gameController instanceof GunSaluteController);
        softAssert.assertAll();
    }
}