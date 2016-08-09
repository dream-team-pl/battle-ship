package dreamteam.battleship.service.springcontroller.preparation;

import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.service.springcontroller.BattleShipServiceBase;
import dreamteam.battleship.service.springcontroller.gamecontroller.*;
import dreamteam.battleship.service.springcontroller.model.GameMode;
import dreamteam.battleship.service.springcontroller.model.Player;
import dreamteam.battleship.service.springcontroller.model.response.Organizer;
import dreamteam.battleship.service.springcontroller.registration.Registration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    protected IGameController gameController;

    private GameMode mode;

    @RequestMapping(method = RequestMethod.GET, path = "/prepare")
    public Organizer preparePlayer(HttpSession session,
                                   @RequestParam(name = "gameMode", defaultValue= "NORMAL_MODE") GameMode gameMode) {
        logger.debug(START + " - " + gameMode);
        mode = gameMode;
        if(gameController==null){
            logger.debug("Preparing the player");
            Player player = callPlayer(session);
            MovementManager manager = callManager(session);

            if(bench.isFree(mode)){
                initialGameController(player, manager);
            }else {
                // TODO setSecondPlayerAndStart
                // TODO start will throw an exception if we call it twice
                setSecondPlayer(player, manager);
            }
        }
        logger.debug(END);
        return new Organizer(gameController.isReadyToPlay());
    }

    private void setSecondPlayer(Player player, MovementManager manager) {
        logger.debug("Player2 join the game controller, lets play!!");
        gameController = bench.pickController(mode);
        gameController.addPlayer2(player, manager);
    }

    private void initialGameController(Player player, MovementManager manager) {
        logger.debug("Player1 will wait");
        gameController = GameControllerBuilder.gameControllerInstance(player, manager, mode);
        bench.letSit(gameController, mode);
    }

    private MovementManager callManager(HttpSession session) {
        return ((PlacingShip)session.getAttribute("placingShip")).manager;
    }

    private Player callPlayer(HttpSession session) {
        return ((Registration)session.getAttribute("registration")).getPlayer();
    }

    public IGameController myController(){
        return gameController;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
