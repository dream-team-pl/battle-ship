package dreamteam.battleship.service.preparation;

import dreamteam.battleship.logic.movement.*;
import dreamteam.battleship.logic.ship.ShipType;

import java.util.List;

/**
 * Created by egolesor on 18.07.16.
 */
public class PlacingResponse {

    public final MovementStatus status;

    public final List<ShipType> availableShips;

    public PlacingResponse(MovementStatus status, List<ShipType> availableShips) {
        this.status = status;
        this.availableShips = availableShips;
    }
}
