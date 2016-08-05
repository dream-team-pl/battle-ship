package dreamteam.battleship.service.springcontroller.play;

import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.registration.Player;

/**
 * Created by egolesor on 19.07.16.
 */
public class ShootResponse {

    public final MovementStatus status;

    public Player winner;

    public ShootResponse(MovementStatus status) {
        this.status = status;
    }
    public ShootResponse(MovementStatus status, Player winner) {
        this.status = status;
        this.winner = winner;
    }
}
