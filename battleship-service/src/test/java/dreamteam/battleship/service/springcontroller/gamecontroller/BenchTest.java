package dreamteam.battleship.service.springcontroller.gamecontroller;

import dreamteam.battleship.service.springcontroller.model.GameMode;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Testing the bench and his behaves
 */
public class BenchTest {
    @Test
    public void checkIf(){
        Bench bench = new Bench();

        assertTrue(bench.isFree(GameMode.NORMAL_MODE));
    }

    @Test
    public void testLettingAControllerToSit(){
        Bench bench = new Bench();

        bench.letSit(mock(IGameController.class), GameMode.NORMAL_MODE);

        assertFalse(bench.isFree(GameMode.NORMAL_MODE));
        bench.pickController(GameMode.NORMAL_MODE);
        assertTrue(bench.isFree(GameMode.NORMAL_MODE));
    }

    @Test
    public void letSit2ControllerAndPickThem(){

        Bench bench = new Bench();

        bench.letSit(mock(IGameController.class), GameMode.GUN_SALUTE_MODE);
        bench.letSit(mock(IGameController.class), GameMode.NORMAL_MODE);

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertFalse(bench.isFree(GameMode.NORMAL_MODE));
        softAssert.assertFalse(bench.isFree(GameMode.GUN_SALUTE_MODE));

        bench.pickController(GameMode.GUN_SALUTE_MODE);
        bench.pickController(GameMode.NORMAL_MODE);

        softAssert.assertTrue(bench.isFree(GameMode.NORMAL_MODE));
        softAssert.assertTrue(bench.isFree(GameMode.GUN_SALUTE_MODE));

        softAssert.assertAll();
    }

}
