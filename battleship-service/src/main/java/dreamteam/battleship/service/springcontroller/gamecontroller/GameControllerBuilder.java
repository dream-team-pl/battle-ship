package dreamteam.battleship.service.springcontroller.gamecontroller;

import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.service.springcontroller.model.Player;

/**
 * Created by ehsan on 09.08.16.
 */
public class GameControllerBuilder {

    public static final IGameController gameControllerInstance(Player player, MovementManager manager, boolean isSalute){
        if(isSalute){
            // TODO salute controller
            return new NormalController(player, manager);
        }else {
            return new NormalController(player, manager);
        }
    }


}
