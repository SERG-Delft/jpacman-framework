package nl.tudelft.jpacman.board;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.ghost.Clyde;
import nl.tudelft.jpacman.npc.ghost.Ghost;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite to confirm the public API of {@link Square} works as desired.
 * 
 * @author Jeroen Roosen 
 */
public class SquareTest {

	/**
	 * The squares under test.
	 */
	private Square square;
	private Square[][] s;
	

	/**
	 * Resets the square under test.
	 */
	@Before
	public void setUp() {
		square = new BasicSquare();
		s = new Square[8][8];
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8;j++){
				s[i][j] = new BasicSquare();
			}
		}
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Square square = s[x][y];
				for (Direction dir : Direction.values()) {
					int dirX = (8 + x + dir.getDeltaX()) % 8;
					int dirY = (8 + y + dir.getDeltaY()) % 8;
					Square neighbour = s[dirX][dirY];
					square.link(neighbour, dir);
				}
			}
		}
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
	
	/**
	 * Tests the method isValidRespawnPoint in the class Square to ensure that 
	 * it returns the expected value
	 */
	@Test
	public void testIsValidRespawn(){
		Unit player = mock(Player.class);
		Unit ghost = mock(Clyde.class);
		s[0][0].put(player);
		s[3][3].put(ghost);
		
		
		assertTrue(s[1][1].isValidRespawnPoint(player, 3, null));
		assertTrue(s[5][5].isValidRespawnPoint(player, 3, null));
		assertFalse(s[4][3].isValidRespawnPoint(player, 3, null));
		assertFalse(s[1][2].isValidRespawnPoint(player, 3, null));
		
	}
	
	/**
	 * Tests the method nearestValidRespawn in the class Square to ensure that 
	 * it returns the expected value
	 */
	@Test
	public void testNearestValidRespawn(){
		Unit player = mock(Player.class);
		Unit ghost = mock(Clyde.class);
		s[0][0].put(player);
		s[3][3].put(ghost);
		
		for(int i = 0; i < 8; i++){
			for(int j = 0; i < 8; i++){
				assertTrue(s[i][j].nearestValidRespawn(
						player, null).isValidRespawnPoint(player, 3, null));
			}
		}
		
		
	}
}
