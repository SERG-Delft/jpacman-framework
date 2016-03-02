package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Angeall on 29/02/2016.
 */
public class InfiniteBoardTest {
    InfiniteBoard board;
    BoardFactory boardFactory = new BoardFactory(new PacManSprites());;
    Square s0_0 = new BasicSquare();
    Square s0_1 = new BasicSquare();
    Square s0_2 = new BasicSquare();
    Square s1_0 = new BasicSquare();
    Square s1_1 = new BasicSquare();
    Square s1_2 = new BasicSquare();
    Square s2_0 = new BasicSquare();
    Square s2_1 = new BasicSquare();
    Square s2_2 = new BasicSquare();
    Square[][] grid = new Square[][]{{s0_0, s0_1, s0_2}, {s1_0, s1_1, s1_2}, {s2_0, s2_1, s2_2}};

    /**
     * The board is cleared before each test
     */
    @Before
    public void setUp(){
        board = boardFactory.createInfiniteBoard(grid);
    }

    /**
     * Test if some squares are indeed in the board
     */
    @Test
    public void testSquareAt() throws Exception {
        assertEquals(s1_1, board.squareAtUnchecked(1, 1));
        assertEquals(s0_1, board.squareAtUnchecked(0, 1));
        assertEquals(s1_2, board.squareAtUnchecked(1, 2));
    }

    /**
     * Test if some squares are indeed inside the borders of the board
     */
    @Test
    public void testWithinBorders() throws Exception {
        assertTrue(board.withinBorders(1, 2));
        assertTrue(board.withinBorders(2, 1));
        assertTrue(board.withinBorders(0, 0));
        assertTrue(board.withinBorders(2, 2));
        assertFalse(board.withinBorders(3, 2));
        assertFalse(board.withinBorders(0, 3));
    }

    /**
     * Test if the squares are well linked to one another
     */
    @Test
    public void testHorizontalLinks() throws Exception {
        assertEquals(s1_1, s0_1.getSquareAt(Direction.EAST));
        assertEquals(s1_1, s2_1.getSquareAt(Direction.WEST));
        assertEquals(s2_2, s1_2.getSquareAt(Direction.EAST));
        assertEquals(s0_0, s1_0.getSquareAt(Direction.WEST));
        assertEquals(null, s2_1.getSquareAt(Direction.EAST));
        assertEquals(null, s0_0.getSquareAt(Direction.WEST));
    }

    /**
     * Test if newly added squares are indeed in the board
     */
    @Test
    public void testAddColumnRightSquareAt() throws Exception {
        Square s3_0 = new BasicSquare();
        Square s3_1 = new BasicSquare();
        Square s3_2 = new BasicSquare();
        Square[] column = new Square[]{s3_0, s3_1, s3_2};
        board.addColumnRight(column);
        assertEquals(s3_0, board.squareAtUnchecked(3, 0));
        assertEquals(s3_1, board.squareAtUnchecked(3, 1));
        assertEquals(s3_2, board.squareAtUnchecked(3, 2));
    }

    /**
     * Test if newly added squares are well linked horizontally with the others in the board
     */
    @Test
    public void testAddColumnRightCheckHorizontalLinks() throws Exception {
        Square s3_0 = new BasicSquare();
        Square s3_1 = new BasicSquare();
        Square s3_2 = new BasicSquare();
        Square[] column = new Square[]{s3_0, s3_1, s3_2};
        board.addColumnRight(column);
        assertEquals(board.squareAtUnchecked(2, 0).getSquareAt(Direction.EAST), board.squareAtUnchecked(3, 0));
        assertEquals(board.squareAtUnchecked(2, 1).getSquareAt(Direction.EAST), board.squareAtUnchecked(3, 1));
        assertEquals(board.squareAtUnchecked(2, 2).getSquareAt(Direction.EAST), board.squareAtUnchecked(3, 2));
        assertEquals(board.squareAtUnchecked(3, 0).getSquareAt(Direction.WEST), board.squareAtUnchecked(2, 0));
        assertEquals(board.squareAtUnchecked(3, 1).getSquareAt(Direction.WEST), board.squareAtUnchecked(2, 1));
        assertEquals(board.squareAtUnchecked(3, 2).getSquareAt(Direction.WEST), board.squareAtUnchecked(2, 2));
    }

