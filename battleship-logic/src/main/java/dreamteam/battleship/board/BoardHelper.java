package dreamteam.battleship.board;

import dreamteam.battleship.ship.Ship;

import java.util.ArrayList;
import java.util.List;

import static dreamteam.battleship.board.Direction.HORIZONTAL;
import static dreamteam.battleship.board.Direction.VERTICAL;

/**
 * BoardHelper is responsible for helping with checking free positions on board.
 * It is necessary for checking it is possible to place ship on board
 */
public class BoardHelper {

    private final IBoard board;
    public BoardHelper(Board board) {
        this.board=board;
    }

    /**
     * Checking if it is possibility to place ship in board range.
     * @param startField
     * @param ship
     * @param direction
     * @return <ul>
     *              <li>
     *                  true - ship is in board range
     *              </li>
     *              <li>
     *                  false - ship is not in board range
     *              </li>
     *          </ul>
     */
    boolean isShipIsInBoardRange(int startField, Ship ship, Direction direction) {
        if (startField < 1 || startField > board.maxSize()) {
            return false;
        }
        if (direction == VERTICAL) {
            return (board.size() * (ship.size() - 1) + startField) <= board.maxSize();
        }
        if (direction == HORIZONTAL) {
            if (startField % board.size()  == 0) {
                return startField + (ship.size() - 1) <= ((startField / board.size() ) * board.size() );
            } else {
                return (startField + (ship.size() - 1)) <= ((startField / board.size()  + 1) * board.size() );
            }
        }
        return false;
    }

    /**
     *  Checking if position is on min border site.
     *  For board size 4x4 and horizontal case it will be numbers 1,2,3,4
     *  For board size 4x4 and vertical case it will be numbers 1,5,9,13
     * @param position
     * @param direction
     * @return <ul>
     *              <li>
     *                  true - it is on min board site
     *              </li>
     *              <li>
     *                  false - it isn't on min board site
     *              </li>
     *          </ul>
     */
    boolean itIsOnMinBorderSite(int position, Direction direction) {
        if (direction == Direction.HORIZONTAL)
            return position <= board.size()  ;
        if (direction == Direction.VERTICAL)
            return position % board.size()   == 1;
        return false;
    }

    /**
     *  Checking if position is on max border site.
     *  For board size 4x4 and horizontal case it will be numbers 13,14,15,16
     *  For board size 4x4 and vertical case it will be numbers 4,8,12,16
     * @param position
     * @param direction
     * @return <ul>
     *              <li>
     *                  true - it is on max board site
     *              </li>
     *              <li>
     *                  false - it isn't on max board site
     *              </li>
     *          </ul>
     */

    boolean itIsOnMaxBorderSite(int position, Direction direction) {
        if (direction == Direction.HORIZONTAL) {
            int number = (board.size()   - 1) * board.size()   + 1;
            return position >= number && position <= board.maxSize();
        }
        if (direction == Direction.VERTICAL)
            return position % board.size()  == 0;
        return false;
    }

    /**
     * Method responsible for finding positions before position row
     *
     *  For board size 3x3 and position 5 method will return  1,2,3
     * @param position
     * @return
     */

    List<Integer> positionsBeforeRow(int position) {
        List<Integer> positionsToCheckList = new ArrayList<Integer>();
        if (!itIsOnMaxBorderSite(position,Direction.VERTICAL)&&!itIsOnMinBorderSite(position,Direction.VERTICAL)) {
            addToList((position-board.size() - 1),positionsToCheckList);
            addToList((position-board.size() +1),positionsToCheckList);
        }else if (itIsOnMaxBorderSite(position,Direction.VERTICAL)) {
            addToList((position-board.size() - 1),positionsToCheckList);
        }else if (itIsOnMinBorderSite(position,Direction.VERTICAL)) {
            addToList((position-board.size() + 1),positionsToCheckList);
        }
        addToList((position-board.size() ),positionsToCheckList);
        return positionsToCheckList;
    }
    /**
     * Method responsible for adding to {@paramref positionsToCheckList} positions after row
     *
     *  For board size 3x3 and position 5 method add 7,8,9 to {@paramref positionsToCheckList}
     *
     * @param position
     * @return
     */
    List<Integer> positionsAfterRow(int position) {
        List<Integer> positionsToCheckList = new ArrayList<Integer>();
        if (!itIsOnMaxBorderSite(position,Direction.VERTICAL)&&!itIsOnMinBorderSite(position,Direction.VERTICAL)) {
            addToList((position+board.size() - 1),positionsToCheckList);
            addToList((position+board.size() +1),positionsToCheckList);
        }else if (itIsOnMaxBorderSite( position,  Direction.VERTICAL)) {
            addToList((position+board.size() - 1),positionsToCheckList);
        }else if (itIsOnMinBorderSite(position,Direction.VERTICAL)) {
            addToList((position+board.size() + 1),positionsToCheckList);
        }
        addToList((position+board.size()),positionsToCheckList);
        return positionsToCheckList;
    }

    /**
     * Method responsible for adding to {@paramref positionsToCheckList} positions in row
     *
     *  For board size 3x3 and position 5 method add 4,5,6 to {@paramref positionsToCheckList}
     *
     * @param position
     * @return
     */
    List<Integer> positionsInRow(int position) {
        List<Integer> positionsToCheckList = new ArrayList<Integer>();
        if (!(itIsOnMinBorderSite( position, Direction.VERTICAL))){
            positionsToCheckList.add(position - 1);
        }
         if (!(itIsOnMaxBorderSite( position, Direction.VERTICAL))){
            positionsToCheckList.add(position + 1);
        }
        positionsToCheckList.add(position);

        return positionsToCheckList;
    }


