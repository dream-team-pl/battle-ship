package dreamteam.battleship.service.springcontroller.gamecontroller;

import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.service.springcontroller.model.GameMode;
import dreamteam.battleship.service.springcontroller.model.Player;

/**
 * Created by ehsan on 09.08.16.
 */
public class GameControllerBuilder {

    public static final IGameController gameControllerInstance(Player player, MovementManager manager, GameMode mode){
        IGameController controller = null;
        switch (mode) {
            case NORMAL_MODE:
                controller = new NormalController(player, manager);
                break;
            case GUN_SALUTE_MODE:
                break;
            case SINGLE_NORMAL_MODE:
                break;
            case SINGLE_GUN_SALUTE_MODE:
                break;
        }

        return controller;
    }


}
