package dreamteam.battleship.service.springcontroller.play;

import dreamteam.battleship.logic.movement.DamageManager;
import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.preparation.GameController;
import dreamteam.battleship.service.springcontroller.preparation.PlacingShip;
import dreamteam.battleship.service.springcontroller.preparation.PlayerOrganizer;
import dreamteam.battleship.service.springcontroller.registration.Player;
import dreamteam.battleship.service.springcontroller.registration.Registration;
import org.springframework.mock.web.MockHttpSession;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by egolesor on 19.07.16.
 */
public class PlayingTest {

    @Test
    public void testIfPlayerTermAreCorrect(){
        Playing playing = new Playing();
        Registration registration = mock(Registration.class);
        when(registration.getPlayer()).thenReturn(mock(Player.class));

        PlacingShip placingShip = mock(PlacingShip.class);
        when(placingShip.myManager()).thenReturn(mock(DamageManager.class));

        PlayerOrganizer organizer = mock(PlayerOrganizer.class);
        GameController controller = mock(GameController.class);
        when(controller.shoot(anyInt(), any(Player.class))).thenReturn(MovementStatus.SUCCESS);
        when(controller.getWinner()).thenReturn(null);
        when(organizer.myController()).thenReturn(controller);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("registration", registration);
        session.setAttribute("placingShip", placingShip);
        session.setAttribute("playerOrganizer", organizer);
        playing.session = session;
        try {
            playing.afterPropertiesSet();
        } catch (Exception e) {
            assertTrue(false);
        }
        ShootResponse status = playing.shoot(12);

        assertTrue(status.status.equals(MovementStatus.SUCCESS));
    }
    
    @DataProvider
    public Object[][] shootingList (){
        return new Object[][] {
                {new LinkedList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7))},
                {new LinkedList<Integer>(Arrays.asList(10, 2, 35, 42, 51, 68, 77))},
                {new LinkedList<Integer>(Arrays.asList(11, 22, 33, 44, 55, 66, 73))},
                {new LinkedList<Integer>(Arrays.asList(15, 12, 53, 84, 51, 62, 99))}
        };
    }
    @Test(dataProvider = "shootingList")
    public void testPlayerSalvaShooting(List<Integer> shootingList) {
        Playing playing = new Playing();
        Registration registration = mock(Registration.class);
        when(registration.getPlayer()).thenReturn(mock(Player.class));

        PlacingShip placingShip = mock(PlacingShip.class);
        when(placingShip.myManager()).thenReturn(mock(DamageManager.class));

        PlayerOrganizer organizer = mock(PlayerOrganizer.class);
        GameController controller = mock(GameController.class);

        for(int shoot: shootingList) {
            when(controller.salvaShoot(eq(shoot), any(Player.class))).thenReturn(MovementStatus.TRY_AGAIN);
        }

        when(controller.getWinner()).thenReturn(null);
        when(organizer.myController()).thenReturn(controller);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("registration", registration);
        session.setAttribute("placingShip", placingShip);
        session.setAttribute("playerOrganizer", organizer);
        playing.session = session;

        try {
            playing.afterPropertiesSet();
        } catch(Exception e) {
            assertTrue(false);
        }
        SalvaShootResponse status = playing.salvaShoot(shootingList);

        System.out.println(status.status);
        assertTrue(status.status.equals(MovementStatus.SALVA_MODE));
    }
}
