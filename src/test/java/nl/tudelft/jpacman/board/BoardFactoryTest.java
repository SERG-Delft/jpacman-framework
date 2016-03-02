package nl.tudelft.jpacman.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import nl.tudelft.jpacman.sprite.PacManSprites;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the linking of squares done by the board factory.
 * 
 * @author Jeroen Roosen 
 */
public class BoardFactoryTest {

	/**
	 * The factory under test.
	 */
	private BoardFactory factory;
	
	/**
	 * Resets the factory under test.
	 */
	@Before
	public void setUp() {
		PacManSprites sprites = mock(PacManSprites.class);
		factory = new BoardFactory(sprites);
	}
	
	/**
	 * Verifies that a single cell is connected to itself on all sides.
	 */
	@Test
	public void worldIsRound() {
		Square s = new BasicSquare();
		Square[][] grid = new Square[][]{{s}};
		factory.createBoard(grid);
		assertEquals(s, s.getSquareAt(Direction.NORTH));
		assertEquals(s, s.getSquareAt(Direction.SOUTH));
		assertEquals(s, s.getSquareAt(Direction.WEST));
		assertEquals(s, s.getSquareAt(Direction.EAST));
	}
	
	/**
	 * Verifies a chain of cells is connected to the east.
	 */
	@Test
	public void connectedEast() {
		Square s1 = new BasicSquare();
		Square s2 = new BasicSquare();
		Square[][] grid = new Square[][]{{s1}, {s2}};
		factory.createBoard(grid);
		assertEquals(s2, s1.getSquareAt(Direction.EAST));
		assertEquals(s1, s2.getSquareAt(Direction.EAST));
	}
	
	/**
	 * Verifies a chain of cells is connected to the west.
	 */
	@Test
	public void connectedWest() {
		Square s1 = new BasicSquare();
		Square s2 = new BasicSquare();
		Square[][] grid = new Square[][]{{s1}, {s2}};
		factory.createBoard(grid);
		assertEquals(s2, s1.getSquareAt(Direction.WEST));
		assertEquals(s1, s2.getSquareAt(Direction.WEST));
	}
	
	/**
	 * Verifies a chain of cells is connected to the north.
	 */
	@Test
	public void connectedNorth() {
		Square s1 = new BasicSquare();
		Square s2 = new BasicSquare();
		Square[][] grid = new Square[][]{{s1, s2}};
		factory.createBoard(grid);
		assertEquals(s2, s1.getSquareAt(Direction.NORTH));
		assertEquals(s1, s2.getSquareAt(Direction.NORTH));
	}
	
	/**
	 * Verifies a chain of cells is connected to the south.
	 */
	@Test
	public void connectedSouth() {
		Square s1 = new BasicSquare();
		Square s2 = new BasicSquare();
		Square[][] grid = new Square[][]{{s1, s2}};
		factory.createBoard(grid);
		assertEquals(s2, s1.getSquareAt(Direction.SOUTH));
		assertEquals(s1, s2.getSquareAt(Direction.SOUTH));
	}

    /**
     * Verifies that the
     * @throws Exception
     */
	@Test
	public void testCreateBoard() throws Exception {
		Square s1 = new BasicSquare();
		Square s2 = new BasicSquare();
		Square s3 = new BasicSquare();
		Square s4 = new BasicSquare();
		Square s5 = new BasicSquare();
		Square s6 = new BasicSquare();
		Square[][] grid = new Square[][]{{s1, s2}, {s3, s4}, {s5, s6}};
		Board board = factory.createBoard(grid);
        assertEquals(s1, board.squareAt(0, 0));
        assertEquals(s2, board.squareAt(0, 1));
        assertEquals(s3, board.squareAt(1, 0));
        assertEquals(s4, board.squareAt(1, 1));
        assertEquals(s5, board.squareAt(2, 0));
        assertEquals(s6, board.squareAt(2, 1));
	}

	@Test
	public void testCreateInfiniteBoard() throws Exception {
		Square s1 = new BasicSquare();
		Square s2 = new BasicSquare();
		Square s3 = new BasicSquare();
		Square s4 = new BasicSquare();
		Square s5 = new BasicSquare();
		Square s6 = new BasicSquare();
		Square[][] grid = new Square[][]{{s1, s2}, {s3, s4}, {s5, s6}};
		InfiniteBoard board = factory.createInfiniteBoard(grid);
		assertEquals(s1, board.squareAt(0, 0));
		assertEquals(s2, board.squareAt(0, 1));
		assertEquals(s3, board.squareAt(1, 0));
		assertEquals(s4, board.squareAt(1, 1));
		assertEquals(s5, board.squareAt(2, 0));
		assertEquals(s6, board.squareAt(2, 1));
	}

	@Test
	public void testCreateGround() throws Exception {
		Square ground = factory.createGround();
		assertTrue(ground.isAccessibleTo(new BasicUnit()));
	}

	@Test
	public void testCreateWall() throws Exception {
		Square ground = factory.createWall();
		assertFalse(ground.isAccessibleTo(new BasicUnit()));
	}
}
