package dreamteam.battleship.service.preparation;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by egolesor on 19.07.16.
 */
@Component
public class Bench {

    private GameController place1;

    public synchronized boolean isFree(){
        return place1 == null;
    }

    public synchronized void letSit(GameController controller){
        place1 = controller;
    }

    public synchronized GameController pickController(){
        GameController controller = place1;
        place1 = null;
        return controller;
    }
}
