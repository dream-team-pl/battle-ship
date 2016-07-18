package dreamteam.battleship.logic.board;


import dreamteam.battleship.logic.ship.Ship;

/**
 * API for board
 */
public interface IBoard {

    /**
     * Method for getting ship at specyfic position
     * @param fieldNumber starting filed
     * @return <ul>
     *              <li>
     *                  ship - if ship is at specyfic postion
     *              </li>
     *              <li>
     *                  null - if ship doesn't exist at specyfic position
     *              </li>
     *          </ul>
     */
    Ship shipOn(int fieldNumber);

    /**
     * Method for placing ship on board
     * @param fieldNumber starting filed
     * @param ship
     * @param direction HORIZONTAL or VERTICAL
     * @return <ul>
     *              <li>
     *                  true - if ship was placed
     *              </li>
     *              <li>
     *                  false - if ship wasn't placed
     *              </li>
     *          </ul>
     */
    boolean placeShip(int fieldNumber, Ship ship, Direction direction);

    /**
     *  Method for checking possibility to place ship at specyfic position
     * @param fieldNumber starting filed
     * @param ship
     * @param direction HORIZONTAL or VERTICAL
     * @return <ul>
     *              <li>
     *                  true - place for the ship exists
     *              </li>
     *              <li>
     *                  false - place for the ship doesn't exist
     *              </li>
     *          </ul>
     */
    boolean isPlaceForTheShip(int fieldNumber,Ship ship, Direction direction);

    /**
     * @return size of board (ex. when board have dimension 3x3 it wille return 3)
     */
    int size();

    /**
     * @return it will return maxSize. It means that it will be size*size
     */
    int maxSize();
}
