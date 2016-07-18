package dreamteam.battleship.service.preparation;

import dreamteam.battleship.logic.arbiter.ArbiterImpl;
import dreamteam.battleship.logic.arbiter.MovementContainerImpl;
import dreamteam.battleship.logic.board.Board;
import dreamteam.battleship.logic.movement.DamageManager;
import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.service.registration.Player;
import dreamteam.battleship.service.registration.Registration;
import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by egolesor on 19.07.16.
 */
@Api
@RestController
@Scope("session")
@CrossOrigin("*")
public class PlayerOrganizer {

    final static Logger logger = Logger.getLogger(PlayerOrganizer.class);

    @Autowired
    @Qualifier("bench")
    protected Bench bench;

    protected GameController gameController;

    @RequestMapping(method = RequestMethod.GET, path = "/prepare")
    public PlayerOrganizerResponse preparePlayer(HttpSession session) {
        if(gameController==null){
            logger.debug("Preparing the player");
            Player player = myPlayer(session);
            MovementManager manager = newManager();

            if(bench.isFree()){
                setFirstPlayer(player, manager);
            }else {
                setSecondPlayer(player, manager);
            }
        }

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

    private MovementManager newManager() {
        return new DamageManager(new Board(10), new MovementContainerImpl(),new ArbiterImpl(null));
    }

    private Player myPlayer(HttpSession session) {
        return ((Registration)session.getAttribute("registration")).getPlayer();
    }
}
