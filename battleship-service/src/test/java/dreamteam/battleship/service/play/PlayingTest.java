package dreamteam.battleship.service.play;

import dreamteam.battleship.logic.movement.DamageManager;
import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.preparation.GameController;
import dreamteam.battleship.service.preparation.PlacingShip;
import dreamteam.battleship.service.preparation.PlayerOrganizer;
import dreamteam.battleship.service.registration.Player;
import dreamteam.battleship.service.registration.Registration;
import org.springframework.mock.web.MockHttpSession;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
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
        playing.init(session);
        ShootResponse status = playing.shoot(session, 12);

        assertTrue(status.status.equals(MovementStatus.SUCCESS));
    }
}
