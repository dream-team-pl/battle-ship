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

    /**
     * The movements that player done so far
     */
    public final Map<Integer, Boolean> myDamages;

    public ShootingResult(MovementStatus status, Map<Integer, Boolean> damages) {
        this.status = status;
        this.myDamages = damages;
    }
    public ShootingResult(MovementStatus status, Player winner, Map<Integer, Boolean> damages) {
        this.status = status;
        this.winner = winner;
        myDamages =damages;
    }
}
