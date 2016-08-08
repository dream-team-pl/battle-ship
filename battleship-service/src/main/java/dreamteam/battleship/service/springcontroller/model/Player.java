package dreamteam.battleship.service.springcontroller.model;

import dreamteam.battleship.logic.ship.Ship;

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
        return availableShips;
    }

    public void addShip(Ship ship){
        availableShips.add(ship);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return identification != null ? identification.equals(player.identification) : player.identification == null;

    }

    @Override
    public int hashCode() {
        return identification != null ? identification.hashCode() : 0;
    }
}
