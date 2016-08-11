package dreamteam.battleship.service.springcontroller.model.response;

import dreamteam.battleship.logic.movement.*;
import dreamteam.battleship.logic.ship.ShipType;

import java.util.List;

/**
 * Created by egolesor on 18.07.16.
 */
class Place implements Response {

    public final MovementStatus status;

    public final List<ShipType> availableShips;

    public Place(MovementStatus status, List<ShipType> availableShips) {
        this.status = status;
        this.availableShips = availableShips;
    }
}
