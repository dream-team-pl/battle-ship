package dreamteam.battleship.service.springcontroller.preparation;

import dreamteam.battleship.logic.arbiter.ArbiterImpl;
import dreamteam.battleship.logic.arbiter.MovementContainerImpl;
import dreamteam.battleship.logic.movement.DamageManager;
import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.registration.Player;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by egolesor on 19.07.16.
 */
public class GameController {
    final static Logger logger = Logger.getLogger(GameController.class);
    Player player1, player2;

    MovementManager manager1, manager2;

    private boolean isTheGameStarted;

    private Player winner;

    public boolean isMyTurn(Player player) {
        return currentPlayer.equals(player);
    }

    private MovementManager currentManager;
    private Player currentPlayer;
    public GameController(Player player1, MovementManager manager1){
        this.player1 = player1;
        this.manager1 = manager1;
    }

    /**
     * adding the second player to the controller an now we can start to play.
     * @param player2
     * @param manager2
     */
    public void addPlayer2(Player player2, MovementManager manager2){
        this.player2 = player2;
        this.manager2 = manager2;
    }

    public boolean isReadyToPlay() {
        return player1 != null && player2 != null;
    }

    public void startGame(){
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

    public MovementStatus shoot(int fieldNumber, Player player){
        MovementStatus status = MovementStatus.INVALID_MOVEMENT;

        if(validatePlayer(player)){
            status = currentManager.damage(fieldNumber);
        }
        checkPlayer(status, player);

        return status;
    }

    private void checkPlayer(MovementStatus status, Player player) {
        if(MovementStatus.WON.equals(status)){
            winner = currentPlayer;
        }
    }

    private boolean validatePlayer(Player player) {
        if(currentPlayer.equals(player)){
            return true;
        }
        return false;
    }

    public void nextPlayer(){
        if(currentPlayer.equals(player1)){
            currentManager = manager2;
            currentPlayer = player2;
        }else{
            currentManager = manager1;
            currentPlayer = player1;
        }
    }

    public Player getWinner(){
        return winner;
    }

    public Map<Integer, Boolean> getBoardForPlayer(Player player){
        Map<Integer, Boolean> retMap;
        if(player.equals(player1)){
            retMap = manager2.getMovements();
        }else {
            retMap = manager1.getMovements();
        }
        return retMap;
    }
}
