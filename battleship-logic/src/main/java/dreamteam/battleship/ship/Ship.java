package dreamteam.battleship.ship;

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
     * @return unique id of ship, because ship can have the same variables, but it won't be meant that they will be the same
     */
    int id();

    /**
     *
     * @return size of ship. It's necessary to outside of our class
     */
    byte size();
}
