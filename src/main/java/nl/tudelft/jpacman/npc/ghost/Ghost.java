package nl.tudelft.jpacman.npc.ghost;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * An antagonist in the game of Pac-Man, a ghost.
 *
 * @author Jeroen Roosen 
 */
public abstract class Ghost extends NPC {

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
     * Creates a new ghost.
     *
     * @param spriteMap
     *            The sprites for every direction.
     * @param moveInterval
     *            The base interval of movement.
     * @param intervalVariation
     *            The variation of the interval.
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

    @Override
    public long getInterval() {
        return this.moveInterval + new Random().nextInt(this.intervalVariation);
    }
}
