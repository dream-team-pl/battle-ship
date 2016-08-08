package dreamteam.battleship.service.springcontroller.restart;

import dreamteam.battleship.service.BattleShipServiceBase;
import dreamteam.battleship.service.springcontroller.util.RestarterUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.jmx.LoggerDynamicMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpSession;

/**
 * Created by ehsan on 05.08.16.
 */
@RestController
@SessionScope
public class RestartGame extends BattleShipServiceBase {

    private static Logger logger = Logger.getLogger(RestartGame.class);
    @Autowired
    HttpSession session;

    @Autowired@Qualifier("RestarterUtil")
    RestarterUtil restarterUtil;

    @RequestMapping(method = RequestMethod.GET, path = "/restart")
    public void restart(){
        logger.debug("Restarting the game");
        restarterUtil.restart(session);
        logger.debug("END");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        super.init(session);
    }
}
