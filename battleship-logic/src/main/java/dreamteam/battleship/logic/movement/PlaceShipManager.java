package dreamteam.battleship.logic.movement;

import dreamteam.battleship.logic.board.Board;
import dreamteam.battleship.logic.board.Direction;
import dreamteam.battleship.logic.ship.Ship;

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
