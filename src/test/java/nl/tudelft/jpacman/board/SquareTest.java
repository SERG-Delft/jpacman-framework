package nl.tudelft.jpacman.board;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite to confirm the public API of {@link Square} works as desired.
 * 
 * @author Jeroen Roosen 
 */
public class SquareTest {

	/**
	 * The square under test.
	 */
	private Square square;

	/**
	 * Resets the square under test.
	 */
	@Before
	public void setUp() {
		square = new BasicSquare();
	}

	/**
	 * Assert that the square holds the occupant once it has occupied it.
	 */
	@Test
	public void testOccupy() {
		Unit occupant = mock(Unit.class);
		square.put(occupant);

		assertTrue(square.getOccupants().contains(occupant));
	}

	/**
	 * Assert that the square no longer holds the occupant after it has left the
	 * square.
	 */
	@Test
	public void testLeave() {
		Unit occupant = mock(Unit.class);
		square.put(occupant);
		square.remove(occupant);

		assertFalse(square.getOccupants().contains(occupant));
	}

	/**
	 * Assert that the order in which units entered the square is preserved.
	 */
	@Test
	public void testOrder() {
		Unit o1 = mock(Unit.class);
		Unit o2 = mock(Unit.class);
		square.put(o1);
		square.put(o2);

		Object[] occupantsAsArray = square.getOccupants().toArray();
		assertArrayEquals(new Object[] { o1, o2 }, occupantsAsArray);
	}
}
