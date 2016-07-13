package dreamteam.battleship.ship;

/**
 * Created by daniel on 13.07.16.
 */
public class MineSweeper extends ShipImpl {

    public MineSweeper(int id) {
        super(id);
    }

    public byte size() {
        return ShipType.oneMast.size;
    }
}
