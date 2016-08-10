package dreamteam.battleship.service.springcontroller.gamecontroller;

import dreamteam.battleship.logic.arbiter.ArbiterImpl;
import dreamteam.battleship.logic.arbiter.MovementContainerImpl;
import dreamteam.battleship.logic.movement.DamageManager;
import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.model.Player;
import dreamteam.battleship.service.springcontroller.model.response.GunSaluteShoot;
import dreamteam.battleship.service.springcontroller.model.response.ShootingResult;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dreamteam.battleship.loggerhelper.LoggerStatics.END;
import static dreamteam.battleship.loggerhelper.LoggerStatics.START;

/**
 * Created by daniel on 09.08.16.
 */
public class GunSaluteController extends GameControllerBase{

    final static Logger logger = Logger.getLogger(GunSaluteController.class);

    public GunSaluteController(Player player1, MovementManager manager1) {
        super(player1, manager1);
    }

    @Override
    public boolean isMyTurn(Player player) {
        return manager1.getNumberOfTurn() == manager2.getNumberOfTurn();
    }

    @Override
    public ShootingResult handleShot(List<Integer> fieldNumbers, Player player) {

        MovementManager mm = player.equals(player1) ? manager1 : manager2;
        mm.incrementNumberOfTurn();
        logger.debug(START);
        GunSaluteShoot response;
        // check if there is sense to shoot
        response = handleGunSaluteShoot(fieldNumbers, player);
        logger.debug("shoot status is " + response.status);
        logger.debug(END);
        return response;
    }

    @Override
    public void startGame() {
        logger.debug(player1.name + " has board " + manager1.getBoard());
        logger.debug(player2.name + " has board " + manager2.getBoard());
        MovementManager tempManager1 = manager1;
        MovementManager tempManager2 = manager2;
        manager1 = new DamageManager(tempManager2.getBoard(), new MovementContainerImpl(), new ArbiterImpl(player2.shipList()));
        manager2 = new DamageManager(tempManager1.getBoard(), new MovementContainerImpl(), new ArbiterImpl(player1.shipList()));
        isTheGameStarted = true;
    }

    private GunSaluteShoot handleGunSaluteShoot(List<Integer> fieldNumbers, Player player) {
        logger.debug("Handling the shoot");
        GunSaluteShoot response;
        Map<Integer, Boolean> gunSaluteShootingResponse = new HashMap<>();


        for(int fieldNumber: fieldNumbers) {
            MovementStatus status = shotResponse(fieldNumber, player);
            boolean fieldIsHit = shipIsHit(status);
            gunSaluteShootingResponse.put(fieldNumber, fieldIsHit);
        }

        Player winner = getWinner();
        MovementStatus ms  = winner != null ? MovementStatus.WON : MovementStatus.GUN_SALUTE_MODE;
        response = new GunSaluteShoot(ms, winner, gunSaluteShootingResponse);
        return response;
    }

    private MovementStatus shotResponse(int fieldNumber, Player player){
        MovementStatus status;
        status = player.equals(player1) ? manager1.damage(fieldNumber) : manager2.damage(fieldNumber);
        trySetWinner();

        return status;
    }

    private boolean shipIsHit (MovementStatus ms) {
        return ms == MovementStatus.SUCCESS || ms == MovementStatus.WON;
    }
}
