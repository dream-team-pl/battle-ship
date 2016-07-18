package dreamteam.battleship.service;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.bind.annotation.*;

/**
 * The Class is responsible for registering the players by session
 */
@RestController
@Scope("session")
@CrossOrigin("*")
public class Registration {

    final static Logger logger = Logger.getLogger(Registration.class);

    protected Player player;

    @RequestMapping(method = RequestMethod.GET, path = "/register")
    public RegistrationResponse register(MockHttpSession session,
                                         @RequestParam(name = "name") String name,
                                         @RequestParam(name = "surname") String surname) {

        logger.debug("registering the player " + name +  "  " + surname + "sessionId" + " session: " + (session!=null));
        KeyGenerator generator = new KeyGenerator();
        player = new Player(generator.generate(), name, surname);
        logger.debug("register complete");
        return new RegistrationResponse(player);
    }
}