    /**
     * Test if newly added squares are well linked vertically with the others in the board
     */
    @Test
    public void testAddColumnRightCheckVerticalLinks() throws Exception {
        Square s3_0 = new BasicSquare();
        Square s3_1 = new BasicSquare();
        Square s3_2 = new BasicSquare();
        Square[] column = new Square[]{s3_0, s3_1, s3_2};
        board.addColumnRight(column);
        assertEquals(board.squareAtUnchecked(3, 1), board.squareAtUnchecked(3, 0).getSquareAt(Direction.SOUTH));
        assertEquals(board.squareAtUnchecked(3, 2), board.squareAtUnchecked(3, 1).getSquareAt(Direction.SOUTH));
        assertEquals(null, board.squareAtUnchecked(3, 2).getSquareAt(Direction.SOUTH));
        assertEquals(null, board.squareAtUnchecked(3, 0).getSquareAt(Direction.NORTH));
        assertEquals(board.squareAtUnchecked(3, 0), board.squareAtUnchecked(3, 1).getSquareAt(Direction.NORTH));
        assertEquals( board.squareAtUnchecked(3, 1), board.squareAtUnchecked(3, 2).getSquareAt(Direction.NORTH));
    }

    /**
    * Test if newly added squares are indeed in the board
    */
    @Test
    public void testAddColumnLeftSquareAt() throws Exception {
        Square s00_0 = new BasicSquare();
        Square s00_1 = new BasicSquare();
        Square s00_2 = new BasicSquare();
        Square[] column = new Square[]{s00_0, s00_1, s00_2};
        board.addColumnLeft(column);
        assertEquals(s00_0, board.squareAtUnchecked(-1, 0));
        assertEquals(s00_1, board.squareAtUnchecked(-1, 1));
        assertEquals(s00_2, board.squareAtUnchecked(-1, 2));
    }

    /**
     * Test if newly added squares are well linked horizontally with the others in the board
     */
    @Test
    public void testAddColumnLeftCheckHorizontalLinks() throws Exception {
        Square s00_0 = new BasicSquare();
        Square s00_1 = new BasicSquare();
        Square s00_2 = new BasicSquare();
        Square[] column = new Square[]{s00_0, s00_1, s00_2};
        board.addColumnLeft(column);
        assertEquals(board.squareAtUnchecked(-1, 0), board.squareAtUnchecked(0, 0).getSquareAt(Direction.WEST));
        assertEquals(board.squareAtUnchecked(-1, 1), board.squareAtUnchecked(0, 1).getSquareAt(Direction.WEST));
        assertEquals(board.squareAtUnchecked(-1, 2), board.squareAtUnchecked(0, 2).getSquareAt(Direction.WEST));
        assertEquals(board.squareAtUnchecked(-1, 0).getSquareAt(Direction.EAST), board.squareAtUnchecked(0, 0));
        assertEquals(board.squareAtUnchecked(-1, 1).getSquareAt(Direction.EAST), board.squareAtUnchecked(0, 1));
        assertEquals(board.squareAtUnchecked(-1, 2).getSquareAt(Direction.EAST), board.squareAtUnchecked(0, 2));
    }

    /**
     * Test if newly added squares are well linked vertically with the others in the board
     */
    @Test
    public void testAddColumnLeftCheckVerticalLinks() throws Exception {
        Square s00_0 = new BasicSquare();
        Square s00_1 = new BasicSquare();
        Square s00_2 = new BasicSquare();
        Square[] column = new Square[]{s00_0, s00_1, s00_2};
        board.addColumnLeft(column);
        assertEquals(null, board.squareAtUnchecked(-1, 0).getSquareAt(Direction.NORTH));
        assertEquals(board.squareAtUnchecked(-1, 0), board.squareAtUnchecked(-1, 1).getSquareAt(Direction.NORTH));
        assertEquals(board.squareAtUnchecked(-1, 1), board.squareAtUnchecked(-1, 2).getSquareAt(Direction.NORTH));
        assertEquals(null, board.squareAtUnchecked(-1, 2).getSquareAt(Direction.SOUTH));
        assertEquals(board.squareAtUnchecked(-1, 2), board.squareAtUnchecked(-1, 1).getSquareAt(Direction.SOUTH));
        assertEquals(board.squareAtUnchecked(-1, 1), board.squareAtUnchecked(-1, 0).getSquareAt(Direction.SOUTH));
    }

