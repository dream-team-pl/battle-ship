package dreamteam.battleship.service.springcontroller.model.response;

import dreamteam.battleship.logic.movement.*;
import dreamteam.battleship.logic.ship.ShipType;

import java.util.List;

/**
 * Created by egolesor on 18.07.16.
 */
public class Placing {

    public final MovementStatus status;

    public final List<ShipType> availableShips;

    public Placing(MovementStatus status, List<ShipType> availableShips) {
        this.status = status;
        this.availableShips = availableShips;
    }
}