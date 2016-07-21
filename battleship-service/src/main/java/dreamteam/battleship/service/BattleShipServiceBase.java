package dreamteam.battleship.service;

import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpSession;

/**
 * Responsible to generate a prepare ThreadName
 */
public class BattleShipServiceBase {

    @InitBinder
    public void init(HttpSession session){
        Thread.currentThread().setName(getClass().getName() + "T:=" + Thread.currentThread().getId() + " | S:=" + session.getId());
    }

}
