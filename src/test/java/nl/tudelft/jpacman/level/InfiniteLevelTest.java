package nl.tudelft.jpacman.level;

import com.google.common.collect.Lists;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.InfiniteBoard;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
     * Tests if the board returned is an infinite one
     */
    @Test
    public void testGetInfiniteBoard() throws Exception {
            Board board = level.getBoard();
            assertEquals(this.board, board);
    }

    /**
     * Verifies that a ghost can be inserted and removed
     */
    @Test
    public void testPutNewGhost() throws Exception {
        level = new InfiniteLevel(board, Lists.newArrayList(ghost), Lists.newArrayList(square1, square2), collisions);
        NPC newGhost = new GhostFactory(new PacManSprites()).createBlinky();
        level.putNewGhost(newGhost);
        assertEquals(newGhost, level.removeGhost(newGhost));
    }

    /**
     * Verifies that the "remainingPellets" method always returns a number > 0 (because the board is infinite)
     */
    @Test
    public void testRemainingPellets() throws Exception {
        level = new InfiniteLevel(board, Lists.newArrayList(ghost), Lists.newArrayList(square1, square2), collisions);
        assertTrue(level.remainingPellets() > 0);
    }
}