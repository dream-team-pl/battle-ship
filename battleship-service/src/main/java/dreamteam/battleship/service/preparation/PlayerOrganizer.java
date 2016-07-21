package dreamteam.battleship.service.preparation;

import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.service.BattleShipServiceBase;
import dreamteam.battleship.service.registration.Player;
import dreamteam.battleship.service.registration.Registration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static dreamteam.battleship.loggerhelper.LoggerStatics.END;
import static dreamteam.battleship.loggerhelper.LoggerStatics.START;

/**
 * responsible to connect the players
 */
@RestController
@Scope("session")
public class PlayerOrganizer extends BattleShipServiceBase {

    final static Logger logger = Logger.getLogger(PlayerOrganizer.class);

    @Autowired
    @Qualifier("bench")
    protected Bench bench;

    protected GameController gameController;

    @RequestMapping(method = RequestMethod.GET, path = "/prepare")
    public PlayerOrganizerResponse preparePlayer(HttpSession session) {
        logger.debug(START);
        if(gameController==null){
            logger.debug("Preparing the player");
            Player player = myPlayer(session);
            MovementManager manager = myManager(session);

            if(bench.isFree()){
                setFirstPlayer(player, manager);
            }else {
                setSecondPlayer(player, manager);
            }
        }
        logger.debug(END);
        return new PlayerOrganizerResponse(gameController.isReadyToPlay());
    }

    private void setSecondPlayer(Player player, MovementManager manager) {
        logger.debug("Player2 join the game controller, lets play!!");
        gameController = bench.pickController();
        gameController.addPlayer2(player, manager);
    }

    private void setFirstPlayer(Player player, MovementManager manager) {
        logger.debug("Player1 will wait");
        gameController = new GameController(player, manager);
        bench.letSit(gameController);
    }

    private MovementManager myManager(HttpSession session) {
        return ((PlacingShip)session.getAttribute("placingShip")).manager;
    }

    private Player myPlayer(HttpSession session) {
        return ((Registration)session.getAttribute("registration")).getPlayer();
    }

    public GameController myController(){
        return gameController;
    }
}
