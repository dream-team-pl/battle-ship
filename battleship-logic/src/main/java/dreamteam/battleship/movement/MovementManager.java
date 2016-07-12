package dreamteam.battleship.movement;

import dreamteam.battleship.arbiter.Arbiter;
import dreamteam.battleship.board.Direction;
import dreamteam.battleship.ship.Ship;

/**
 * Created by ehsan on 12.07.16.
 */
public interface MovementManager {
    /**
     * Damage the field number
     * @param fieldNumber - the field that must be damaged
     * @return
     *          <ul>
     *              <li>
     *                  INVALID_MOVEMENT - if the movement is invalid
     *              </li>
     *              <li>
     *                  TRY_AGAIN - if nothing is damaged
     *              </li>
     *              <li>
     *                  SUCCESS - if the field contains a ship
     *              </li>
     *              <li>
     *                  WON - if the {@link Arbiter} decide that we have a winner
     *              </li>
     *          </ul>
     */
    default MovementStatus damage(int fieldNumber){
        return MovementStatus.INVALID_MOVEMENT;
    }


    /**
     *
     * @param ship - the ship
     * @param fromFieldNumber - from the fieldNubmer
     * @param direction - the direction that the must be stored
     * @return
     *          <ul>
     *              <li>
     *                  INVALID_MOVEMENT - if the movement is invalid
     *              </li>
     *              <li>
     *                  TRY_AGAIN - if there is not place for that ship
     *              </li>
     *              <li>
     *                  SUCCESS - if the ship is placed
     *              </li>
     *          </ul>
     */
    default MovementStatus tryPutShip(Ship ship, int fromFieldNumber, Direction direction){
        return MovementStatus.INVALID_MOVEMENT;
    }
}
