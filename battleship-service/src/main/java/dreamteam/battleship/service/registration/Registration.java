package dreamteam.battleship.service.registration;

import dreamteam.battleship.logic.ship.Ship;
import dreamteam.battleship.logic.ship.ShipFactory;
import dreamteam.battleship.logic.ship.ShipType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.SwaggerDefinition;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.web.Swagger2Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

/**
 * The Class is responsible for registering the players by session
 */
@RestController
@Scope("session")
public class Registration {

    final static Logger logger = Logger.getLogger(Registration.class);

    protected Player player;

    @RequestMapping(method = RequestMethod.GET, path = "/register")
    public RegistrationResponse register(HttpSession session,
                                         @RequestParam(name = "name") String name,
                                         @RequestParam(name = "surname") String surname) {

        logger.debug("registering the player " + name +  "  " + surname + "sessionId" + " session: " + (session!=null));
        KeyGenerator generator = new KeyGenerator();

        player = new Player(name, surname, generator.generate(), new LinkedList<>());
        logger.debug("register complete");
        return new RegistrationResponse(player);
    }

    // TODO do something to create all thinks such these one in one creator or builder
    private List<Ship> shipList() {
        List<Ship> list = new LinkedList<>();

        list.add(ShipFactory.create(ShipType.oneMast));
        list.add(ShipFactory.create(ShipType.twoMast));
        list.add(ShipFactory.create(ShipType.threeMast));
        list.add(ShipFactory.create(ShipType.fourMast));

        return list;
    }

    public Player getPlayer(){
        return player;
    }
}