    /**
     * Test if newly added squares are indeed in the board
     */
    @Test
    public void testAddLineUpSquareAt() throws Exception {
        Square s0_00 = new BasicSquare();
        Square s1_00 = new BasicSquare();
        Square s2_00 = new BasicSquare();
        Square[] line = new Square[]{s0_00, s1_00, s2_00};
        board.addLineTop(line);
        assertEquals(s0_00, board.squareAtUnchecked(0, -1));
        assertEquals(s1_00, board.squareAtUnchecked(1, -1));
        assertEquals(s2_00, board.squareAtUnchecked(2, -1));
    }

    /**
     * Test if newly added squares are well linked horizontally with the others in the board
     */
    @Test
    public void testAddLineUpCheckHorizontalLinks() throws Exception {
        Square s0_00 = new BasicSquare();
        Square s1_00 = new BasicSquare();
        Square s2_00 = new BasicSquare();
        Square[] line = new Square[]{s0_00, s1_00, s2_00};
        board.addLineTop(line);
        assertEquals(null, board.squareAtUnchecked(0, -1).getSquareAt(Direction.WEST));
        assertEquals(null, board.squareAtUnchecked(2, -1).getSquareAt(Direction.EAST));
        assertEquals(board.squareAtUnchecked(1, -1), board.squareAtUnchecked(0, -1).getSquareAt(Direction.EAST));
        assertEquals(board.squareAtUnchecked(2, -1), board.squareAtUnchecked(1, -1).getSquareAt(Direction.EAST));
        assertEquals(board.squareAtUnchecked(0, -1), board.squareAtUnchecked(1, -1).getSquareAt(Direction.WEST));
        assertEquals(board.squareAtUnchecked(1, -1), board.squareAtUnchecked(2, -1).getSquareAt(Direction.WEST));
    }

    /**
     * Test if newly added squares are well linked vertically with the others in the board
     */
    @Test
    public void testAddLineUpCheckVerticalLinks() throws Exception {
        Square s0_00 = new BasicSquare();
        Square s1_00 = new BasicSquare();
        Square s2_00 = new BasicSquare();
        Square[] line = new Square[]{s0_00, s1_00, s2_00};
        board.addLineTop(line);
        assertEquals(null, board.squareAtUnchecked(0, -1).getSquareAt(Direction.NORTH));
        assertEquals(null, board.squareAtUnchecked(1, -1).getSquareAt(Direction.NORTH));
        assertEquals(null, board.squareAtUnchecked(2, -1).getSquareAt(Direction.NORTH));
        assertEquals(board.squareAtUnchecked(0, -1), board.squareAtUnchecked(0, 0).getSquareAt(Direction.NORTH));
        assertEquals(board.squareAtUnchecked(1, -1), board.squareAtUnchecked(1, 0).getSquareAt(Direction.NORTH));
        assertEquals(board.squareAtUnchecked(2, -1), board.squareAtUnchecked(2, 0).getSquareAt(Direction.NORTH));
        assertEquals(board.squareAtUnchecked(0, 0), board.squareAtUnchecked(0, -1).getSquareAt(Direction.SOUTH));
        assertEquals(board.squareAtUnchecked(1, 0), board.squareAtUnchecked(1, -1).getSquareAt(Direction.SOUTH));
        assertEquals(board.squareAtUnchecked(2, 0), board.squareAtUnchecked(2, -1).getSquareAt(Direction.SOUTH));
    }

    /**
     * Test if newly added squares are indeed in the board
     */
    @Test
    public void testAddLineDownSquareAt() throws Exception {
        Square s0_3 = new BasicSquare();
        Square s1_3 = new BasicSquare();
        Square s2_3 = new BasicSquare();
        Square[] line = new Square[]{s0_3, s1_3, s2_3};
        board.addLineBottom(line);
        assertEquals(s0_3, board.squareAtUnchecked(0, 3));
        assertEquals(s1_3, board.squareAtUnchecked(1, 3));
        assertEquals(s2_3, board.squareAtUnchecked(2, 3));
    }

