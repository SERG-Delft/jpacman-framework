package nl.tudelft.jpacman.level;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Tests various aspects of level.
 * 
 * @author Jeroen Roosen 
 */
// The four suppresswarnings ignore the same rule, which results in 4 same string literals
@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.TooManyStaticImports"})
public class LevelTest {

	/**
	 * The level under test.
	 */
	private Level level;

	/**
	 * An NPC on this level.
	 */
	private final NPC ghost = mock(NPC.class);

	/**
	 * Starting position 1.
	 */
	private final Square square1 = mock(Square.class);

	/**
	 * Starting position 2.
	 */
	private final Square square2 = mock(Square.class);

	/**
	 * The board for this level.
	 */
	private final Board board = mock(Board.class);
	
	/**
	 * The collision map.
	 */
	private final CollisionMap collisions = mock(CollisionMap.class);

	/**
	 * Sets up the level with the default board, a single NPC and a starting
	 * square.
	 */
	@Before
	public void setUp() {
		final long defaultInterval = 100L;
		level = new Level(board, Lists.newArrayList(ghost), Lists.newArrayList(
				square1, square2), collisions);
		when(ghost.getInterval()).thenReturn(defaultInterval);
	}

	/**
	 * Validates the state of the level when it isn't started yet.
	 */
	@Test
	public void noStart() {
		assertFalse(level.isInProgress());
	}

	/**
	 * Validates the state of the level when it is stopped without starting.
	 */
	@Test
	public void stop() {
		level.stop();
		assertFalse(level.isInProgress());
	}

	/**
	 * Validates the state of the level when it is started.
	 */
	@Test
	public void start() {
		level.start();
		assertTrue(level.isInProgress());
	}

	/**
	 * Validates the state of the level when it is started then stopped.
	 */
	@Test
	public void startStop() {
		level.start();
		level.stop();
		assertFalse(level.isInProgress());
	}

	/**
	 * Verifies registering a player puts the player on the correct starting
	 * square.
	 */
	@Test
	@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
	public void registerPlayer() {
		Player p = mock(Player.class);
		level.registerPlayer(p);
		verify(p).occupy(square1);
	}

	/**
	 * Verifies registering a player twice does not do anything.
	 */
	@Test
	@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
	public void registerPlayerTwice() {
		Player p = mock(Player.class);
		level.registerPlayer(p);
		level.registerPlayer(p);
		verify(p, times(1)).occupy(square1);
	}

	/**
	 * Verifies registering a second player puts that player on the correct
	 * starting square.
	 */
	@Test
	@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
	public void registerSecondPlayer() {
		Player p1 = mock(Player.class);
		Player p2 = mock(Player.class);
		level.registerPlayer(p1);
		level.registerPlayer(p2);
		verify(p2).occupy(square2);
	}

	/**
	 * Verifies registering a third player puts the player on the correct
	 * starting square.
	 */
	@Test
	@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
	public void registerThirdPlayer() {
		Player p1 = mock(Player.class);
		Player p2 = mock(Player.class);
		Player p3 = mock(Player.class);
		level.registerPlayer(p1);
		level.registerPlayer(p2);
		level.registerPlayer(p3);
		verify(p3).occupy(square1);
	}
}
