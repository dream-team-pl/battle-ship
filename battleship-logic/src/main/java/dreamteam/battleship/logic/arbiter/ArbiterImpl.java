package dreamteam.battleship.logic.arbiter;

import dreamteam.battleship.logic.ship.Ship;

import java.util.List;

public class ArbiterImpl extends Arbiter{

    /**
     *
     * @param shipList is passed in constructor. shipList will be created base on another class.
     */
    public ArbiterImpl(List<Ship> shipList) {
        this.shipList = shipList;
    }

    @Override
    public void removeShip(Ship ship) {
        if(!shipList.remove(ship))
            throw new IllegalArgumentException("The ship isn't into shipList");
    }

    @Override
    public boolean isWinner() {
        return shipList.isEmpty();
    }

    @Override
    public int numberOfAliveShips() {
        return this.shipList.size();
    }
}
