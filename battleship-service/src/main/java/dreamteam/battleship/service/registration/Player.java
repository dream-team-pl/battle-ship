package dreamteam.battleship.service.registration;

/**
 * Created by ehsan on 18.07.16.
 */
public class Player {

    public final String name;
    public final String surname;
    public final String identification;


    public Player(String name, String surname, String identification) {
        this.name = name;
        this.surname = surname;
        this.identification = identification;
    }
}
