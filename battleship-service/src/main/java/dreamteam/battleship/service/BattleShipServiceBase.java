package dreamteam.battleship.service;

import org.springframework.web.bind.annotation.InitBinder;

/**
 * Responsible to generate a prepare ThreadName
 */
public class BattleShipServiceBase {

    @InitBinder
    public void init(){
        Thread.currentThread().setName(getClass().getName() + "T:=" + Thread.currentThread().getId());
    }

}
