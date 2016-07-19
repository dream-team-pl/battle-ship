package dreamteam.battleship.logic.movement;

import dreamteam.battleship.logic.board.Board;

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

    @Override
    public Board getBoard() {
        return board;
    }
}