    /**
     * Test if newly added squares are well linked horizontally with the others in the board
     */
    @Test
    public void testAddLineDownCheckHorizontalLinks() throws Exception {
        Square s0_3 = new BasicSquare();
        Square s1_3 = new BasicSquare();
        Square s2_3 = new BasicSquare();
        Square[] line = new Square[]{s0_3, s1_3, s2_3};
        board.addLineBottom(line);
        assertEquals(null, board.squareAtUnchecked(0, 3).getSquareAt(Direction.WEST));
        assertEquals(null, board.squareAtUnchecked(2, 3).getSquareAt(Direction.EAST));
        assertEquals(board.squareAtUnchecked(1, 3), board.squareAtUnchecked(0, 3).getSquareAt(Direction.EAST));
        assertEquals(board.squareAtUnchecked(2, 3), board.squareAtUnchecked(1, 3).getSquareAt(Direction.EAST));
        assertEquals(board.squareAtUnchecked(0, 3), board.squareAtUnchecked(1, 3).getSquareAt(Direction.WEST));
        assertEquals(board.squareAtUnchecked(1, 3), board.squareAtUnchecked(2, 3).getSquareAt(Direction.WEST));
    }

    /**
     * Test if newly added squares are well linked vertically with the others in the board
     */
    @Test
    public void testAddLineDownCheckVerticalLinks() throws Exception {
        Square s0_3 = new BasicSquare();
        Square s1_3 = new BasicSquare();
        Square s2_3 = new BasicSquare();
        Square[] line = new Square[]{s0_3, s1_3, s2_3};
        board.addLineBottom(line);
        assertEquals(null, board.squareAtUnchecked(0, 3).getSquareAt(Direction.SOUTH));
        assertEquals(null, board.squareAtUnchecked(1, 3).getSquareAt(Direction.SOUTH));
        assertEquals(null, board.squareAtUnchecked(2, 3).getSquareAt(Direction.SOUTH));
        assertEquals(board.squareAtUnchecked(0, 3), board.squareAtUnchecked(0, 2).getSquareAt(Direction.SOUTH));
        assertEquals(board.squareAtUnchecked(1, 3), board.squareAtUnchecked(1, 2).getSquareAt(Direction.SOUTH));
        assertEquals(board.squareAtUnchecked(2, 3), board.squareAtUnchecked(2, 2).getSquareAt(Direction.SOUTH));
        assertEquals(board.squareAtUnchecked(0, 2), board.squareAtUnchecked(0, 3).getSquareAt(Direction.NORTH));
        assertEquals(board.squareAtUnchecked(1, 2), board.squareAtUnchecked(1, 3).getSquareAt(Direction.NORTH));
        assertEquals(board.squareAtUnchecked(2, 2), board.squareAtUnchecked(2, 3).getSquareAt(Direction.NORTH));
    }


