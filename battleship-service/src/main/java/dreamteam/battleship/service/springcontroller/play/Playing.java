package dreamteam.battleship.service.springcontroller.play;

import dreamteam.battleship.service.springcontroller.BattleShipServiceBase;
import dreamteam.battleship.service.springcontroller.gamecontroller.IGameController;
import dreamteam.battleship.service.springcontroller.model.Player;
import dreamteam.battleship.service.springcontroller.model.response.ShootingResult;
import dreamteam.battleship.service.springcontroller.model.response.TurnStatus;
import dreamteam.battleship.service.springcontroller.preparation.PlayerOrganizer;
import dreamteam.battleship.service.springcontroller.registration.Registration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

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
     * @param fieldNumbers
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/shoot")
    public ShootingResult shoot(@RequestParam(name = "fieldNumber") List<Integer> fieldNumbers) {
        return controller.handleShot(fieldNumbers, player);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/turnstatus")
    public TurnStatus turnStatus() throws Exception{
        // this method will call iterally, so i dont think that logging is a good idea
        return controller.turnStatus(player);
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
        player= callPlayer();
    }
}
