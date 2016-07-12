package dreamteam.battleship.ship;

/**
 * Simple representation of our ships. Variables are protected to get access
 * to test part in project. ShipImpl could have size between 1 and 4.
 */
public class ShipImpl implements Ship{

    public final ShipType shipType;
    protected byte damaged;
    protected final int ui;
    protected static int uiCounter;

    public static ShipImpl shipFactory(ShipType shipType) {
        uiCounter++;
        return new ShipImpl(shipType, uiCounter);
    }
    /**
     *
     * @param shipType - name of ship
     * @param ui - size of ship
     */
    private ShipImpl(ShipType shipType, int ui) {
        this.shipType = shipType;
        this.ui = ui;
    }

    /**
     * decrement damaged variable. We need it when we check if the ship is destroyed.
     */
    public void damage() {
        damaged++;
    }

    /**
     *
     * @return true if the ship is destroyed and false if ship alived
     */
    public boolean isDamaged() {
        return (shipType.size - damaged) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipImpl ship = (ShipImpl) o;

        if (ui != ship.ui) return false;
        if (!shipType.equals(ship.shipType)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = ui;
        result = 31 * result + shipType.hashCode();
        result = 31 * result + damaged;
        return result;
    }
}
