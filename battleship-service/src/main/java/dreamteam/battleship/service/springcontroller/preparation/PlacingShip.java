package dreamteam.battleship.service.springcontroller.preparation;

import dreamteam.battleship.logic.board.Board;
import dreamteam.battleship.logic.board.Direction;
import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.logic.movement.PlaceShipManager;
import dreamteam.battleship.logic.ship.Ship;
import dreamteam.battleship.logic.ship.ShipFactory;
import dreamteam.battleship.logic.ship.ShipType;
import dreamteam.battleship.service.springcontroller.BattleShipServiceBase;
import dreamteam.battleship.service.springcontroller.model.Player;
import dreamteam.battleship.service.springcontroller.model.response.Response;
import dreamteam.battleship.service.springcontroller.util.SessionUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by egolesor on 18.07.16.
 */
@RestController
@Scope("session")
public class PlacingShip extends BattleShipServiceBase {

    final private static Logger logger = Logger.getLogger(PlacingShip.class);

    protected MovementManager manager = new PlaceShipManager(new Board(10));

    protected List<ShipType> availableShips = availableShipList();

    protected Player player;

    @Autowired
    protected HttpSession session;

    @RequestMapping(method = RequestMethod.GET, path = "/place")
    public Response place(@RequestParam(name = "type") ShipType type,
                          @RequestParam(name = "fieldNumber") int fieldNumber,
                          @RequestParam(name = "direction") Direction direction) {

        logger.debug("Placing the ship " + type + " on field number " + fieldNumber + " " + direction);
        MovementStatus status = MovementStatus.TRY_AGAIN;
        if(validShip(type)){
            //getting the ship from the player
            // put the ship from the player
            Ship ship = ShipFactory.create(type);
            status = manager.tryPutShip(ship, fieldNumber, direction);
            shipListUpdate(status, type, ship);
        }
        logger.debug("Placement completed with status " + status);
        return Response.place(status, availableShips);
    }

    // TODO do something to create all thinks such these one in one creator or builder
    private List<ShipType> availableShipList() {
        List<ShipType> list = new LinkedList<>();
        list.add(ShipType.oneMast);
        list.add(ShipType.twoMast);
        list.add(ShipType.threeMast);
        list.add(ShipType.fourMast);
        return list;
    }



    private void shipListUpdate(MovementStatus status, ShipType type, Ship ship) {
        if(status.equals(MovementStatus.SUCCESS)){
            availableShips.remove(type);
            player.addShip(ship);
        }
    }

    private boolean validShip(ShipType type){
        return availableShips.contains(type);
    }

    public MovementManager myManager(){
        return manager;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.init(session);
        player = SessionUtil.getMyPlayer(session);
    }
}
