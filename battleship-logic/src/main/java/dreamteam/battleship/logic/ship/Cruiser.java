package dreamteam.battleship.logic.ship;

/**
 * Created by daniel on 13.07.16.
 */
public class Cruiser extends ShipImpl{

    public Cruiser(int id) {
        super(id);
    }

    public byte size() {
        return ShipType.threeMast.size;
    }
}
