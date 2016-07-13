package dreamteam.battleship.ship;

/**
 * Created by daniel on 12.07.16.
 */
public class BattleShip extends ShipImpl{

    public BattleShip(int id) {
        super(id);
    }

    @Override
    public byte size() {
        return ShipType.fourMast.size;
    }
}
