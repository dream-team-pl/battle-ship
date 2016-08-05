package dreamteam.battleship.service.springcontroller.play;

import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.registration.Player;

import java.util.Map;

/**
 * Created by daniel on 05.08.16.
 */
public class SalvaShootResponse extends ShootResponse {
    public final Map<Integer, Boolean> salvaShootingResponse;

    public SalvaShootResponse(MovementStatus status, Map<Integer, Boolean> salvaShootingResponse) {
        super(status);
        this.salvaShootingResponse = salvaShootingResponse;
    }

    public SalvaShootResponse(MovementStatus status, Player winner, Map<Integer, Boolean> salvaShootingResponse) {
        super(status, winner);
        this.salvaShootingResponse = salvaShootingResponse;
    }
}
