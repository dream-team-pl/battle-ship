package dreamteam.battleship.service.springcontroller.gamecontroller;

import dreamteam.battleship.logic.arbiter.ArbiterImpl;
import dreamteam.battleship.logic.arbiter.MovementContainerImpl;
import dreamteam.battleship.logic.movement.DamageManager;
import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.model.Player;
import dreamteam.battleship.service.springcontroller.model.response.Response;
import org.apache.log4j.Logger;

import java.util.List;

import static dreamteam.battleship.loggerhelper.LoggerStatics.END;
import static dreamteam.battleship.loggerhelper.LoggerStatics.START;

/**
 * Created by daniel on 09.08.16.
 */
class GunSaluteController extends GameControllerBase{

    final static Logger logger = Logger.getLogger(GunSaluteController.class);

    private boolean draw = false;
    private PlayerQueue playerQueue = new PlayerQueue();

    public GunSaluteController(Player player1, MovementManager manager1) {
        super(player1, manager1);
    }

    @Override
    public boolean isMyTurn(Player player) {
        return playerQueue.isMyTurn(player);
    }

    @Override
    public Response handleShot(List<Integer> fieldNumbers, Player player) {

        logger.debug(START);
        Response response;
        // check if there is sense to shoot
        response = handleGunSaluteShoot(fieldNumbers, player);
        logger.debug(END);

        playerQueue.remove(player);

        if(playerQueue.isEmpty()) {
            playerQueue.add(player1, player2, manager2.numberOfPlayerShots(), manager1.numberOfPlayerShots());
            trySetStateOfGame();
        }

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
        playerQueue.add(player1, player2, player1.shipList().size(), player2.shipList().size());
        isTheGameStarted = true;
    }

    private void trySetStateOfGame() {

        boolean player1Win = manager1.isThePlayerWon();
        boolean player2Win = manager2.isThePlayerWon();

        if(player1Win && player2Win) draw = true;
        else
            trySetWinner();
    }

    @Override
    public Response turnStatus(Player player) {
        return Response.turnStatus(getBoardForPlayer(player), isMyTurn(player), getWinner(), getOpponentManager(player).numberOfPlayerShots(), draw);
    }

    private Response handleGunSaluteShoot(List<Integer> fieldNumbers, Player player) {
        logger.debug("Handling the shoot");
        handleShotList(fieldNumbers, player);
        Player winner = getWinner();
        MovementStatus ms  = winner != null ? MovementStatus.WON : MovementStatus.GUN_SALUTE_MODE;
        return Response.shootingResult(ms, winner, getCurrentManager(player).getMovements());
    }

    private void handleShotList(List<Integer> fieldNumbers, Player player) {
        int numberOfShots = playerQueue.shotsNumber(player);

        if(fieldNumbers.size() > numberOfShots) {
            fieldNumbers = fieldNumbers.subList(0, numberOfShots);
        }

        for(int fieldNumber: fieldNumbers) {
            MovementStatus status = player.equals(player1) ? manager1.damage(fieldNumber) : manager2.damage(fieldNumber);
        }
    }

    private MovementManager getCurrentManager(Player player) {
        return player.equals(player1) ? manager1 : manager2;
    }

    private MovementManager getOpponentManager(Player player) {
        return player.equals(player1) ? manager2 : manager1;
    }
}
