package dreamteam.battleship.service.springcontroller.gamecontroller;

import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.service.springcontroller.model.GameMode;
import dreamteam.battleship.service.springcontroller.model.Player;

/**
 * A builder to build controller according to the mode.
 */
public class GameControllerBuilder {

    /**
     *
     * Building an instance of the controller
     * @param player The player1 to add to the controller
     * @param manager The manager of the first player
     * @param mode GameMode
     * @return A proper controller according to the mode
     */
    public static final IGameController gameControllerInstance(Player player, MovementManager manager, GameMode mode){
        IGameController controller;
        //TODO Edit the switch case and add new controllers by the mode
        switch (mode) {
            case NORMAL_MODE:
                controller = new NormalController(player, manager);
                break;
            case GUN_SALUTE_MODE:
                controller = new GunSaluteController(player, manager);
                break;
            default:
                controller = new NormalController(player, manager);
        }

        return controller;
    }

}
