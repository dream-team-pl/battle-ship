package dreamteam.battleship.service.play;

import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.BattleShipServiceBase;
import dreamteam.battleship.service.preparation.GameController;
import dreamteam.battleship.service.preparation.PlayerOrganizer;
import dreamteam.battleship.service.registration.Player;
import dreamteam.battleship.service.registration.Registration;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static dreamteam.battleship.loggerhelper.LoggerStatics.END;
import static dreamteam.battleship.loggerhelper.LoggerStatics.START;

/**
 * Responsible to play and shooting the fields
 */
@RestController
@Scope("session")
public class Playing extends BattleShipServiceBase {

    final static Logger logger = Logger.getLogger(Playing.class);

    protected GameController controller;

    private Player player;
    @InitBinder
    public void init(HttpSession session){
        super.init();
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

        logger.debug(START);
        ShootResponse response;
        // check if there is sense to shoot
        if(controller.getWinner()==null){
            response = handleShoot(session, fieldNumber);
            logger.debug("shoot status is " + response.status);
        }else {
            response= winnerResponse();
        }
        logger.debug(END);
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
        ShootResponse response;
        MovementStatus status = controller.shoot(fieldNumber, player);
        if(mustPlayNext(status)){
            controller.nextPlayer();
        }
        // check if he is the winnner
        //FIXME In the future when we will use web sockets we are going to send event, we need to delete this line
        if(MovementStatus.WON.equals(status)){
            response = winnerResponse();
        }else {
            response = new ShootResponse(status, controller.getBoardForPlayer(player));
        }
        return response;
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
