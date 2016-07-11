package ship;

/**
 * Created by daniel on 11.07.16.
 * Simple representation of our ships. Variables are protected to get access
 * to test part in project. Ship could have size between 1 and 4. Name have to
 * be longer than 0.
 */
public class Ship {

    final protected String name;
    final protected byte size;
    protected int damaged;// FIXME the type of damage must be the same az size

    /**
     *
     * @param name - name of ship
     * @param size - size of ship
     */
    public Ship(String name, byte size) {
        if(size <= 0 || size >4) throw new IllegalArgumentException();
        if(name.length() == 0) throw new IllegalArgumentException();

        this.name = name;
        this.size = size;
    }

    /**
     * decrement damaged variable. We need it when we check if the ship is destroyed.
     */
    public void damage() {
        damaged++;
    }

    /**
     *
     * @return true if the ship is destroyed and false if ship alived
     */
    public boolean isDamaged() {
        return (size - damaged) == 0;
    }

    // FIXME the equal and hash must be carefully implemented. Cus more ships could have the same name size and damaged.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship ship = (Ship) o;

        if (size != ship.size) return false;
        if (damaged != ship.damaged) return false;
        return name != null ? name.equals(ship.name) : ship.name == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (int) size;
        result = 31 * result + damaged;
        return result;
    }
}
