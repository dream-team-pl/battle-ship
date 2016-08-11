package dreamteam.battleship.service.springcontroller.model.response;

import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.springcontroller.model.Player;

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
}
