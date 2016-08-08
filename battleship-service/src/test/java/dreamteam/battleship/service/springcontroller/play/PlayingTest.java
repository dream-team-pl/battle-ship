package dreamteam.battleship.service.springcontroller.play;

import dreamteam.battleship.logic.movement.DamageManager;
import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.preparation.GameController;
import dreamteam.battleship.service.springcontroller.preparation.PlacingShip;
import dreamteam.battleship.service.springcontroller.preparation.PlayerOrganizer;
import dreamteam.battleship.service.springcontroller.registration.Player;
import dreamteam.battleship.service.springcontroller.registration.Registration;
import org.springframework.mock.web.MockHttpSession;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by egolesor on 19.07.16.
 */
public class PlayingTest {

    Playing playing;
    Registration registration;
    PlacingShip placingShip;
    PlayerOrganizer organizer;
    GameController controller;

    @BeforeClass
    public void initialize() {
        playing = new Playing();
        registration = mock(Registration.class);
        when(registration.getPlayer()).thenReturn(mock(Player.class));

        placingShip = mock(PlacingShip.class);
        when(placingShip.myManager()).thenReturn(mock(DamageManager.class));

        organizer = mock(PlayerOrganizer.class);
        controller = mock(GameController.class);
    }

    @Test
    public void testIfPlayerTermAreCorrect(){

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

        for(int shoot: shootingList) {
            when(controller.gunSaluteShoot(eq(shoot), any(Player.class))).thenReturn(MovementStatus.TRY_AGAIN);
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
        GunSaluteShootResponse status = playing.gunSaluteShoot(shootingList);

        assertTrue(status.status.equals(MovementStatus.SALVA_MODE));
    }
}
