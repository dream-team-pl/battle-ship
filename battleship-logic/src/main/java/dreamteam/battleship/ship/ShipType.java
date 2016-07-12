package dreamteam.battleship.ship;

/**
 * Enum for storing type and size of ships
 */
public enum ShipType {
    /**
     * Standard ships
     */
    oneMast(1), twoMast(2), threeMast(3), fourMast(4), non(0);

    public final byte size;

    /**
     *
     * @param size - responsible for defined ShipType size
     */
    ShipType(int size)
    {
        this.size = (byte)size;
    }
}
