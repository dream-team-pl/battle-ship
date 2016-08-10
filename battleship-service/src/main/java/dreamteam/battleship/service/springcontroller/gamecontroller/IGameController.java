package dreamteam.battleship.service.springcontroller.gamecontroller;

import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.service.springcontroller.model.Player;
import dreamteam.battleship.service.springcontroller.model.response.ShootingResult;

import java.util.List;
import java.util.Map;

/**
 * Created by daniel on 08.08.16.
 */
public interface IGameController {

    /**
     *
     * @param player
     * @return boolean that inform if there is player's turn or not
     */
    boolean isMyTurn(Player player);

    /**
     * adding the second player to the controller an now we can start to play.
     * @param player
     * @param movementManager
     */
    void addPlayer2(Player player, MovementManager movementManager);

    /**
     * after initialization we check if game is initialized and can be played
     * @return
     */
    boolean isReadyToPlay();

    /**
     * It's checked after every turn, so we check if the winner exist
     */
    void trySetWinner();

    Player getWinner();

    Map<Integer, Boolean> getBoardForPlayer(Player player);

    /**
     * Will shoot the concrete field and check the result of the shooting.
     * @param fieldNumbers
     * @param player
     * @return
     */
    ShootingResult handleShot(List<Integer> fieldNumbers, Player player);

    /**
     * initialize the game
     */
    void startGame();
}
