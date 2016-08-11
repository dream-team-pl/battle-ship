package dreamteam.battleship.service.springcontroller.gamecontroller;

import dreamteam.battleship.service.springcontroller.model.GameMode;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by egolesor on 19.07.16.
 */
@Component
public class Bench {

    private Map<GameMode, IGameController> controllers = new EnumMap<>(GameMode.class);

    public synchronized boolean isFree(GameMode gameMode){
        return !controllers.containsKey(gameMode);
    }

    public synchronized void letSit(IGameController controller, GameMode gameMode ){
        controllers.put(gameMode, controller);
    }

    public synchronized IGameController pickController(GameMode gameMode){
        IGameController controller = controllers.get(gameMode);
        controllers.remove(gameMode);
        return controller;
    }
}
