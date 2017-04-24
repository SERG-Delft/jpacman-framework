package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.sprite.PacManSprites;

/**
 * Factory that creates Players.
 *
 * @author Jeroen Roosen 
 */
public class PlayerFactory {

    /**
     * The sprite store containing the Pac-Man sprites.
     */
    private final PacManSprites sprites;

    /**
     * Creates a new player factory.
     *
     * @param spriteStore
     *            The sprite store containing the Pac-Man sprites.
     */
    public PlayerFactory(PacManSprites spriteStore) {
        this.sprites = spriteStore;
    }

    /**
     * Creates a new player with the classic Pac-Man sprites.
     *
     * @return A new player.
     */
    public Player createPacMan() {
        return new Player(getSprites().getPacmanSprites(), getSprites().getPacManDeathAnimation());
    }

    /**
     * The sprites created by the factory.
     *
     * @return The sprites for the player created.
     */
    protected PacManSprites getSprites() {
        return sprites;
    }
}
