package nl.tudelft.jpacman.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Test suite to confirm the public API of {@link Square} works as desired.
 *
 * @author Jeroen Roosen 
 */
class SquareTest {

    /**
     * The square under test.
     */
    private Square square;

    /**
     * Resets the square under test.
     */
    @BeforeEach
    void setUp() {
        square = new BasicSquare();
    }

    /**
     * Assert that the square holds the occupant once it has occupied it.
     */
    @Test
    void testOccupy() {
        Unit occupant = mock(Unit.class);
        square.put(occupant);

        assertThat(square.getOccupants()).contains(occupant);
    }

    /**
     * Assert that the square no longer holds the occupant after it has left the
     * square.
     */
    @Test
    void testLeave() {
        Unit occupant = mock(Unit.class);
        square.put(occupant);
        square.remove(occupant);

        assertThat(square.getOccupants()).doesNotContain(occupant);
    }

    /**
     * Assert that the order in which units entered the square is preserved.
     */
    @Test
    void testOrder() {
        Unit o1 = mock(Unit.class);
        Unit o2 = mock(Unit.class);
        square.put(o1);
        square.put(o2);

        assertThat(square.getOccupants()).containsSequence(o1, o2);
    }
}
