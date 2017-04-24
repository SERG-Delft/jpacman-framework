package nl.tudelft.jpacman.board;

/**
 * An enumeration of possible directions on a two-dimensional square grid.
 *
 * @author Jeroen Roosen 
 */
public enum Direction {

    /**
     * North, or up.
     */
    NORTH(0, -1),

    /**
     * South, or down.
     */
    SOUTH(0, 1),

    /**
     * West, or left.
     */
    WEST(-1, 0),

    /**
     * East, or right.
     */
    EAST(1, 0);

    /**
     * The delta x (width difference) to an element in the direction in a grid
     * with 0,0 (x,y) as its top-left element.
     */
    private final int deltaX;

    /**
     * The delta y (height difference) to an element in the direction in a grid
     * with 0,0 (x,y) as its top-left element.
     */
    private final int deltaY;

    /**
     * Creates a new Direction with the given parameters.
     *
     * @param deltaX
     *            The delta x (width difference) to an element in the direction
     *            in a matrix with 0,0 (x,y) as its top-left element.
     * @param deltaY
     *            The delta y (height difference) to an element in the direction
     *            in a matrix with 0,0 (x,y) as its top-left element.
     */
    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    /**
     * @return The delta x (width difference) for a single step in this
     *         direction, in a matrix with 0,0 (x,y) as its top-left element.
     */
    public int getDeltaX() {
        return deltaX;
    }

    /**
     * @return The delta y (height difference) for a single step in this
     *         direction, in a matrix with 0,0 (x,y) as its top-left element.
     */
    public int getDeltaY() {
        return deltaY;
    }
}
