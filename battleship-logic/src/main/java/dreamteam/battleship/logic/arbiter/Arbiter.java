package dreamteam.battleship.logic.arbiter;

import dreamteam.battleship.logic.ship.Ship;

import java.util.List;

/**
 * Arbiter is responsible for checking winning condition. He remove ships from the list, and check if player
 * removed all ships.
 */
public abstract class Arbiter {

    /**
     * List of ships that are alived
     */
    protected List<Ship> shipList;

    /**
     *
     * @param ship - to remove. Remove a ship from list.
     */
    public abstract void removeShip(Ship ship);

    /**
     *
     * @return winning condition. Compare list to empty list. Empty list means that current player
     * won the game, because he destroyed all opponent's ships.
     */
    public abstract boolean isWinner();

    public abstract int numberOfAliveShips();
}
