package nl.tudelft.jpacman.level;

import com.google.common.collect.Lists;
import nl.tudelft.jpacman.board.*;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.sprite.Sprite;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Tests various aspects of level.
 * 
 * @author Jeroen Roosen 
 */
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

	@Test
	public void testAddRemoveObserver() throws Exception {
		Level.LevelObserver observer = mock(Level.LevelObserver.class);
		level.addObserver(observer);
        try{
            level.removeObserver(observer);
        } catch(Exception exc){
            fail();
        }
	}

	@Test
	public void testGetBoard() throws Exception {
        Board board = level.getBoard();
        assertEquals(this.board, board);
	}

	@Test
	public void testMove() throws Exception {
        Square s0_0 = new BasicSquare();
        Square s0_1 = new BasicSquare();
        Square s0_2 = new BasicSquare();
        Square s1_0 = new BasicSquare();
        Square s1_1 = new BasicSquare();
        Square s1_2 = new BasicSquare();
        BasicUnit myUnit = new BasicUnit();
        myUnit.occupy(s0_0);
        Square[][] grid = new Square[][]{{s0_0, s0_1, s0_2}, {s1_0, s1_1, s1_2}};
        Board board = new BoardFactory(mock(PacManSprites.class)).createBoard(grid);
        s1_0 = s0_0;
        Level level = new Level(board, Lists.newArrayList(ghost), Lists.newArrayList(s0_0, s0_1), collisions);
        Player p1 = mock(Player.class);
        level.registerPlayer(p1);
        level.move(myUnit, Direction.NORTH);
        assertEquals(s1_0, myUnit.getSquare());
	}

	@Test
	public void testIsAnyPlayerAlive() throws Exception {
        Player p = mock(Player.class);
        level.registerPlayer(p);
        p.setAlive(false);
        assertFalse(level.isAnyPlayerAlive());
	}

	@Test
	public void testRemainingPellets() throws Exception {
        Square s0_0 = new BasicSquare();
        Pellet pellet = new Pellet(100, null);
        pellet.occupy(s0_0);
        Square s0_1 = new BasicSquare();
        Square s0_2 = new BasicSquare();
        Square s1_0 = new BasicSquare();
        Square s1_1 = new BasicSquare();
        Square s1_2 = new BasicSquare();
        BasicUnit myUnit = new BasicUnit();
        myUnit.occupy(s0_0);
        Square[][] grid = new Square[][]{{s0_0, s0_1, s0_2}, {s1_0, s1_1, s1_2}};
        Board board = new BoardFactory(mock(PacManSprites.class)).createBoard(grid);
        Level level = new Level(board, Lists.newArrayList(ghost), Lists.newArrayList(s0_0, s0_1), collisions);
        assertEquals(1, level.remainingPellets());
        assertFalse(2 == level.remainingPellets());
	}
}
