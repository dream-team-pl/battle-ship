package dreamteam.battleship.service.springcontroller.util;

import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.service.springcontroller.gamecontroller.IGameController;
import dreamteam.battleship.service.springcontroller.model.Player;
import dreamteam.battleship.service.springcontroller.preparation.PlacingShip;
import dreamteam.battleship.service.springcontroller.preparation.PlayerOrganizer;
import dreamteam.battleship.service.springcontroller.registration.Registration;

import javax.servlet.http.HttpSession;
/**
 * Created by ehsan on 11.08.16.
 */
public class SessionUtil implements SessionAttrKey{

    public static Player getMyPlayer(HttpSession session){
        return ((Registration)session.getAttribute(REGISTRATION_SERVICE)).getPlayer();
    }

    public static IGameController getMyController(HttpSession session){
        return ((PlayerOrganizer)session.getAttribute(PREPARING_SERVICE)).myController();
    }

    public static MovementManager getMyManager(HttpSession session){
        return ((PlacingShip)session.getAttribute(PLACING_SERVICE)).myManager();
    }

    public static void restart(HttpSession session) {
        session.removeAttribute(SessionAttrKey.PLACING_SERVICE);
        session.removeAttribute(SessionAttrKey.PLAYING_SERVICE);
        session.removeAttribute(SessionAttrKey.PREPARING_SERVICE);
        Player player = ((Registration)session.getAttribute(SessionAttrKey.REGISTRATION_SERVICE)).getPlayer();
        player.clearList();
    }

}
