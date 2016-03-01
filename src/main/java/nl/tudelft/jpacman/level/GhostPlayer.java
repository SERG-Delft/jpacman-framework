package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.ghost.GhostColor;
import nl.tudelft.jpacman.sprite.PacManSprites;


/**
 * Created by helldog136 on 1/03/16.
 */
public class GhostPlayer extends Player {
    /**
     * Creates a new player with a score of 0 points.
     */
    GhostPlayer(PacManSprites sprites, GhostColor color) {
        super(sprites.getGhostSprite(color), sprites.getPacManDeathAnimation());
    }
}
