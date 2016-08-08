package dreamteam.battleship.logic.movement;

import dreamteam.battleship.logic.arbiter.Arbiter;
import dreamteam.battleship.logic.board.Board;
import dreamteam.battleship.logic.board.Direction;
import dreamteam.battleship.logic.ship.Ship;

import java.util.Map;


/**
 * Created by ehsan on 12.07.16.
 */
public interface MovementManager {
    int MAX_MAP_SIZE = 100;
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
     * @return
     *          <ul>
     *              <li>true - if the player is already won. it returns the status by the help of arbiter</li>
     *              <li>false - if the player is not won yet. it returns the status by the help of arbiter</li>
     *          </ul>
     */
    default boolean isThePlayerWon(){
        return false;
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

    Board getBoard();

    Map<Integer, Boolean> getMovements();
}
