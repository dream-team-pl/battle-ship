package dreamteam.battleship.service.registration;

/**
 * Created by ehsan on 14.07.16.
 */
public class Register {

    final public String name;

    final public String surname;

    final public String secureKey;

    final public PlayerType type;

    public Register(String name, String surname, String secureKey, PlayerType type) {
        this.name = name;
        this.surname = surname;
        this.secureKey = secureKey;
        this.type = type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Register register = (Register) o;

        return secureKey.equals(register.secureKey);

    }

    @Override
    public int hashCode() {
        return secureKey.hashCode();
    }
}
