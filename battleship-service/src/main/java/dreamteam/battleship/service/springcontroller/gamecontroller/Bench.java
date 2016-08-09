package dreamteam.battleship.service.springcontroller.gamecontroller;

import org.springframework.stereotype.Component;

/**
 * Created by egolesor on 19.07.16.
 */
@Component
public class Bench {

    private IGameController normalController;

    private IGameController gunSaluteController;

    public synchronized boolean isFree(boolean gunSaluteMode){
        return gunSaluteMode? gunSaluteController== null : normalController==null;
    }

    public synchronized void letSit(IGameController controller, boolean gunSaluteMode ){
        if(gunSaluteMode){
            gunSaluteController = controller;
        }else {
            normalController = controller;
        }
    }

    public synchronized IGameController pickController(boolean gunSaluteMode){
        IGameController controller ;
        if(gunSaluteMode){
            controller = gunSaluteController;
            gunSaluteController=null;
        }else{
            controller = normalController;
            normalController = null;
        }

        return controller;
    }
}
