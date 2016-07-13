package dreamteam.battleship.board;

import dreamteam.battleship.ship.Ship;

import java.util.HashMap;
import java.util.Map;


/**
 * implements the {@link IBoard}
 */
public class Board implements IBoard {

    private int mapSize = 10; // we have just such mach it may be change in the future
    private final Map<Integer,Ship> battleShipMap;

    public Board(int mapSize){
        this.mapSize=mapSize;
        this.battleShipMap=new HashMap<Integer,Ship>(mapSize);
    }

    public Ship shipOn(int fieldNumber){
        return this.battleShipMap.get(fieldNumber);
    }

    public boolean placeShip(int fieldNumber,Ship ship,Direction direction){
        return false;
    }

    public boolean isPlaceForTheShip(Ship ship,int fieldNumber,Direction direction){
        return false;
    }

    public int maxSize() {
        return mapSize*mapSize;
    }

    
}
