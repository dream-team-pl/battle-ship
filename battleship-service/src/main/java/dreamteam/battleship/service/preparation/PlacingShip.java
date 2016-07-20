package dreamteam.battleship.service.preparation;

import dreamteam.battleship.logic.board.Board;
import dreamteam.battleship.logic.board.Direction;
import dreamteam.battleship.logic.movement.MovementManager;
import dreamteam.battleship.logic.movement.MovementStatus;
import dreamteam.battleship.logic.movement.PlaceShipManager;
import dreamteam.battleship.logic.ship.Ship;
import dreamteam.battleship.logic.ship.ShipFactory;
import dreamteam.battleship.logic.ship.ShipType;
import dreamteam.battleship.service.registration.Player;
import dreamteam.battleship.service.registration.Registration;
import org.apache.log4j.Logger;
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
public class PlacingShip {

    final static Logger logger = Logger.getLogger(PlacingShip.class);

    protected MovementManager manager = new PlaceShipManager(new Board(10));

    protected List<ShipType> availableShips = availableShipList();

    protected Player player;
    @InitBinder
    public void init(HttpSession session){
        player = ((Registration)session.getAttribute("registration")).getPlayer();
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

    @RequestMapping(method = RequestMethod.GET, path = "/place")
    public PlacingResponse place(HttpSession session,
                                 @RequestParam(name = "type") ShipType type,
                                 @RequestParam(name = "fieldNumber") int fieldNumber,
                                 @RequestParam(name = "direction") Direction direction) {

        logger.debug("Placing the ship " + type + " on field number " + fieldNumber + " " + direction);
        MovementStatus status = MovementStatus.TRY_AGAIN;
        if(validShip(type)){
            //getting the ship from the player
            // put the ship from the player
            Ship ship = ShipFactory.create(type);
            player.addShip(ship);
            status = manager.tryPutShip(ship, fieldNumber, direction);
            cleanUpList(status, type);
        }
        logger.debug("Placement completed with status " + status);
        return new PlacingResponse(status, availableShips);
    }

    private void cleanUpList(MovementStatus status, ShipType type) {
        if(status.equals(MovementStatus.SUCCESS)){
            availableShips.remove(type);
        }
    }

    private boolean validShip(ShipType type){
        return availableShips.contains(type);
    }

    public MovementManager myManager(){
        return manager;
    }
}