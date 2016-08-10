package dreamteam.battleship.logic.arbiter;

import java.util.HashMap;
import java.util.Map;

/**
 * MovementContainerImpl stores player's movements.
 */
public abstract class MovementContainer {

    /**
     * Container for movements. HashMap was chosen because of performance.
     */
    protected Map<Integer, Boolean> movements = new HashMap();
    protected int numberOfTurn;

    /**
     *
     * @param field - field on Board
     * @param isDamaged - information about player's attack. If player shot, isDamaged = true
     *                  else isDamaged = false
     *                  addMovement adds pair(pos, isDamaged) into movements Map
     */
    public abstract void addMovement(int field, boolean isDamaged);

    /**
     *
     * @param field - field on Board
     * @return true if movements contains field and false when movements doesn't contain field
     */
    public abstract boolean containsMovement(int field);

    public void incrementNumberOfTurn() {
        numberOfTurn++;
    }

    public int getNumberOfTurn() {
        return numberOfTurn;
    }

    public Map<Integer, Boolean> getMovements(){
        return movements;
    }
}
