package dreamteam.battleship.service.springcontroller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpSession;

/**
 * Responsible to generate a prepare ThreadName
 */
public abstract class BattleShipServiceBase implements InitializingBean{

    public void init(HttpSession session){
        Thread.currentThread().setName(getClass().getName() + "T:=" + Thread.currentThread().getId() + " | S:=" + session.getId());
    }

}
