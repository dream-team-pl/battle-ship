package dreamteam.battleship.arbiter;

public class MovementContainerImpl extends MovementContainer {

    @Override
    public void addMovement(int field, boolean isDamaged) {
        if(!containsMovement(field)) movements.put(field, isDamaged);
    }

    @Override
    public boolean containsMovement(int field) {
        if(movements.containsKey(field)) return true;
        return false;
    }
}
