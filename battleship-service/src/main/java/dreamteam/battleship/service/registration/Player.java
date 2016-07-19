package dreamteam.battleship.service.registration;

import dreamteam.battleship.logic.ship.Ship;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ehsan on 18.07.16.
 */
public class Player {

    public final String name;
    public final String surname;
    public final String identification;
    private final List<Ship> availableShips;

    public Player(String name, String surname, String identification, List<Ship> availableShips) {
        this.name = name;
        this.surname = surname;
        this.identification = identification;
        this.availableShips = availableShips;
    }

    public List<Ship> shipList(){
        List<Ship> temp = new LinkedList<>();
        Collections.copy(temp, availableShips);
        return temp;
    }
}
