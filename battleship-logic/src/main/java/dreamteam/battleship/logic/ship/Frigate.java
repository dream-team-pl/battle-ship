package dreamteam.battleship.logic.ship;

/**
 * Created by daniel on 13.07.16.
 */
public class Frigate extends ShipImpl {

    public Frigate(int id) {
        super(id);
    }
    public byte size() {
        return ShipType.twoMast.size;
    }
}
