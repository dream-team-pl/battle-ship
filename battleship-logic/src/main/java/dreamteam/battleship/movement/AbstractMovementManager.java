package dreamteam.battleship.movement;

import dreamteam.battleship.board.Board;

/**
 * Created by ehsan on 12.07.16.
 */
public abstract class AbstractMovementManager implements MovementManager{

    protected final Board board;
    AbstractMovementManager(Board board){
        this.board = board;
    }

    boolean isValidFieldNumber(int fieldNumebr){
        return (fieldNumebr>0 && fieldNumebr < MAX_MAP_SIZE);
    }

}
