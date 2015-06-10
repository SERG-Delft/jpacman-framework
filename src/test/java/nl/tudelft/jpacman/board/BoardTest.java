package nl.tudelft.jpacman.board;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

/**
 * Test various aspects of board.
 * 
 * @author Jeroen Roosen 
 */
public class BoardTest {

	private Board board;
	
	private Square x0y0 = mock(Square.class);
	private Square x0y1 = mock(Square.class);
	private Square x0y2 = mock(Square.class);
	private Square x1y0 = mock(Square.class);
	private Square x1y1 = mock(Square.class);
	private Square x1y2 = mock(Square.class);
	
	private final int maxWidth = 2;
	private final int maxHeight = 3;
	
	/**
	 * Setup a board that can be used for testing.
	 */
	@Before
	public void setUp() {
		Square[][] grid = new Square[maxWidth][maxHeight];
		grid[0][0] = x0y0;
		grid[0][1] = x0y1;
		grid[0][2] = x0y2;
		grid[1][0] = x1y0;
		grid[1][1] = x1y1;
		grid[1][2] = x1y2;
		board = new Board(grid);
	}
	
	/**
	 * Verifies the board has the correct width.
	 */
	@Test
	public void verifyWidth() {
		assertEquals(maxWidth, board.getWidth());
	}
	
	/**
	 * Verifies the board has the correct height.
	 */
	@Test
	public void verifyHeight() {
		assertEquals(maxHeight, board.getHeight());
	}
	
	/**
	 * Verifies the square at x0y0 is indeed the right square.
	 */
	@Test
	public void verifyX0Y0() {
		assertEquals(x0y0, board.squareAt(0, 0));
	}
	
	/**
	 * Verifies the square at x1y2 is indeed the right square.
	 */
	@Test
	public void verifyX1Y2() {
		assertEquals(x1y2, board.squareAt(1, 2));
	}
	
	/**
	 * Verifies the square at x0y1 is indeed the right square.
	 */
	@Test
	public void verifyX0Y1() {
		assertEquals(x0y1, board.squareAt(0, 1));
	}
}