    /**
     * Method responsible for adding to {@paramref positionsToCheckList} positions before column
     *
     *  For board size 3x3 and position 5 method add 1,4,7 to {@paramref positionsToCheckList}
     *
     * @param position
     * @return
     */
    List<Integer> positionsBeforeColumn(int position) {
        List<Integer> positionsToCheckList = new ArrayList<Integer>();
        if (!(itIsOnMinBorderSite( position, Direction.VERTICAL))) {
            addToList((position - board.size() - 1), positionsToCheckList);
            addToList((position - 1), positionsToCheckList);
            addToList((position + board.size() - 1), positionsToCheckList);
        }
        return positionsToCheckList;
    }

    /**
     * Method responsible for adding to {@paramref positionsToCheckList} positions column
     *
     *  For board size 3x3 and position 5 method add 2,5,8 to {@paramref positionsToCheckList}
     *
     * @param position
     * @return
     */
    List<Integer> positionsInColumn(int position) {
        List<Integer> positionsToCheckList = new ArrayList<Integer>();
        addToList(position - board.size() , positionsToCheckList);
        addToList(position, positionsToCheckList);
        addToList(position + board.size() , positionsToCheckList);
        return positionsToCheckList;
    }

    /**
     * Method responsible for adding to {@paramref positionsToCheckList} positions after column
     *
     *  For board size 3x3 and position 5 method add 3,6,9 to {@paramref positionsToCheckList}
     *
     * @param position
     * @return
     */
    List<Integer> positionsAfterColumn(int position) {
        List<Integer> positionsToCheckList = new ArrayList<Integer>();
        if (!(itIsOnMaxBorderSite( position, Direction.VERTICAL))) {
            addToList((position - board.size() + 1), positionsToCheckList);
            addToList((position + 1), positionsToCheckList);
            addToList((position + board.size() + 1), positionsToCheckList);
        }
        return positionsToCheckList;
    }

    /**
     * Method responsible for adding to {@paramref positionsToCheckList} specific position
     * @param position
     * @param positionsToCheckList
     * @return
     */
    boolean addToList(int position, List<Integer> positionsToCheckList) {
        if (position >= 1 && position <= board.maxSize()) {
            positionsToCheckList.add(position);
            return true;
        }
        return false;
    }

    /**
     * Method responsible for getting positions to check.
     * It return positions which depends of ship part index.
     * @param position
     * @param ship
     * @param shipPartIndex
     * @param direction
     * @return
     */
    List<Integer> positionsToCheckAtPartOfShip(int position, Ship ship, int shipPartIndex, Direction direction) {
        List<Integer> positionsToCheck = new ArrayList<Integer>();

        if (direction == Direction.HORIZONTAL) {
            if (!itIsOnMinBorderSite(position,Direction.VERTICAL) && shipPartIndex == 1) {
                positionsToCheck.addAll(positionsBeforeColumn(position));
            }
            if (shipPartIndex == ship.size() && !itIsOnMaxBorderSite(position,Direction.VERTICAL)) {
                positionsToCheck.addAll(positionsAfterColumn(position));
            }
            positionsToCheck.addAll(positionsInColumn(position));
        }else if (direction == Direction.VERTICAL) {
            if (!itIsOnMinBorderSite(position,Direction.HORIZONTAL) && shipPartIndex == 1) {
                positionsToCheck.addAll(positionsBeforeRow(position));
            }
            if (shipPartIndex == ship.size() && !itIsOnMaxBorderSite(position,Direction.HORIZONTAL)) {
                positionsToCheck.addAll(positionsAfterRow(position));
            }
            positionsToCheck.addAll(positionsInRow(position));
        }
        return positionsToCheck;
    }



    /**
     * Method responsible for checking if is place on board for {@paramref ship}.
     * It iterate over the ship parts and getting required fields for ship and checks its.
     *
     * @param position
     * @param ship
     * @param direction
     * @return
     */
    boolean isRequiredPositionsForShipAreEmpty(int position, Ship ship, Direction direction){
        List<Integer> positionToChecks=new ArrayList<Integer>();
        for (int i=0;i<ship.size();i++){
            if (Direction.HORIZONTAL==direction){
                positionToChecks.addAll(positionsToCheckAtPartOfShip(position+i,ship,i+1,direction));
            }else  if (Direction.VERTICAL==direction){
                positionToChecks.addAll(positionsToCheckAtPartOfShip(position+i*board.size() ,ship,i+1,direction));
            }
            for (int pos:positionToChecks){
                if(!isEmptyPosition(pos)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Method responsible for checking if {@paramref position} on board is empty
     * @param position
     * @return
     */
    boolean isEmptyPosition(int position){
        return board.shipOn(position)==null;
    }

    /**
     * Method responsible for checking if is place on board for {@paramref ship}
     * @param position
     * @param ship
     * @param direction
     * @return <ul>
     *              <li>
     *                  true - it is possible to place ship on board. No conflicts with other ships
     *              </li>
     *              <li>
     *                  false - it is'n possible to place ship on board.
     *              </li>
     *          </ul>
     */
    boolean isPlaceForTheShip(int position,Ship ship,Direction direction){
        return isShipIsInBoardRange(position,ship,direction)&& isRequiredPositionsForShipAreEmpty(position,ship,direction);
    }

}
