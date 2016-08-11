package dreamteam.battleship.service.springcontroller.restart;

import dreamteam.battleship.service.springcontroller.BattleShipServiceBase;
import dreamteam.battleship.service.springcontroller.util.SessionUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by ehsan on 05.08.16.
 */
@RestController
@Scope("session")
public class RestartGame extends BattleShipServiceBase {

    private static Logger logger = Logger.getLogger(RestartGame.class);
    @Autowired
    HttpSession session;

    @RequestMapping(method = RequestMethod.GET, path = "/restart")
    public void restart(){
        logger.debug("Restarting the game");
        SessionUtil.restart(session);
        logger.debug("END");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        super.init(session);
    }
}
