package dreamteam.battleship.service.play;

import dreamteam.battleship.logic.movement.MovementStatus;

/**
 * Created by egolesor on 19.07.16.
 */
public class ShootResponse {

    public final MovementStatus status;

    public ShootResponse(MovementStatus status) {
        this.status = status;
    }
}
