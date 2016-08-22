package dreamteam.battleship.service.springcontroller.model.response;

import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.model.Player;

import java.util.Map;

/**
 * Created by ehsan on 11.08.16.
 */
public class ModelResponseTestUtil {

    public static Player getPlayer(Response response){
        return ((Register)response).player;
    }

    public static MovementStatus placeStatus(Response response){
        return ((Place)response).status;
    }

    public static boolean readyToPlay(Response response){
        return ((Organizer)response).readyToPlay;
    }

    public static MovementStatus shootingStatus(Response response){
        return ((ShootingResult)response).status;
    }

    public static int numberOfShots(Response response){
        return ((TurnStatus)response).numberOfShots;
    }

    public static Map<Integer, Boolean> myDamages(Response response){
        return ((TurnStatus)response).myDamages;
    }

    public static boolean isMyTurn(Response response){
        return ((TurnStatus)response).isMyTurn;
    }

    public static Player winner(Response response){
        return ((TurnStatus)response).winner;
    }

    public static boolean gameOver(Response response){
        return ((TurnStatus)response).gameOver;
    }
}
