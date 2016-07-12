package dreamteam.battleship.ship;

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
        return new BattleShip(shipType, idCounter);
    }
}
