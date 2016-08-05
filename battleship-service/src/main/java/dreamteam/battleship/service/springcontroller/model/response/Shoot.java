package dreamteam.battleship.service.springcontroller.model.response;

import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.model.Player;

import java.util.Map;

/**
 * Created by egolesor on 19.07.16.
 */
public class Shoot {

    public final MovementStatus status;

    public Player winner;

    public final Map<Integer, Boolean> myDamages;

    public Shoot(MovementStatus status, Map<Integer, Boolean> damages) {
        this.status = status;
        this.myDamages = damages;
    }
    public Shoot(MovementStatus status, Player winner, Map<Integer, Boolean> damages) {
        this.status = status;
        this.winner = winner;
        myDamages =damages;
    }
}
