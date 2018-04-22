package nl.tudelft.jpacman.npc;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.sprite.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 * A non-player unit.
 *
 * @author Jeroen Roosen
 */
public abstract class Ghost extends Unit {
    /**
     * The sprite map, one sprite for each direction.
     */
    private final Map<Direction, Sprite> sprites;

    /**
     * The base move interval of the ghost.
     */
    private final int moveInterval;

    /**
     * The random variation added to the {@link #moveInterval}.
     */
    private final int intervalVariation;

    /**
     * Calculates the next move for this unit and returns the direction to move
     * in.
     * <p>
     * Precondition: The NPC occupies a square (hasSquare() holds).
     *
     * @return The direction to move in, or <code>null</code> if no move could
     * be devised.
     */
    public Direction nextMove() {
        return nextAiMove().orElseGet(this::randomMove);
    }

    /**
     * Tries to calculate a move based on the behaviour of the npc.
     *
     * @return an optional containing the move or empty if the current state of the game
     * makes the ai move impossible
     */
    public abstract Optional<Direction> nextAiMove();

    /**
     * Creates a new ghost.
     *
     * @param spriteMap         The sprites for every direction.
     * @param moveInterval      The base interval of movement.
     * @param intervalVariation The variation of the interval.
     */
    protected Ghost(Map<Direction, Sprite> spriteMap, int moveInterval, int intervalVariation) {
        this.sprites = spriteMap;
        this.intervalVariation = intervalVariation;
        this.moveInterval = moveInterval;
    }

    @Override
    public Sprite getSprite() {
        return sprites.get(getDirection());
    }

    /**
     * The time that should be taken between moves.
     *
     * @return The suggested delay between moves in milliseconds.
     */
    public long getInterval() {
        return this.moveInterval + new Random().nextInt(this.intervalVariation);
    }

    /**
     * Determines a possible move in a random direction.
     *
     * @return A direction in which the ghost can move, or <code>null</code> if
     * the ghost is shut in by inaccessible squares.
     */
    protected Direction randomMove() {
        Square square = getSquare();
        List<Direction> directions = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            if (square.getSquareAt(direction).isAccessibleTo(this)) {
                directions.add(direction);
            }
        }
        if (directions.isEmpty()) {
            return null;
        }
        int i = new Random().nextInt(directions.size());
        return directions.get(i);
    }
}
