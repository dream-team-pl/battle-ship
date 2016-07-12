package dreamteam.battleship.movement;

/**
 * Created by ehsan on 12.07.16.
 */
public interface MovementContainer {
    boolean containsMovement(int movementNumber);
    void addMovement(int movementNumber, boolean isDamaged);
}
