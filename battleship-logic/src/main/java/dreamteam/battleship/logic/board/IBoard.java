package dreamteam.battleship.logic.board;

import dreamteam.battleship.logic.ship.Ship;

/**
 * responsible for placing ships and checking the free places
 */
public interface IBoard {

    Ship shipOn(int fieldNumber);
    boolean placeShip(int fieldNumber,Ship ship,Direction direction);
    boolean isPlaceForTheShip(Ship ship,int fieldNumber,Direction direction);
    int maxSize();
}
