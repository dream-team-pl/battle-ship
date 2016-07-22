package dreamteam.battleship.service.springcontroller.play;

import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.registration.Player;

import java.util.Map;

/**
 * Created by egolesor on 19.07.16.
 */
public class ShootResponse {

    public final MovementStatus status;

    public Player winner;

    public final Map<Integer, Boolean> myDamages;

    public ShootResponse(MovementStatus status, Map<Integer, Boolean> damages) {
        this.status = status;
        this.myDamages = damages;
    }
    public ShootResponse(MovementStatus status, Player winner, Map<Integer, Boolean> damages) {
        this.status = status;
        this.winner = winner;
        myDamages =damages;
    }
}
