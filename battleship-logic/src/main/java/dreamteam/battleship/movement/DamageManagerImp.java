package dreamteam.battleship.movement;

import dreamteam.battleship.arbiter.Arbiter;
import dreamteam.battleship.board.Board;
import dreamteam.battleship.ship.Ship;

/**
 * Responsible to managing the damage movements
 */
public class DamageManagerImp implements MovementManager {

    private final MovementContainer container;

    private final Board board;

    private final Arbiter arbiter;
    public DamageManagerImp(Board board, dreamteam.battleship.movement.MovementContainer movementContainer, Arbiter arbiter) {
        this.container = movementContainer;
        this.board = board;
        this.arbiter = arbiter;
    }

    @Override
    public MovementStatus damage(int fieldNumber) {
        MovementStatus retStatus = MovementStatus.INVALID_MOVEMENT;
        if(isValidMovement(fieldNumber)){
            // I don't like to use lots of else so i do this trick to not use is often
            retStatus = MovementStatus.TRY_AGAIN;
            if(tryDamageShip(fieldNumber)){
                retStatus = MovementStatus.SUCCESS;
            }
        }
        if(arbiter.isWinner()){
            retStatus = MovementStatus.WON;
        }
        return retStatus;
    }

    /**
     * Check if the movement is a valid movement and earlier there were not such movement
     * @param movementNumber
     * @return
     *          <ul>
     *              <li>
     *                  true - if the movement is valid
     *              </li>
     *              <li>
     *                  false - if the movement is not valid (already existing movement) or
     *                  is not in the range of the map
     *              </li>
     *          </ul>
     */
    private boolean isValidMovement(int movementNumber){
        boolean isValid = true;
        if(movementNumber<1 || movementNumber > board.mapSize){
            isValid = false;
        }
        if(isValid && container.containsMovement(movementNumber)){
            isValid = false;
        }
        return isValid;
    }

    /**
     * try to damage a field update the list of not damaged ships and save the movement.
     * @param fieldNumber
     * @return
     *          <ul>
     *              <li>
     *                  true - if there was a ship in that field
     *              </li>
     *              <li>
     *                  false - in other cases
     *              </li>
     *          </ul>
     */
    private boolean tryDamageShip(int fieldNumber){
        Ship ship = board.shipOn(fieldNumber);
        boolean isDamaged = false;
        if (ship!=null){
            ship.damage();
            isDamaged = true;
            shipListCleanUp(ship);
        }
        container.addMovement(fieldNumber, isDamaged);
        return isDamaged;
    }

    /**
     * Check if the ship is damaged it cleans the ship from the ship list
     * @param ship - the ship that in the current movement damaged
     */
    private void shipListCleanUp(Ship ship){
        if(ship.isDamaged()){
            arbiter.removeShip(ship);
        }
    }

}
