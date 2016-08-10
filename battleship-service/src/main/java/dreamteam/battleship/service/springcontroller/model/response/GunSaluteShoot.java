package dreamteam.battleship.service.springcontroller.model.response;

import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.model.Player;

import java.util.Map;

/**
 * Created by daniel on 09.08.16.
 */
public class GunSaluteShoot extends ShootingResult {
    public final Map<Integer, Boolean> gunSaluteShootingResponse;

    public GunSaluteShoot(MovementStatus status, Map<Integer, Boolean> gunSaluteShootingResponse) {
        super(status);
        this.gunSaluteShootingResponse = gunSaluteShootingResponse;
    }

    public GunSaluteShoot(MovementStatus status, Player winner, Map<Integer, Boolean> gunSaluteShootingResponse) {
        super(status, winner);
        this.gunSaluteShootingResponse = gunSaluteShootingResponse;
    }
}
