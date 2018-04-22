package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.sprite.PacManSprites;

/**
 * Factory that creates ghosts.
 *
 * @author Jeroen Roosen 
 */
public class GhostFactory {

    /**
     * The sprite store containing the ghost sprites.
     */
    private final PacManSprites sprites;

    /**
     * Creates a new ghost factory.
     *
     * @param spriteStore The sprite provider.
     */
    public GhostFactory(PacManSprites spriteStore) {
        this.sprites = spriteStore;
    }

    /**
     * Creates a new Blinky / Shadow, the red Ghost.
     *
     * @see Blinky
     * @return A new Blinky.
     */
    public Ghost createBlinky() {
        return new Blinky(sprites.getGhostSprite(GhostColor.RED));
    }

    /**
     * Creates a new Pinky / Speedy, the pink Ghost.
     *
     * @see Pinky
     * @return A new Pinky.
     */
    public Ghost createPinky() {
        return new Pinky(sprites.getGhostSprite(GhostColor.PINK));
    }

    /**
     * Creates a new Inky / Bashful, the cyan Ghost.
     *
     * @see Inky
     * @return A new Inky.
     */
    public Ghost createInky() {
        return new Inky(sprites.getGhostSprite(GhostColor.CYAN));
    }

    /**
     * Creates a new Clyde / Pokey, the orange Ghost.
     *
     * @see Clyde
     * @return A new Clyde.
     */
    public Ghost createClyde() {
        return new Clyde(sprites.getGhostSprite(GhostColor.ORANGE));
    }
}
