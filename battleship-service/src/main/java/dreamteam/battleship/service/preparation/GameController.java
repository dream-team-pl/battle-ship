package dreamteam.battleship.service.preparation;

import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.service.registration.Player;

/**
 * Created by egolesor on 19.07.16.
 */
public class GameController {

    Player player1, player2;

    MovementManager manager1, manager2;

    public GameController(Player player1, MovementManager manager1){
        this.player1 = player1;
        this.manager1 = manager1;
    }

    public void addPlayer2(Player player2, MovementManager manager2){
        this.player2 = player2;
        this.manager2 = manager2;
    }

    public boolean isReadyToPlay() {
        return player1 != null && player2 != null;
    }
}
