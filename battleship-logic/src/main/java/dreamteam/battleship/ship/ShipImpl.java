package dreamteam.battleship.ship;

/**
 * Simple representation of our ships. Variables are protected to get access
 * to test part in project. ShipImpl could have size between 1 and 4.
 */
public abstract class ShipImpl implements Ship{

    protected final int id;
    protected byte damaged;

    public ShipImpl(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Ship)) return false;

        ShipImpl that = (ShipImpl) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public boolean isDamaged() {
        return (size() - damaged) == 0;
    }

    public void damage() {
        damaged++;
    }

    /**
     *
     * @return ship's size
     */
    public abstract byte size();
}
