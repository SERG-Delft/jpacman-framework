package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.game.MultiGhostPlayerGame;
import nl.tudelft.jpacman.level.HunterGameModePlayer;
import nl.tudelft.jpacman.level.HunterGhostPlayer;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.sprite.Sprite;
import nl.tudelft.jpacman.sprite.SpriteStore;

import java.util.*;

/**
 * Created by helldog136 on 2/03/16.
 */
public class HunterGhostNPC extends Ghost implements HunterGameModePlayer {
    /**
     * The variation in intervals, this makes the ghosts look more dynamic and
     * less predictable.
     */
    private static final int INTERVAL_VARIATION = 50;

    /**
     * The base movement interval.
     */
    private static final int MOVE_INTERVAL = 250;
    private static final int DECAY = 2;
    private static final int VALUE_START = 50;
    private int value = VALUE_START;
    private boolean active = true;
    
    private Map<Direction,Sprite> originalsprites;
    private int points = 0;

    public HunterGhostNPC(Map<Direction, Sprite> ghostSprite) {
        super(ghostSprite);
        System.out.println("hello");
        originalsprites = ghostSprite;
    }

    @Override
    public long getInterval() {
        return MOVE_INTERVAL + new Random().nextInt(INTERVAL_VARIATION);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The HunterNPC has two AI depending on the fact that he is a hunter or not
     * </p>
     */
    @Override
    public Direction nextMove() {
        if(isActive() && new Random().nextInt(10) != 5){
            Square target;
            if (isHunter()) { //TODO get closest between NPC and player
                //Square closestNPC = Navigation.findNearest(HunterGhostNPC.class, getSquare()).getSquare();
                Square closestPlayer = Navigation.findNearest(HunterGhostPlayer.class, getSquare()).getSquare();
                /*if(closestNPC != null) {
                    if (closestPlayer == null) {
                        return randomMove();
                    } else {
                        target = closestPlayer;
                    }
                }else{
                    if (closestPlayer == null) {
                        return randomMove();
                    } else {*/
                        target = closestPlayer;
                    /*}
                }*/
            } else {
                target = Navigation.findNearest(Pellet.class, getSquare()).getSquare();
            }
            if (target != null) {
                List<Direction> path = Navigation.shortestPath(getSquare(), target, this);
                if (path != null && !path.isEmpty()) {
                    Direction d = path.get(0);
                    return d;
                }
            }
        }
        return randomMove();
    }
    
    private boolean hunter = false;

    @Override
    public void setHunter(boolean hunter) {
        if (hunter) {
            setSprites(new PacManSprites().getGhostHunterSprites());
        }else{
            setSprites(originalsprites);
        }
        this.hunter = hunter;
    }

    @Override
    public boolean isHunter() {
        return hunter;
    }

    public boolean isActive() {
        return active;
    }
    
    @Override
    public void addPoints(int n) {
        this.points += n;
    }

    @Override
    public int hunted() {
        setSprites(new PacManSprites().getGhostVulSprites());
        int ret = value;
        value = value / DECAY;
        active = false;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                reactive();
            }
        }, MultiGhostPlayerGame.PENALTY_TIME);
        return ret;
    }

    private void reactive() {
        setSprites(originalsprites);
        active = true;
    }
}
