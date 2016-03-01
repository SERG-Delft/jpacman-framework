package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.ghost.GhostColor;
import nl.tudelft.jpacman.sprite.PacManSprites;

/**
 * Created by helldog136 on 1/03/16.
 */
public class GhostPlayer extends Player {
    /**
     * Creates a new ghost player of given color with a score of 0 points.
     * 
     * @param sprites the SpriteStore to get the ghost sprites
     * @param color the color of the ghost player
     */
    GhostPlayer(PacManSprites sprites, GhostColor color) {
        super(sprites.getGhostSprite(color), sprites.getGhostDeathAnimation(color));
    }
}