    /**
     * Test if adding multiple lines and columns goes well and that the newly added squares are in the board
     */
    @Test
    public void testAddMultipleColumnsAndLines() throws Exception {
        assertEquals(s0_0, board.squareAt(0, 0));
        Square s0_3 = new BasicSquare();
        Square s1_3 = new BasicSquare();
        Square s2_3 = new BasicSquare();
        Square[] lineDown = new Square[]{s0_3, s1_3, s2_3};
        board.addLineBottom(lineDown);

        assertEquals(s0_0, board.squareAt(0, 0));
        assertEquals(s2_2, board.squareAt(2, 2));

        Square s0_00 = new BasicSquare();
        Square s1_00 = new BasicSquare();
        Square s2_00 = new BasicSquare();
        Square[] lineUp = new Square[]{s0_00, s1_00, s2_00};
        board.addLineTop(lineUp);

        assertEquals(s0_0, board.squareAt(0, 0));
        assertEquals(s2_2, board.squareAt(2, 2));

        Square s0_000 = new BasicSquare();
        Square s1_000 = new BasicSquare();
        Square s2_000 = new BasicSquare();
        Square[] lineUp2 = new Square[]{s0_000, s1_000, s2_000};
        board.addLineTop(lineUp2);

        assertEquals(s0_0, board.squareAt(0, 0));
        assertEquals(s2_2, board.squareAt(2, 2));

        Square s00_0 = new BasicSquare();
        Square s00_1 = new BasicSquare();
        Square s00_2 = new BasicSquare();
        Square s00_3 = new BasicSquare();
        Square s00_4 = new BasicSquare();
        Square s00_5 = new BasicSquare();
        Square[] columnLeft = new Square[]{s00_0, s00_1, s00_2, s00_3, s00_4, s00_5};
        board.addColumnLeft(columnLeft);

        assertEquals(s0_0, board.squareAt(0, 0));
        assertEquals(s2_2, board.squareAt(2, 2));

        Square s000_0 = new BasicSquare();
        Square s000_1 = new BasicSquare();
        Square s000_2 = new BasicSquare();
        Square s000_3 = new BasicSquare();
        Square s000_4 = new BasicSquare();
        Square s000_5 = new BasicSquare();
        Square[] columnLeft2 = new Square[]{s000_0, s000_1, s000_2, s000_3, s000_4, s000_5};
        board.addColumnLeft(columnLeft2);

        assertEquals(s0_0, board.squareAt(0, 0));
        assertEquals(s2_2, board.squareAt(2, 2));

        Square s3_0 = new BasicSquare();
        Square s3_1 = new BasicSquare();
        Square s3_2 = new BasicSquare();
        Square s3_3 = new BasicSquare();
        Square s3_4 = new BasicSquare();
        Square s3_5 = new BasicSquare();
        Square[] columnRight = new Square[]{s3_0, s3_1, s3_2, s3_3, s3_4, s3_5};
        board.addColumnRight(columnRight);
        assertEquals(s0_000, board.squareAtUnchecked(0, -1).getSquareAt(Direction.NORTH));
        try{
            board.squareAt(2, 1); // check that the squareAt throws an AssertionError outside the window
            fail();
        } catch(AssertionError exc) {
            assertTrue(true);
        }
        assertEquals(s0_0, board.squareAt(0, 0));
        assertEquals(s2_2, board.squareAt(2, 2));
    }

    /**
     * Test if moving the visible space  affects the indices of the windows
     */
    @Test
    public void testAddMultipleColumnsAndLinesWithSlides() throws Exception {
        assertEquals(s0_0, board.squareAt(0, 0));
        Square s0_3 = new BasicSquare();
        Square s1_3 = new BasicSquare();
        Square s2_3 = new BasicSquare();
        Square[] lineDown = new Square[]{s0_3, s1_3, s2_3};
        board.addLineBottom(lineDown);
        board.moveDownVisible();
        board.moveUpVisible();

        assertEquals(s0_0, board.squareAt(0, 0));
        assertEquals(s2_2, board.squareAt(2, 2));

        Square s0_00 = new BasicSquare();
        Square s1_00 = new BasicSquare();
        Square s2_00 = new BasicSquare();
        Square[] lineUp = new Square[]{s0_00, s1_00, s2_00};
        board.addLineTop(lineUp);
        board.moveUpVisible();

        assertEquals(s0_00, board.squareAt(0, 0));
        assertEquals(s2_1, board.squareAt(2, 2));

        Square s0_000 = new BasicSquare();
        Square s1_000 = new BasicSquare();
        Square s2_000 = new BasicSquare();
        Square[] lineUp2 = new Square[]{s0_000, s1_000, s2_000};
        board.addLineTop(lineUp2);
        board.moveUpVisible();

        assertEquals(s0_000, board.squareAt(0, 0));
        assertEquals(s2_0, board.squareAt(2, 2));

        Square s00_0 = new BasicSquare();
        Square s00_1 = new BasicSquare();
        Square s00_2 = new BasicSquare();
        Square s00_3 = new BasicSquare();
        Square s00_4 = new BasicSquare();
        Square s00_5 = new BasicSquare();
        Square[] columnLeft = new Square[]{s00_0, s00_1, s00_2, s00_3, s00_4, s00_5};
        board.addColumnLeft(columnLeft);
        board.moveLeftVisible();

        assertEquals(s00_0, board.squareAt(0, 0));
        assertEquals(s1_0, board.squareAt(2, 2));

        Square s000_0 = new BasicSquare();
        Square s000_1 = new BasicSquare();
        Square s000_2 = new BasicSquare();
        Square s000_3 = new BasicSquare();
        Square s000_4 = new BasicSquare();
        Square s000_5 = new BasicSquare();
        Square[] columnLeft2 = new Square[]{s000_0, s000_1, s000_2, s000_3, s000_4, s000_5};
        board.addColumnLeft(columnLeft2);
        board.moveLeftVisible();

        assertEquals(s000_0, board.squareAt(0, 0));
        assertEquals(s0_0, board.squareAt(2, 2));

        Square s3_0 = new BasicSquare();
        Square s3_1 = new BasicSquare();
        Square s3_2 = new BasicSquare();
        Square s3_3 = new BasicSquare();
        Square s3_4 = new BasicSquare();
        Square s3_5 = new BasicSquare();
        Square[] columnRight = new Square[]{s3_0, s3_1, s3_2, s3_3, s3_4, s3_5};
        board.addColumnRight(columnRight);
        board.moveRightVisible();

        assertEquals(s00_0, board.squareAt(0, 0));
        assertEquals(s1_0, board.squareAt(2, 2));
    }

