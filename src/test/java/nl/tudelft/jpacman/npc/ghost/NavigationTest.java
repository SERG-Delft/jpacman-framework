package nl.tudelft.jpacman.npc.ghost;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.sprite.PacManSprites;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Tests the various methods provided by the {@link Navigation} class.
 * 
 * @author Jeroen Roosen 
 * 
 */
@SuppressWarnings({"magicnumber", "PMD.AvoidDuplicateLiterals", "PMD.TooManyStaticImports"})
public class NavigationTest {

	/**
	 * Map parser used to construct boards.
	 */
	private MapParser parser;

	/**
	 * Set up the map parser.
	 */
	@Before
	public void setUp() {
		PacManSprites sprites = new PacManSprites();
		parser = new MapParser(new LevelFactory(sprites, new GhostFactory(
				sprites)), new BoardFactory(sprites));
	}

	/**
	 * Verifies that the path to the same square is empty.
	 */
	@Test
	public void testShortestPathEmpty() {
		Board b = parser.parseMap(Lists.newArrayList(" ")).getBoard();
		Square s1 = b.squareAt(0, 0);
		Square s2 = b.squareAt(0, 0);
		List<Direction> path = Navigation
				.shortestPath(s1, s2, mock(Unit.class));
		assertEquals(0, path.size());
	}

	/**
	 * Verifies that if no path exists, the result is <code>null</code>.
	 */
	@Test
	public void testNoShortestPath() {
		Board b = parser
				.parseMap(Lists.newArrayList("#####", "# # #", "#####"))
				.getBoard();
		Square s1 = b.squareAt(1, 1);
		Square s2 = b.squareAt(3, 1);
		List<Direction> path = Navigation
				.shortestPath(s1, s2, mock(Unit.class));
		assertNull(path);
	}

	/**
	 * Verifies that having no traveller ignores terrain.
	 */
	@Test
	public void testNoTraveller() {
		Board b = parser
				.parseMap(Lists.newArrayList("#####", "# # #", "#####"))
				.getBoard();
		Square s1 = b.squareAt(1, 1);
		Square s2 = b.squareAt(3, 1);
		List<Direction> path = Navigation.shortestPath(s1, s2, null);
		assertArrayEquals(new Direction[] { Direction.EAST, Direction.EAST },
				path.toArray(new Direction[] {}));
	}

	/**
	 * Tests if the algorithm can find a path in a straight line.
	 */
	@Test
	public void testSimplePath() {
		Board b = parser.parseMap(Lists.newArrayList("####", "#  #", "####"))
				.getBoard();
		Square s1 = b.squareAt(1, 1);
		Square s2 = b.squareAt(2, 1);
		List<Direction> path = Navigation
				.shortestPath(s1, s2, mock(Unit.class));
		assertArrayEquals(new Direction[] { Direction.EAST },
				path.toArray(new Direction[] {}));
	}

	/**
	 * Verifies that the algorithm can find a path when it has to take corners.
	 */
	@Test
	public void testCornerPath() {
		Board b = parser.parseMap(
				Lists.newArrayList("####", "#  #", "## #", "####")).getBoard();
		Square s1 = b.squareAt(1, 1);
		Square s2 = b.squareAt(2, 2);
		List<Direction> path = Navigation
				.shortestPath(s1, s2, mock(Unit.class));
		assertArrayEquals(new Direction[] { Direction.EAST, Direction.SOUTH },
				path.toArray(new Direction[] {}));
	}

	/**
	 * Verifies that the nearest object is detected.
	 */
	@Test
	public void testNearestUnit() {
		Board b = parser
				.parseMap(Lists.newArrayList("#####", "# ..#", "#####"))
				.getBoard();
		Square s1 = b.squareAt(1, 1);
		Square s2 = b.squareAt(2, 1);
		Square result = Navigation.findNearest(Pellet.class, s1).getSquare();
		assertEquals(s2, result);
	}

	/**
	 * Verifies that there is no such location if there is no nearest object.
	 */
	@Test
	public void testNoNearestUnit() {
		Board b = parser.parseMap(Lists.newArrayList(" ")).getBoard();
		Square s1 = b.squareAt(0, 0);
		Unit unit = Navigation.findNearest(Pellet.class, s1);
		assertNull(unit);
	}
	
	/**
	 * Verifies that there is ghost on the default board
	 * next to cell [1, 1].
	 *  
	 * @throws IOException if board reading fails.
	 */
	@Test
	public void testFullSizedLevel() throws IOException {
		try (InputStream i = getClass().getResourceAsStream("/board.txt")) {
			Board b = parser.parseMap(i).getBoard();
			Square s1 = b.squareAt(1, 1);
			Unit unit = Navigation.findNearest(Ghost.class, s1);
			assertNotNull(unit);
		}
	}
}
