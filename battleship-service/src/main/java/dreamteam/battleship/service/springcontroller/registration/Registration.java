package dreamteam.battleship.service.springcontroller.registration;

import dreamteam.battleship.logic.ship.Ship;
import dreamteam.battleship.logic.ship.ShipFactory;
import dreamteam.battleship.logic.ship.ShipType;

import dreamteam.battleship.service.BattleShipServiceBase;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

/**
 * The Class is responsible for registering the players by session
 */
@RestController
@Scope("session")
public class Registration extends BattleShipServiceBase {

    final static Logger logger = Logger.getLogger(Registration.class);

    protected Player player;

    @RequestMapping(method = RequestMethod.GET, path = "/register")
    public RegistrationResponse register(HttpSession session,
                                         @RequestParam(name = "name") String name,
                                         @RequestParam(name = "surname") String surname) {

        String key = new KeyGenerator().generate();
        logger.debug("registering the player " + name +  "  " + surname + "sessionId" + " session: " + session.getId());

        player = new Player(name, surname, key, new LinkedList<>());
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