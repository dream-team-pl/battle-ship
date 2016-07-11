package ship;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by daniel on 11.07.16.
 * Test of Ship class.
 */
public class ShipTest {

    @DataProvider
    public Object[][] names (){
        return new Object[][] {
                {"ship1", },
                {"ship2"},
                {"destroyer"},
                {"Al-Pacino"},
                {"Titanic"},
                {""}
        };
    }

    /**
     *
     * @param name - is delivered to check if after creating object Ship, name is initialized
     */
    @Test(dataProvider = "names", expectedExceptions = IllegalArgumentException.class)
    public void setName(String name) {
        // given
        // hardcoding size of ship. It is irrelevant
        Ship ship = new Ship(name, (byte) 0);

        // when
        // ...

        // then
        assertEquals(ship.name, name);
    }

    @DataProvider
    public Object[][] sizes (){
        return new Object[][] {
                {(byte) 0},
                {(byte) 1},
                {(byte) 2},
                {(byte) 3},
                {(byte) 4},
                {(byte) 0},
                {(byte) 7}
        };
    }

    /**
     *
     * @param size - is delivered to check if after creating object Ship, size is initialized
     */
    @Test(dataProvider = "sizes", expectedExceptions = IllegalArgumentException.class)
    public void setSize(byte size) {
        // given
        // hardcoding size of ship. It is irrelevant
        Ship ship = new Ship("", size);

        // when
        // ...

        // then
        assertEquals(ship.size, size);
    }

    @DataProvider
    public Object[][] damaging() {
        return new Object[][] {
                {(byte)4, 3},
                {(byte)4, 2},
                {(byte)4, 1},
                {(byte)3, 3},
                {(byte)2, 1},
                {(byte)1, 1}
        };
    }

    /**
     *
     * @param size - is initialized, to prevent from to many call of damage() method
     * @param damaged - how many times damage() method is called
     */
    @Test(dataProvider = "damaging")
    public void shipIsHit(byte size, int damaged) {
        // given
        Ship ship = new Ship("ship", size);

        // when
        for(int i=0; i<damaged; i++)
            ship.damage();

        // then
        assertEquals(ship.damaged, damaged);
    }

    @DataProvider
    public Object[][] sizeAndDamagedSet() {
        return new Object[][] {
                {(byte) 4, 4},
                {(byte) 3, 3},
                {(byte) 2, 2},
                {(byte) 1, 1}
        };
    }

    /**
     *
     * @param size - is initialized, to prevent from to many call of damage() method, and check how many times
     *             ship can be hit.
     * @param damaged - how many times damage() method is called
     */
    @Test(dataProvider = "sizeAndDamagedSet")
    public void isWinner(byte size, int damaged) {
        // given
        Ship ship = new Ship("ship", size);

        // when
        for(int i=0; i<damaged; i++)
            ship.damage();

        // given
        assertTrue(ship.isDamaged());
    }

    @DataProvider
    public Object[][] sizeAndDamagedSet2() {
        return new Object[][] {
                {(byte) 4, 3},
                {(byte) 3, 2},
                {(byte) 2, 1},
                {(byte) 4, 2},
                {(byte) 4, 1},
                {(byte) 3, 1}
        };
    }

    /**
     *
     * @param size - is initialized, to prevent from to many call of damage() method, and check how many times
     *             ship can be hit.
     * @param damaged - how many times damage() method is called
     */
    @Test(dataProvider = "sizeAndDamagedSet2")
    public void isNotWinner(byte size, int damaged) {
        // given
        Ship ship = new Ship("ship", size);

        // when
        for(int i=0; i<damaged; i++)
            ship.damage();

        // given
        assertFalse(ship.isDamaged());
    }
}
