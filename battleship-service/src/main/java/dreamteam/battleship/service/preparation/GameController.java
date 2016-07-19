package dreamteam.battleship.service.preparation;

import dreamteam.battleship.logic.arbiter.ArbiterImpl;
import dreamteam.battleship.logic.arbiter.MovementContainerImpl;
import dreamteam.battleship.logic.movement.DamageManager;
import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.service.registration.Player;

/**
 * Created by egolesor on 19.07.16.
 */
public class GameController {

    Player player1, player2;

    MovementManager manager1, manager2;

    private boolean isTheGameStarted;

    private MovementManager currentManager;
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

    public void startGame(){
        if(!isTheGameStarted) {
            manager1 = new DamageManager(manager2.getBoard(), new MovementContainerImpl(), new ArbiterImpl(player2.shipList()));
            manager2 = new DamageManager(manager1.getBoard(), new MovementContainerImpl(), new ArbiterImpl(player1.shipList()));
            currentManager = manager1;
        }
    }

    public MovementStatus shoot(int fieldNumber, Player player){
        MovementStatus status = MovementStatus.INVALID_MOVEMENT;
        if(validatePlayer(player)){
            status = currentManager.damage(fieldNumber);
        }
        return status;
    }

    private boolean validatePlayer(Player player) {
        if(currentManager==manager1){
            return player==player1;
        }
        return player==player2;
    }

    public void nextPlayer(){
        if(currentManager == manager1){
            currentManager = manager2;
        }else{
            currentManager = manager1;
        }
    }
}
