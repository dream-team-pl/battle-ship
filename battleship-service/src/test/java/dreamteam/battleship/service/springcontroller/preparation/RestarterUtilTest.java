package dreamteam.battleship.service.springcontroller.preparation;

import dreamteam.battleship.service.springcontroller.play.Playing;
import dreamteam.battleship.service.springcontroller.util.RestarterUtil;
import dreamteam.battleship.service.springcontroller.util.SessionAttrKey;
import org.springframework.mock.web.MockHttpSession;
import org.testng.annotations.Test;

import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
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


        RestarterUtil restarterUtil = new RestarterUtil();

        restarterUtil.restart(session);

        assertTrue(session.getAttribute(SessionAttrKey.PLACING_SERVICE)==null);
        assertTrue(session.getAttribute(SessionAttrKey.PREPARING_SERVICE)==null);
        assertTrue(session.getAttribute(SessionAttrKey.PLAYING_SERVICE)==null);
    }
}
