package dreamteam.battleship.movement;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daniel on 12.07.16.
 * MovementContainer stores player's movements.
 */
public class MovementContainer {

    /**
     * Container for movements. HashMap was chosen because of performance.
     */
    protected Map <Integer, Boolean> movements = new HashMap();

    /**
     *
     * @param field - field on Board
     * @param isDamaged - information about player's attack. If player shot, isDamaged = true
     *                  else isDamaged = false
     */
    public void addMovement(int field, boolean isDamaged) {
        if(!containsMovement(field)) movements.put(field, isDamaged);
    }

    /**
     *
     * @param field - field on Board
     * @return true if movements contains field and false when movements doesn't contain field
     */
    protected boolean containsMovement(int field) {
        if(movements.containsKey(field)) return true;
        return false;
    }
}
