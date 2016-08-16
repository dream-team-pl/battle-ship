package dreamteam.battleship.service.springcontroller.model.response;

import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.logic.ship.ShipType;
import dreamteam.battleship.service.springcontroller.model.Player;

import java.util.List;
import java.util.Map;

/**
 * Created by ehsan on 11.08.16.
 */
public interface Response {

    static Register register(Player player){
        return new Register(player);
    }

    static Place place(MovementStatus status, List<ShipType> availableShips){
        return new Place(status, availableShips);
    }

    static Organizer organizer(boolean readyToPlay){
        return new Organizer(readyToPlay);
    }

    static ShootingResult shootingResult(MovementStatus status, Map<Integer, Boolean> resultFromOpponentBoard) {
        return new ShootingResult(status, resultFromOpponentBoard);
    }

    static ShootingResult shootingResult(MovementStatus status, Player winner, Map<Integer, Boolean> resultFromOpponentBoard) {
        return new ShootingResult(status, winner, resultFromOpponentBoard);
    }

    static TurnStatus turnStatus(Map<Integer, Boolean> myDamages, boolean isMyTurn, Player winner, int numberOfShots) {
        return new TurnStatus(myDamages, isMyTurn, winner, numberOfShots);
    }

    static TurnStatus turnStatus(Map<Integer, Boolean> myDamages, boolean isMyTurn, Player winner, int numberOfShots, boolean gameOver) {
        return new TurnStatus(myDamages, isMyTurn, winner, numberOfShots, gameOver);
    }
}
