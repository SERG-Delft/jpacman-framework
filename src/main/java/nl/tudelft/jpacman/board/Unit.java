package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.sprite.Sprite;

/**
 * A unit that can be placed on a {@link Square}.
 *
 * @author Jeroen Roosen 
 */
public abstract class Unit {

    /**
     * The square this unit is currently occupying.
     */
    private Square square;

    /**
     * The direction this unit is facing.
     */
    private Direction direction;

    /**
     * Creates a unit that is facing east.
     */
    protected Unit() {
        this.direction = Direction.EAST;
    }

    /**
     * Sets this unit to face the new direction.
     * @param newDirection The new direction this unit is facing.
     */
    public void setDirection(Direction newDirection) {
        this.direction = newDirection;
    }

    /**
     * Returns the current direction this unit is facing.
     * @return The current direction this unit is facing.
     */
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * Returns the square this unit is currently occupying.
     * Precondition: <code>hasSquare()</code>.
     *
     * @return The square this unit is currently occupying.
     */
    public Square getSquare() {
        assert invariant();
        assert square != null;
        return square;
    }

    /**
     * Returns whether this unit is currently on  a square.
     *
     * @return True iff the unit is occupying a square at the moment.
     */
    public boolean hasSquare() {
        return square != null;
    }

    /**
     * Occupies the target square iff this unit is allowed to as decided by
     * {@link Square#isAccessibleTo(Unit)}.
     *
     * @param target
     *            The square to occupy.
     */
    public void occupy(Square target) {
        assert target != null;

        if (square != null) {
            square.remove(this);
        }
        square = target;
        target.put(this);
        assert invariant();
    }

    /**
     * Leaves the currently occupying square, thus removing this unit from the board.
     */
    public void leaveSquare() {
        if (square != null) {
            square.remove(this);
            square = null;
        }
        assert invariant();
    }

    /**
     * Tests whether the square this unit is occupying has this unit listed as
     * one of its occupiers.
     *
     * @return <code>true</code> if the square this unit is occupying has this
     *         unit listed as one of its occupiers, or if this unit is currently
     *         not occupying any square.
     */
    protected boolean invariant() {
        return square == null || square.getOccupants().contains(this);
    }

    /**
     * Returns the sprite of this unit.
     *
     * @return The sprite of this unit.
     */
    public abstract Sprite getSprite();

    /**
     * A utility method for implementing the ghost AI.
     *
     * @param amountToLookAhead the amount of squares to follow this units direction in.
     * @return The square amountToLookAhead spaces in front of this unit.
     */
    public Square squaresAheadOf(int amountToLookAhead) {
        Direction targetDirection = this.getDirection();
        Square destination = this.getSquare();
        for (int i = 0; i < amountToLookAhead; i++) {
            destination = destination.getSquareAt(targetDirection);
        }

        return destination;
    }
}
