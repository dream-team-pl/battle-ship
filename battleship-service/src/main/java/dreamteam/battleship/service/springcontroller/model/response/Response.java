package dreamteam.battleship.service.springcontroller.model.response;

import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.logic.ship.ShipType;
import dreamteam.battleship.service.springcontroller.model.Player;

import java.util.List;

/**
 * Created by ehsan on 11.08.16.
 */
public interface Response {

    static Register register(Player player){
        return new Register(player);
    }

    static Place place(MovementStatus status, List<ShipType> availableShips){
        return new Place(status, availableShips);
    }

    static Organizer organizer(boolean readyToPlay){
        return new Organizer(readyToPlay);
    }

}
