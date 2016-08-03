package dreamteam.battleship.service.springcontroller.play;

import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.BattleShipServiceBase;
import dreamteam.battleship.service.springcontroller.preparation.GameController;
import dreamteam.battleship.service.springcontroller.preparation.PlayerOrganizer;
import dreamteam.battleship.service.springcontroller.registration.Player;
import dreamteam.battleship.service.springcontroller.registration.Registration;
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
        super.init(session);
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

    @RequestMapping(method = RequestMethod.GET, path = "/turnstatus")
    public TurnStatusResponse turnStatus(HttpSession session){
        // this method will call iterally, so i dont think that logging is a good idea
        return
                new TurnStatusResponse(controller.getBoardForPlayer(player), controller.isMyTurn(player), controller.getWinner());
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