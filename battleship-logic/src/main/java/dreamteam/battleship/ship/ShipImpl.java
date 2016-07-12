package dreamteam.battleship.ship;

/**
 * Simple representation of our ships. Variables are protected to get access
 * to test part in project. ShipImpl could have size between 1 and 4.
 */
public abstract class ShipImpl implements Ship{

    protected final int ID;

    public ShipImpl(int ID) {
        this.ID = ID;
    }

    /**
     *
     * @return unique ship's id
     */
    @Override
    public int id() {
        return ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Ship)) return false;

        ShipImpl that = (ShipImpl) o;

        return ID == that.ID;
    }

    @Override
    public int hashCode() {
        return ID;
    }

    /**
     * method is executed when opponent hit player's ship
     */
    public abstract void damage();

    /**
     *
     * @return true if the ship is destroyed and false if ship alived
     */
    public abstract boolean isDamaged();

    /**
     *
     * @return ship's size
     */
    public abstract byte size();
}
