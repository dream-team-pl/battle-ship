package dreamteam.battleship.service.springcontroller.model.response;

import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.model.Player;

/**
 * Created by egolesor on 19.07.16.
 */
public class Shoot {

    public final MovementStatus status;

    public Player winner;

    public Shoot(MovementStatus status) {
        this.status = status;
    }
    public Shoot(MovementStatus status, Player winner) {
        this.status = status;
        this.winner = winner;
    }
}
