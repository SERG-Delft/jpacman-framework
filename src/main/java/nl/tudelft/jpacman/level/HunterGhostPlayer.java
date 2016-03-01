package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.npc.ghost.GhostColor;
import nl.tudelft.jpacman.sprite.AnimatedSprite;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.sprite.Sprite;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by helldog136 on 26/02/16.
 */
public class HunterGhostPlayer extends GhostPlayer {
    private static final int VALUE_START = 50;
    private static final int DECAY = 2;
    private static final long PENALTY_TIME = 5000;
    private boolean hunter = false;
    private int value = VALUE_START;
    private boolean active = true;

    /**
     * Creates a new player with a score of 0 points.
     *
     * @param sprites
     * @param color
     */
    HunterGhostPlayer(PacManSprites sprites, GhostColor color) {
        super(sprites, color);
    }


    public boolean isHunter() {
        return hunter;
    }

    public int hunted() {
        int ret = value;
        value = value / DECAY;
        active = false;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                reactive();
            }
        }, PENALTY_TIME);
        return ret;
    }

    private void reactive() {
        active = true;
    }

    public boolean isActive() {
        return active;
    }

    public void setHunter(boolean hunter) {
        if(hunter){
            
        }
        this.hunter = hunter;
    }
}
