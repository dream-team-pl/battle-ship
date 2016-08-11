package dreamteam.battleship.service.springcontroller.registration;

import dreamteam.battleship.service.springcontroller.BattleShipServiceBase;
import dreamteam.battleship.service.springcontroller.model.Player;
import dreamteam.battleship.service.springcontroller.model.response.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;

/**
 * The Class is responsible for registering the players by session
 */
@RestController
@Scope("session")
public class Registration extends BattleShipServiceBase {

    final static Logger logger = Logger.getLogger(Registration.class);

    protected Player player;

    @Autowired
    protected HttpSession session;

    @RequestMapping(method = RequestMethod.GET, path = "/register")
    public Response register(@RequestParam(name = "name") String name,
                             @RequestParam(name = "surname") String surname) {

        String key = new KeyGenerator().generate();
        logger.debug("registering the player " + name +  "  " + surname + "sessionId" + " session: " + session.getId());

        player = new Player(name, surname, key, new LinkedList<>());
        logger.debug("register complete");
        return Response.register(player);
    }

    public Player getPlayer(){
        return player;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.init(session);
    }
}
