package dreamteam.battleship.service.springcontroller.play;

import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.BattleShipServiceBase;
import dreamteam.battleship.service.springcontroller.gamecontroller.IGameController;
import dreamteam.battleship.service.springcontroller.model.response.ShootingResult;
import dreamteam.battleship.service.springcontroller.model.response.TurnStatus;
import dreamteam.battleship.service.springcontroller.preparation.PlayerOrganizer;
import dreamteam.battleship.service.springcontroller.model.Player;
import dreamteam.battleship.service.springcontroller.registration.Registration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Responsible to play and shooting the fields
 */
@RestController
@Scope("session")
public class Playing extends BattleShipServiceBase {

    final static Logger logger = Logger.getLogger(Playing.class);

    protected IGameController controller;

    private Player player;

    @Autowired
    protected HttpSession session;

    /**
     * Responsible to handling the request to for the shooting.
     * @param fieldNumber
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/shoot")
    public ShootingResult shoot(@RequestParam(name = "fieldNumber") int fieldNumber) {
        return controller.handleShot(fieldNumber, player);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/turnstatus")
    public TurnStatus turnStatus(){
        // this method will call iterally, so i dont think that logging is a good idea
        return
                new TurnStatus(controller.getBoardForPlayer(player), controller.isMyTurn(player), controller.getWinner());
    }

    private ShootingResult winnerResponse() {
        return
                new ShootingResult(MovementStatus.WON, controller.getWinner(), controller.getBoardForPlayer(player));
    }

    /**
     * Initializing the controller. getting the controller from the session that created earlier
     * @return
     */
    private IGameController callController() {
        logger.debug("initializing the controller for the playew");
        return ((PlayerOrganizer)session.getAttribute("playerOrganizer")).myController();
    }

    /**
     * Initializing the player. getting the player from the session that created earlier
     * @return
     */
    private Player callPlayer() {
        logger.debug("getting the player in the playing");
        return ((Registration)session.getAttribute("registration")).getPlayer();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.init(session);
        controller = callController();
        controller.startGame();

        player= callPlayer();
    }
}
