package dreamteam.battleship.arbiter;

import dreamteam.battleship.ship.Ship;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by daniel on 11.07.16.
 * Arbiter is responsible for checking winning condition. He remove ships from the list, and check if player
 * removed all ships.
 */
public class Arbiter {

    /**
     * List of ships that are alived
     */
    protected List<Ship> shipList = new LinkedList();

    /**
     *
     * @param shipList is passed in constructor. shipList will be created base on another class.
     */
    public Arbiter(List<Ship> shipList) {
        this.shipList = shipList;
    }

    /**
     *
     * @param ship - for removing. If ship doesn't exist in list, than an exception is thrown
     */
    public void removeShip(Ship ship) {
        if(shipList.contains(ship))
            shipList.remove(ship);
        else
            throw new IllegalArgumentException("The ship isn't into shipList");
    }

    /**
     *
     * @return winning condition. Compare list to empty list. Empty list means that current player
     * won the game, because he destroyed all opponent's ships.
     */
    public boolean isWinner() {
        return shipList.isEmpty();
    }
}
