package dreamteam.battleship.service.springcontroller.play;

import dreamteam.battleship.logic.movement.DamageManager;
import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.gamecontroller.IGameController;
import dreamteam.battleship.service.springcontroller.model.Player;
import dreamteam.battleship.service.springcontroller.model.response.ModelResponseTestUtil;
import dreamteam.battleship.service.springcontroller.model.response.Response;
import dreamteam.battleship.service.springcontroller.preparation.PlacingShip;
import dreamteam.battleship.service.springcontroller.preparation.PlayerOrganizer;
import dreamteam.battleship.service.springcontroller.registration.Registration;
import org.springframework.mock.web.MockHttpSession;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
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
        IGameController controller = mock(IGameController.class);
        when(controller.handleShot(anyListOf(Integer.class), any(Player.class))).thenReturn(Response.shootingResult(MovementStatus.SUCCESS, null));
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
        Response status = playing.shoot(Arrays.asList(12));

        assertTrue((ModelResponseTestUtil.shootingStatus(status)).equals(MovementStatus.SUCCESS));
    }
}
