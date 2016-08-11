package dreamteam.battleship.service.springcontroller.preparation;

import dreamteam.battleship.logic.ship.Ship;
import dreamteam.battleship.service.springcontroller.model.Player;
import dreamteam.battleship.service.springcontroller.play.Playing;
import dreamteam.battleship.service.springcontroller.registration.Registration;

import dreamteam.battleship.service.springcontroller.util.SessionAttrKey;
import dreamteam.battleship.service.springcontroller.util.SessionUtil;
import org.springframework.mock.web.MockHttpSession;
import org.testng.annotations.Test;

import javax.servlet.http.HttpSession;

import java.util.LinkedList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertTrue;

/**
 * Created by ehsan on 04.08.16.
 */
public class RestarterUtilTest {
    @Test
    public void testIfRestartClearTheSession(){
        HttpSession session = new MockHttpSession();

        session.setAttribute(SessionAttrKey.PLACING_SERVICE, mock(PlacingShip.class));
        session.setAttribute(SessionAttrKey.PREPARING_SERVICE, mock(PlayerOrganizer.class));
        session.setAttribute(SessionAttrKey.PLAYING_SERVICE, mock(Playing.class));
        Registration registration = mock(Registration.class);
        Player player = new Player("","","",new LinkedList<Ship>());
        when(registration.getPlayer()).thenReturn(player);
        session.setAttribute(SessionAttrKey.REGISTRATION_SERVICE, registration);

        SessionUtil.restart(session);

        assertTrue(session.getAttribute(SessionAttrKey.PLACING_SERVICE)==null);
        assertTrue(session.getAttribute(SessionAttrKey.PREPARING_SERVICE)==null);
        assertTrue(session.getAttribute(SessionAttrKey.PLAYING_SERVICE)==null);
    }
}
