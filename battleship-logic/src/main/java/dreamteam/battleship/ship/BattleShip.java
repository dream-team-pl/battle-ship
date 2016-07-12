package dreamteam.battleship.ship;

/**
 * Created by daniel on 12.07.16.
 */
public class BattleShip extends ShipImpl{

    protected final ShipType shipType;
    protected byte damaged;

    public BattleShip(ShipType shipType, int ID) {
        super(ID);
        this.shipType = shipType;
    }

    @Override
    public byte size() {
        return shipType.size;
    }

    @Override
    public boolean isDamaged() {
        return (shipType.size - damaged) == 0;
    }

    @Override
    public void damage() {
        damaged++;
    }
}
