package dreamteam.battleship.service.springcontroller.model.response;

import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.model.Player;

import java.util.Map;

/**
 * A model that presents the result of the shooting a field by the player
 */
public class ShootingResult {

    public final MovementStatus status;

    /**
     * Will be set if only we got a winner
     */
    public Player winner;

    public final Map<Integer, Boolean> resultFromOpponentBoard;

    public ShootingResult(MovementStatus status, Map<Integer, Boolean> resultFromOpponentBoard) {
        this.status = status;
        this.resultFromOpponentBoard = resultFromOpponentBoard;
    }
    public ShootingResult(MovementStatus status, Player winner, Map<Integer, Boolean> resultFromOpponentBoard) {
        this.status = status;
        this.winner = winner;
        this.resultFromOpponentBoard = resultFromOpponentBoard;
    }
}
