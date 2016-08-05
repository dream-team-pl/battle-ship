package dreamteam.battleship.service.springcontroller.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * Responsible to restart the game that player started
 */
@Component("RestarterUtil")
public class RestarterUtil {

    public void restart(HttpSession session) {
        session.removeAttribute(SessionAttrKey.PLACING_SERVICE);
        session.removeAttribute(SessionAttrKey.PLAYING_SERVICE);
        session.removeAttribute(SessionAttrKey.PREPARING_SERVICE);
    }
}
