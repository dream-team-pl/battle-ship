package dreamteam.battleship.service.springcontroller.model.response;

import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.model.Player;

/**
 * A model that presents the result of the shooting a field by the player
 */
public class ShootingResult {

    public final MovementStatus status;

    /**
     * Will be set if only we got a winner
     */
    public Player winner;

    public ShootingResult(MovementStatus status) {
        this.status = status;
    }
    public ShootingResult(MovementStatus status, Player winner) {
        this.status = status;
        this.winner = winner;
    }
}
