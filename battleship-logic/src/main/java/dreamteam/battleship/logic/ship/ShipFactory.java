package dreamteam.battleship.logic.ship;

/**
 * Class is responsible for creating new object and set unique id variable
 */
public class ShipFactory {
    protected static int idCounter;

    /**
     *
     * @param shipType - based on this parameter object ...
     * ... @return new Ship
     */
    public static Ship create(ShipType shipType) {
        idCounter++;

        switch(shipType) {
            case oneMast:
                return new MineSweeper(idCounter);
            case twoMast:
                return new Frigate(idCounter);
            case threeMast:
                return new Cruiser(idCounter);
            case fourMast:
                return new BattleShip(idCounter);
            default:
                return null;
        }
    }
}
