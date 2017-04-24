package nl.tudelft.jpacman.npc;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Unit;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A non-player unit.
 *
 * @author Jeroen Roosen 
 */
public abstract class NPC extends Unit {

    /**
     * The time that should be taken between moves.
     *
     * @return The suggested delay between moves in milliseconds.
     */
    public abstract long getInterval();

    /**
     * Calculates the next move for this unit and returns the direction to move
     * in.
     *
     * Precondition: The NPC occupies a square (hasSquare() holds).
     *
     * @return The direction to move in, or <code>null</code> if no move could
     *         be devised.
     */
    public abstract @Nullable Direction nextMove();

}
