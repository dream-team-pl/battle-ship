package dreamteam.battleship.logic.ship;

/**
 * Simple representation of ships
 */
public interface Ship {
    /**
     * method is execute when opponent hit to player ship
     */
    void damage();

    /**
     *
     * @return true if opponent kill player's ship
     */
    boolean isDamaged();

    /**
     *
     * @return size of ship. It's necessary to outside of our class
     */
    byte size();
}
