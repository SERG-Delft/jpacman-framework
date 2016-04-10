package nl.tudelft.jpacman.board;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite to confirm that {@link Unit}s correctly (de)occupy squares.
 * 
 * @author Jeroen Roosen 
 * 
 */
public class OccupantTest {

	/**
	 * The unit under test.
	 */
	private Unit unit;

	/**
	 * Resets the unit under test.
	 */
	@Before
	public void setUp() {
		unit = new BasicUnit();
	}

	/**
	 * Asserts that a unit has no square to start with.
	 */
	@Test
	public void noStartSquare() {
		assertNull(unit.getSquare());
	}

	/**
	 * Tests that the unit indeed has the target square as its base after
	 * occupation.
	 */
	@Test
	public void testOccupy() {
		Square target = new BasicSquare();
		unit.occupy(target);
		assertThat(unit.getSquare(), is(target));
		assertThat(target.getOccupants(), contains(unit));
	}
	
    /**
     * Test that the unit indeed has the target square as its base after
     * double occupation.
     */
	@Test
	public void testReoccupy() {
		Square target = new BasicSquare();
		unit.occupy(target);
		unit.occupy(target);
		assertThat(unit.getSquare(), is(target));
		assertThat(target.getOccupants(), contains(unit));
	}
}
