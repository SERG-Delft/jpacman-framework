package nl.tudelft.jpacman.board;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.Mockito.mock;

/**
 * Test various aspects of board.
 *
 * @author Jeroen Roosen 
 */
public class BoardTest {

    private static final int MAX_WIDTH = 2;
    private static final int MAX_HEIGHT = 3;

    private final Square[][] grid = {
            { mock(Square.class), mock(Square.class), mock(Square.class) },
            { mock(Square.class), mock(Square.class), mock(Square.class) },
    };
    private final Board board = new Board(grid);

    /**
     * Verifies the board has the correct width.
     */
    @Test
    public void verifyWidth() {
        assertThat(board.getWidth()).isEqualTo(MAX_WIDTH);
    }

    /**
     * Verifies the board has the correct height.
     */
    @Test
    public void verifyHeight() {
        assertThat(board.getHeight()).isEqualTo(MAX_HEIGHT);
    }

    /**
     * Test at various places that the board returns the correct square.
     * @return Tests for the various places.
     */
    @TestFactory
    public Iterable<DynamicTest> verifySquares() {
        return Arrays.asList(
                testSquareAt(0, 0),
                testSquareAt(1, 2),
                testSquareAt(0, 1)
        );
    }

    /**
     * Create a test that board returns correct square.
     * @param x x-coordinate
     * @param y y-coordinate
     * @return Test checking that (x,y) yields correct square.
     */
    private DynamicTest testSquareAt(int x, int y) {
        return dynamicTest(
                x + "," + y,
                () -> assertThat(board.squareAt(x, y)).isEqualTo(grid[x][y])
        );
    }
}
