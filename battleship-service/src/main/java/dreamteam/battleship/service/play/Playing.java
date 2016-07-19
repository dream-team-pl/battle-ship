package dreamteam.battleship.service.play;

import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.preparation.GameController;
import dreamteam.battleship.service.preparation.PlayerOrganizer;
import dreamteam.battleship.service.registration.Player;
import dreamteam.battleship.service.registration.Registration;
import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by egolesor on 19.07.16.
 */
@Api
@RestController
@Scope("session")
@CrossOrigin("*")
public class Playing {

    final static Logger logger = Logger.getLogger(Playing.class);

    protected GameController controller;

    private Player player;
    @InitBinder
    public void init(HttpSession session){
        controller = initController(session);
        controller.startGame();

        player=myPlayer(session);
    }

    /**
     * Responsible to handling the request to for the shooting.
     * @param session
     * @param fieldNumber
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/shoot")
    public ShootResponse shoot(HttpSession session,
                               @RequestParam(name = "fieldNumber") int fieldNumber) {

        ShootResponse response;
        if(controller.getWinner()==null){
            response = handleShoot(session, fieldNumber);
            logger.debug("shoot status is " + response.status);
        }else {
            response= winnerResponse();
        }
        return response;
    }

    private ShootResponse winnerResponse() {
        return
                new ShootResponse(MovementStatus.WON, controller.getWinner(), controller.getBoardForPlayer(player));
    }

    /**
     * Will shoot the concrete field and check the result of the shooting.
     * @param session
     * @param fieldNumber
     * @return
     */
    private ShootResponse handleShoot(HttpSession session, int fieldNumber) {
        logger.debug("Handling the shoot");
        MovementStatus status = controller.shoot(fieldNumber, player);
        if(mustPlayNext(status)){
            controller.nextPlayer();
        }
        return new ShootResponse(status, controller.getBoardForPlayer(player));
    }

    /**
     * It checks if player done his action.
     * @param status
     * @return
     */
    private boolean mustPlayNext(MovementStatus status) {
        return !( status.equals(MovementStatus.INVALID_MOVEMENT) || status.equals(MovementStatus.SUCCESS) || status.equals(MovementStatus.WON));
    }

    /**
     * Initializing the controller. getting the controller from the session that created earlier
     * @param session
     * @return
     */
    private GameController initController(HttpSession session) {
        logger.debug("initializing the controller for the playew");
        return ((PlayerOrganizer)session.getAttribute("playerOrganizer")).myController();
    }

    /**
     * Initializing the player. getting the player from the session that created earlier
     * @param session
     * @return
     */
    private Player myPlayer(HttpSession session) {
        logger.debug("getting the player in the playing");
        return ((Registration)session.getAttribute("registration")).getPlayer();
    }
}
