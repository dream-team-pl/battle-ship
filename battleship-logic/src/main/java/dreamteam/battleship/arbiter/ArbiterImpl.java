package dreamteam.battleship.arbiter;

import dreamteam.battleship.ship.Ship;

import java.util.List;

public class ArbiterImpl extends Arbiter{

    /**
     *
     * @param shipList is passed in constructor. shipList will be created base on another class.
     */
    public ArbiterImpl(List<Ship> shipList) {
        this.shipList = shipList;
    }

    public void removeShip(Ship ship) {
        if(shipList.contains(ship))
            shipList.remove(ship);
        else
            throw new IllegalArgumentException("The ship isn't into shipList");
    }

    public boolean isWinner() {
        return shipList.isEmpty();
    }
}
