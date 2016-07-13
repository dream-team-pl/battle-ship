package dreamteam.battleship.arbiter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Tests for MovementContainerImpl Class
 */
public class MovementContainerTest {

    /**
     *
     * @return set of movements
     */
    @DataProvider
    public Object[][] setOfMovements() {
        return new Object[][] {
                {1, false},
                {3, true},
                {24, true},
                {31, true},
                {33, false},
                {50, true},
                {57, false},
                {77, false},
                {93, true}
        };
    }

    /**
     *
     * @param field - field on Board
     * @param isDamaged - information about player's attack. If player shot, isDamaged = true
     *                  else isDamaged = false
     *
     *                  First the pair: key, value are added to movements variable in Movement Container class,
     *                  then we check if we get value by passing to method get: key=field.
     */
    @Test(dataProvider = "setOfMovements")
    public void allowAddingMovement(Integer field, Boolean isDamaged) {
        // given
        MovementContainer movementContainer = new MovementContainerImpl();

        // when
        movementContainer.addMovement(field, isDamaged);

        // then
        assertEquals(movementContainer.movements.get(field), isDamaged);
    }

    /**
     *
     * @return sets of movements
     */
    @DataProvider
    public Object[][] setOfMovements1() {
        return new Object[][] {
                {1, false, 2, true},
                {3, true, 7, false},
                {14, true, 22, false},
                {44, true, 11, false},
                {33, false, 21, true},
                {30, true, 55, true},
                {83, false, 77, true},
                {77, false, 1, true},
                {9, true, 8, false}
        };
    }

    /**
     *
     * @param field1 - field on board
     * @param isDamaged1 - information about player's attack. If player shot, isDamaged = true
     *                  else isDamaged = false
     * @param field2 - field on board
     * @param isDamaged2 - information about player's attack. If player shot, isDamaged = true
     *                  else isDamaged = false
     *
     *                   containsMovements method from MovementContainerImpl is tested. Pair value, key is placed in map and than
     *                   we check if pair is in map
     */
    @Test(dataProvider = "setOfMovements1")
    public void checkAvailableMovements(Integer field1, Boolean isDamaged1, Integer field2, Boolean isDamaged2) {
        // given
        MovementContainer movementContainer = new MovementContainerImpl();

        // when
        movementContainer.addMovement(field1, isDamaged1);
        movementContainer.addMovement(field2, isDamaged2);

        // then
        assertTrue(movementContainer.containsMovement(field1));
        assertTrue(movementContainer.containsMovement(field2));
    }

    /**
     *
     * @return set of movement and random key (another than key which we use to put our data(boolean) to map)
     */
    @DataProvider
    public Object[][] objectAndRandomKey() {
        return new Object[][] {
                {1, false, 2},
                {34, true, 99},
                {12, true, 9},
                {70, true, 59},
                {68, false, 90},
                {31, true, 33},
                {99, false, 40},
                {5, false, 77}
        };
    }

    /**
     *
     * @param field - field on board
     * @param isDamaged - information about player's attack. If player shot, isDamaged = true
     *                  else isDamaged = false
     * @param key - field on board
     *
     *                   containsMovements method from MovementContainerImpl is tested. Pair value, key is placed in map and than
     *                   we check if pair is in map, but we use to this another key than we use when we putting object into map
     */
    @Test(dataProvider = "objectAndRandomKey")
    public void checkUnAvailableMovements(Integer field, Boolean isDamaged, Integer key) {
        // given
        MovementContainer movementContainer = new MovementContainerImpl();

        // when
        movementContainer.addMovement(field, isDamaged);

        // then
        assertFalse(movementContainer.containsMovement(key));
    }
}
