package dreamteam.battleship.service.springcontroller.gamecontroller;

import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.service.springcontroller.model.Player;

import java.util.Map;

/**
 * Created by daniel on 08.08.16.
 */
abstract class GameControllerBase implements IGameController {

    protected Player player1, player2;
    protected MovementManager manager1, manager2;
    protected Player winner;
    protected boolean isTheGameStarted;

    public GameControllerBase(Player player1, MovementManager manager1){
        this.player1 = player1;
        this.manager1 = manager1;
    }

    @Override
    public void addPlayer2(Player player, MovementManager movementManager) {
        this.player2 = player;
        this.manager2 = movementManager;
    }

    @Override
    public boolean isReadyToPlay() {
        return player1 != null && player2 != null;
    }

    @Override
    public void trySetWinner() {
        if(manager1.isThePlayerWon())
            winner=player1;
        else if(manager2.isThePlayerWon())
            winner=player2;
    }

    @Override
    public Player getWinner() {
        return winner;
    }

    @Override
    public Map<Integer, Boolean> getBoardForPlayer(Player player) {
        if(player.equals(player1))
            return manager2.getMovements();
        else
            return manager1.getMovements();
    }
}
