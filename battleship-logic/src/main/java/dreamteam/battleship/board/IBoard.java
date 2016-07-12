package dreamteam.battleship.board;

import dreamteam.battleship.ship.Ship;

/**
 * Created by grzegorz_sledz on 12.07.16.
 */
public interface IBoard {

    Ship shipOn(int fieldNumber);
    boolean placeShip(int fieldNumber,Ship ship,Direction direction);
    boolean isPlaceForTheShip(Ship ship,int fieldNumber,Direction direction);
    int maxSize();
}
