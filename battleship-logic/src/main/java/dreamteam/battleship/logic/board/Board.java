package dreamteam.battleship.logic.board;


import dreamteam.battleship.logic.ship.Ship;

import java.util.HashMap;
import java.util.Map;


/**
 * Simple representation of board
 */
public class Board implements IBoard {

    private final Integer size;
    private final Map<Integer, Ship> battleShipMap;
    private final BoardHelper boardHelper;

    public Board(int size) {
        this.size = size;
        this.battleShipMap = new HashMap<Integer, Ship>(size * size);
        this.boardHelper =new BoardHelper(this);
    }

    @Override
    public Ship shipOn(int fieldNumber) {
        return this.battleShipMap.get(fieldNumber);
    }

    @Override
    public boolean isPlaceForTheShip(int fieldNumber,Ship ship,  Direction direction) {
        return boardHelper.isPlaceForTheShip(fieldNumber,ship,direction);
    }


    @Override
    public boolean placeShip(int fieldNumber, Ship ship, Direction direction) {
        if (isPlaceForTheShip(fieldNumber,ship,direction)){
            if(direction==Direction.HORIZONTAL) {
                for (int i = 0; i < ship.size();i++) {
                    battleShipMap.put(fieldNumber+i,ship);
                }
            }else if(direction==Direction.VERTICAL){
                for (int i = 0; i < ship.size();i++) {
                    battleShipMap.put(fieldNumber+i*size,ship);
                }
            }
            return true;
        }
        return false;
    }
    @Override
    public int size() {
        return this.size;
    }
    @Override
    public int maxSize() {
        return size()*size();
    }

    @Override
    public String toString() {
        return  battleShipMap.toString();
    }
}
