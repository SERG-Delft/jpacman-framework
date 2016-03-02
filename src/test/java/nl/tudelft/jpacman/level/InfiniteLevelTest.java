package nl.tudelft.jpacman.level;

import com.google.common.collect.Lists;
import nl.tudelft.jpacman.board.*;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by Angeall on 02/03/2016.
 */
public class InfiniteLevelTest {
    /**
     * The infinite level used for the tests
     */
    private InfiniteLevel level;
    /**
     * The infinite board inside the infinite level
     */
    private InfiniteBoard board;
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
     * The collision map.
     */
    private final CollisionMap collisions = mock(CollisionMap.class);

    /**
     * A square of the board
     */
    private BasicSquare s0_0 = new BasicSquare();
    /**
     * A square of the board
     */
    private BasicSquare s0_1 = new BasicSquare();
    /**
     * A square of the board
     */
    private BasicSquare s0_2 = new BasicSquare();
    /**
     * A square of the board
     */
    private BasicSquare s1_0 = new BasicSquare();
    /**
     * A square of the board
     */
    private BasicSquare s1_1 = new BasicSquare();
    /**
     * A square of the board
     */
    private BasicSquare s1_2 = new BasicSquare();

    /**
     * Clears the board and the level before each test
     */
    @Before
    public void setUp(){
        Square[][] grid = new Square[][]{{s0_0, s0_1, s0_2}, {s1_0, s1_1, s1_2}};
        board = new BoardFactory(new PacManSprites()).createInfiniteBoard(grid);
        level = new InfiniteLevel(board, Lists.newArrayList(ghost), Lists.newArrayList(square1, square2), collisions);
    }

    /**
     * Tests if the boars extends itself as it should
     */
    @Test
    public void testMove() throws Exception {
        BasicUnit unit = new BasicUnit();
        unit.occupy(s0_0);
        assertEquals(s0_0, board.squareAt(0, 0)); // This call to squareAt should force the level to extend the board
        assertTrue(board.getLeftColumn().contains(s0_2)); // The bottom line should contain the s1_2
        level.move(unit, Direction.EAST);
        assertFalse(board.getLeftColumn().contains(s0_2)); // The bottom line should be a new one
    }

    /**
     * Tests if the board returned is an infinite one
     */
    @Test
    public void testGetInfiniteBoard() throws Exception {
            Board board = level.getBoard();
            assertEquals(this.board, board);
    }
}