package dreamteam.battleship.service.play;

import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.preparation.GameController;
import dreamteam.battleship.service.preparation.PlayerOrganizer;
import dreamteam.battleship.service.registration.Player;
import dreamteam.battleship.service.registration.Registration;

import javax.servlet.http.HttpSession;

/**
 * Created by egolesor on 19.07.16.
 */
public class Playing {

    protected GameController controller;

    public ShootResponse shoot(HttpSession session, int fieldNumber) {

        myController(session);
        MovementStatus status = controller.shoot(fieldNumber, myPlayer(session));
        controller.nextPlayer();
        return new ShootResponse(status);
    }

    private void myController(HttpSession session) {
        if(controller==null){
            controller = ((PlayerOrganizer)session.getAttribute("playerOrganizer")).myController();
            controller.startGame();
        }
    }

    private Player myPlayer(HttpSession session) {
        return ((Registration)session.getAttribute("registration")).getPlayer();
    }
}
