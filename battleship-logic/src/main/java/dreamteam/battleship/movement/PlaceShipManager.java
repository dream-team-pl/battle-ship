package dreamteam.battleship.movement;

import dreamteam.battleship.arbiter.Arbiter;
import dreamteam.battleship.board.Board;
import dreamteam.battleship.board.Direction;
import dreamteam.battleship.ship.Ship;

/**
 * Created by ehsan on 12.07.16.
 */
public class PlaceShipManager extends AbstractMovementManager {

    public PlaceShipManager(Board board) {
        super(board);
    }

    @Override
    public MovementStatus tryPutShip(Ship ship, int fromFieldNumber, Direction direction) {
        MovementStatus status = MovementStatus.INVALID_MOVEMENT;
        if(isValidFieldNumber(fromFieldNumber)){
            status = MovementStatus.TRY_AGAIN;
            if(board.isPlaceForTheShip(fromFieldNumber,ship, direction)){
                status = MovementStatus.SUCCESS;
                board.placeShip(fromFieldNumber, ship, direction);
            }
        }
        return status;
    }

}
