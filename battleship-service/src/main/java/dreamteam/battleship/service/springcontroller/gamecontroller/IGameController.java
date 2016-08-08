package dreamteam.battleship.service.springcontroller.gamecontroller;

import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.model.Player;
import dreamteam.battleship.service.springcontroller.model.response.Shoot;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by daniel on 08.08.16.
 */
public interface IGameController {

    boolean isMyTurn(Player player);

    void addPlayer2(Player player, MovementManager movementManager);

    boolean isReadyToPlay();

    MovementStatus shotResponse(int fieldNumber, Player player);

    void trySetWinner();

    Player getWinner();

    Map<Integer, Boolean> getBoardForPlayer(Player player);

    Shoot handleShot(HttpSession session, int fieldNumber);

    void startGame();
}
