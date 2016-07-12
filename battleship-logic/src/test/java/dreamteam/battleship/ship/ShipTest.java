package dreamteam.battleship.ship;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test of ShipImpl class.
 */
public class ShipTest {

    /**
     *
     * @return shipTypes, than we can check if after creating ShipImpl, we will initialize ship size
     */
    @DataProvider
    public Object[][] ships (){
        return new Object[][] {
                {ShipType.oneMast},
                {ShipType.twoMast},
                {ShipType.threeMast},
                {ShipType.fourMast}
        };
    }

    /**
     *
     * @param shipType - is delivered to check if after creating object ShipImpl, size is initialized
     */
    @Test(dataProvider = "ships")
    public void setSize(ShipType shipType) {
        // given
        ShipImpl ship = ShipImpl.shipFactory(shipType);

        // when
        // ...

        // then
        assertEquals(ship.shipType.size, shipType.size);
    }

    /**
     *
     * @return shipType and count of damage
     */
    @DataProvider
    public Object[][] shipsAndCountOfDamage() {
        return new Object[][] {
                {ShipType.fourMast, 3},
                {ShipType.fourMast, 2},
                {ShipType.fourMast, 1},
                {ShipType.threeMast, 3},
                {ShipType.twoMast, 1},
                {ShipType.oneMast, 1}
        };
    }

    /**
     *
     * @param shipType - is needed to create ShipImpl object
     * @param damaged - how many times damage() method is called
     *                check how many times ship is damaged
     */
    @Test(dataProvider = "shipsAndCountOfDamage")
    public void shipIsHit(ShipType shipType, int damaged) {
        // given
        ShipImpl ship = ShipImpl.shipFactory(shipType);

        // when
        for(int i=0; i<damaged; i++)
            ship.damage();

        // then
        assertEquals(ship.damaged, damaged);
    }

    /**
     *
     * @return shipType and count of damage
     */
    @DataProvider
    public Object[][] shipsAndCountOfDamage2() {
        return new Object[][] {
                {ShipType.fourMast, 4},
                {ShipType.threeMast, 3},
                {ShipType.twoMast, 2},
                {ShipType.oneMast, 1}
        };
    }

    /**
     *
     * @param shipType - is needed to create ShipImpl object
     * @param damaged - how many times damage() method is called
     *                check is ship is damaged, ship is damaged when we shoot in it the same number times like his size,
     *                this is winning conditions
     */
    @Test(dataProvider = "shipsAndCountOfDamage2")
    public void isWinner(ShipType shipType, int damaged) {
        // given
        ShipImpl ship = ShipImpl.shipFactory(shipType);

        // when
        for(int i=0; i<damaged; i++)
            ship.damage();

        // then
        assertTrue(ship.isDamaged());
    }

    /**
     *
     * @return shipType and count of damage
     */
    @DataProvider
    public Object[][] sizeAndDamagedSet() {
        return new Object[][] {
                {ShipType.fourMast, 3},
                {ShipType.threeMast, 2},
                {ShipType.twoMast, 1},
                {ShipType.fourMast, 2},
                {ShipType.fourMast, 1},
                {ShipType.threeMast, 1}
        };
    }

    /**
     *
     * @param shipType - is needed to create ShipImpl object
     * @param damaged - how many times damage() method is called
     *                check is ship is damaged, ship is damaged when we shoot in it the same number times like his size, but
     *                here we didn't.
     */
    @Test(dataProvider = "sizeAndDamagedSet")
    public void isNotWinner(ShipType shipType, int damaged) {
        // given
        ShipImpl ship = ShipImpl.shipFactory(shipType);

        // when
        for(int i=0; i<damaged; i++)
            ship.damage();

        // given
        assertFalse(ship.isDamaged());
    }
}
