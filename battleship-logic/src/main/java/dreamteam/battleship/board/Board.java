package dreamteam.battleship.board;

import dreamteam.battleship.ship.Ship;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by grzegorz_sledz on 11.07.16.
 */
public class Board implements IBoard {

    public final Integer mapSize;
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

}
