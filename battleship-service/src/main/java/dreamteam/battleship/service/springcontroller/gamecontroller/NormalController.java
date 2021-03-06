package dreamteam.battleship.service.springcontroller.gamecontroller;

import dreamteam.battleship.logic.arbiter.ArbiterImpl;
import dreamteam.battleship.logic.arbiter.MovementContainerImpl;
import dreamteam.battleship.logic.movement.DamageManager;
import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.model.Player;
import dreamteam.battleship.service.springcontroller.model.response.Response;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

import static dreamteam.battleship.loggerhelper.LoggerStatics.END;
import static dreamteam.battleship.loggerhelper.LoggerStatics.START;

/**
 * Created by daniel on 09.08.16.
 */
class NormalController extends GameControllerBase {

    final static Logger logger = Logger.getLogger(NormalController.class);

    protected MovementManager currentManager;
    protected Player currentPlayer;

    private final int SHOTS_NUMBER = 1;
    private final int FIRST_INDEX_OF_THE_LIST = 0;

    public NormalController(Player player1, MovementManager manager1) {
        super(player1, manager1);
    }

    @Override
    public boolean isMyTurn(Player player) {
        return currentPlayer.equals(player);
    }

    private MovementStatus shotResponse(int fieldNumber, Player player) {
        MovementStatus status = MovementStatus.INVALID_MOVEMENT;

        if(validatePlayer(player)){
            status = currentManager.damage(fieldNumber);
        }
        trySetWinner();

        return status;
    }

    @Override
    public Response handleShot(List<Integer> fieldNumbers, Player player) {
        logger.debug(START);
        Response response = (getWinner()==null) ? standardResponse(fieldNumbers.get(FIRST_INDEX_OF_THE_LIST), player) : winnerResponse();
        logger.debug(END);
        return response;
    }

    @Override
    public void startGame() {
        if(!isTheGameStarted) {
            logger.debug(player1.name + " has board " + manager1.getBoard());
            logger.debug(player2.name + " has board " + manager2.getBoard());
            MovementManager tempManager1 = manager1;
            MovementManager tempManager2 = manager2;
            manager1 = new DamageManager(tempManager2.getBoard(), new MovementContainerImpl(), new ArbiterImpl(player2.shipList()));
            manager2 = new DamageManager(tempManager1.getBoard(), new MovementContainerImpl(), new ArbiterImpl(player1.shipList()));
            currentManager = manager1;
            currentPlayer = player1;
            isTheGameStarted = true;
        }
    }

    @Override
    public Response turnStatus(Player player) {
        return Response.turnStatus(getBoardForPlayer(player), isMyTurn(player), getWinner(), SHOTS_NUMBER);
    }

    private void nextPlayer(){
        if(currentPlayer.equals(player1)){
            currentManager = manager2;
            currentPlayer = player2;
        }else{
            currentManager = manager1;
            currentPlayer = player1;
        }
    }

    private boolean validatePlayer(Player player) {
        if(currentPlayer.equals(player))
            return true;
        return false;
    }

    private boolean mustPlayNext(MovementStatus status) {
        return !( status.equals(MovementStatus.INVALID_MOVEMENT) || status.equals(MovementStatus.SUCCESS) || status.equals(MovementStatus.WON));
    }

    private Response winnerResponse() {
        return Response.shootingResult(MovementStatus.WON, getWinner(), new HashMap<Integer, Boolean>());
    }

    /**
     * Will shoot the concrete field and check the result of the shooting.
     * @param fieldNumber
     * @return
     */
    private Response standardResponse(int fieldNumber, Player player) {
        logger.debug("Handling the shoot");
        Response response;
        MovementStatus status = shotResponse(fieldNumber, player);
        if(mustPlayNext(status))
            nextPlayer();
        // check if he is the winnner
        //FIXME In the future when we will use web sockets we are going to send event, we need to delete this line
        response = MovementStatus.WON.equals(status) ? winnerResponse() : Response.shootingResult(status, new HashMap<Integer, Boolean>());
        return response;
    }
}