    /**
     * Verifies that the 3*3 visible space has always a width of 3
     */
    @Test
    public void testGetWidth() throws Exception {
        Square s3_0 = new BasicSquare();
        Square s3_1 = new BasicSquare();
        Square s3_2 = new BasicSquare();
        Square[] column = new Square[]{s3_0, s3_1, s3_2};
        board.addColumnRight(column);
        Square s00_0 = new BasicSquare();
        Square s00_1 = new BasicSquare();
        Square s00_2 = new BasicSquare();
        column = new Square[]{s00_0, s00_1, s00_2};
        board.addColumnLeft(column);
        assertEquals(3, board.getWidth());
    }

    /**
     * Verifies that the 3*3 visible space has always a height of 3
     */
    @Test
    public void testGetHeight() throws Exception {
        Square s0_3 = new BasicSquare();
        Square s1_3 = new BasicSquare();
        Square s2_3 = new BasicSquare();
        Square[] line = new Square[]{s0_3, s1_3, s2_3};
        board.addLineBottom(line);
        Square s0_00 = new BasicSquare();
        Square s1_00 = new BasicSquare();
        Square s2_00 = new BasicSquare();
        line = new Square[]{s0_00, s1_00, s2_00};
        board.addLineTop(line);
        assertEquals(3, board.getHeight());
    }

    /**
     * Verifies that the left column is returned
     */
    @Test
    public void testGetLeftColumn() throws Exception {
        Square s00_0 = new BasicSquare();
        Square s00_1 = new BasicSquare();
        Square s00_2 = new BasicSquare();
        Square[] column = new Square[]{s00_0, s00_1, s00_2};
        board.addColumnLeft(column);
        assertArrayEquals(column, board.getLeftColumn().toArray());
    }

    @Test
    public void testGetRightColumn() throws Exception {
        Square s3_0 = new BasicSquare();
        Square s3_1 = new BasicSquare();
        Square s3_2 = new BasicSquare();
        Square[] column = new Square[]{s3_0, s3_1, s3_2};
        board.addColumnRight(column);
        assertArrayEquals(column, board.getRightColumn().toArray());
    }

    @Test
    public void testGetBottomLine() throws Exception {
        Square s0_3 = new BasicSquare();
        Square s1_3 = new BasicSquare();
        Square s2_3 = new BasicSquare();
        Square[] line = new Square[]{s0_3, s1_3, s2_3};
        board.addLineBottom(line);
        assertArrayEquals(line, board.getBottomLine().toArray());
    }

    @Test
    public void testGetTopLine() throws Exception {
        Square s0_00 = new BasicSquare();
        Square s1_00 = new BasicSquare();
        Square s2_00 = new BasicSquare();
        Square[] line = new Square[]{s0_00, s1_00, s2_00};
        board.addLineTop(line);
        assertArrayEquals(line, board.getTopLine().toArray());
    }
}