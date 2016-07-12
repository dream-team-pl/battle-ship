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
     * equals is needed because we store ships in List
     */
    boolean equals(Object obj);

    /**
     * hashCode is needed because we store ships in List
     */
    int hashCode();
}
