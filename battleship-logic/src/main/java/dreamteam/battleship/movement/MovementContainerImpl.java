package dreamteam.battleship.movement;

public class MovementContainerImpl extends MovementContainer{

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
