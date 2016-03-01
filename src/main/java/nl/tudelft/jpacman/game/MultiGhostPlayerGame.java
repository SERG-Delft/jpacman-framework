package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.HunterGhostPlayer;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.MultiGhostPlayerCollisions;
import nl.tudelft.jpacman.level.Player;

import java.util.*;

/**
 * Created by helldog136 on 25/02/16.
 */
public class MultiGhostPlayerGame extends Game {

    private static final long HUNTER_SWITCH_INTERVAL = 10*1000;
    private static final int SWITCH_PROBA_START = 100;
    /**
     * The players of this game.
     */
    private final ArrayList<HunterGhostPlayer> players;

    /**
     * The level of this game.
     */
    private final Level level;
    private final MultiGhostPlayerCollisions collisions;

    /**
     * Create a new multi players game for the provided level and players.
     * @param _players The players.
     * @param l The level.
     */
    protected MultiGhostPlayerGame(ArrayList<HunterGhostPlayer> _players, Level l) {
        assert _players != null;
        assert l != null;

        this.players = _players;
        this.level = l;
        this.collisions = new MultiGhostPlayerCollisions();
        this.level.setCollisions(collisions);
        for (Player p : players) {
            level.registerPlayer(p);
        }
    }

    private int currentHunterIndex = 0;
    private Timer hunterSwitchTimer = new Timer();
    private double[] hunterSwitchProbs = {SWITCH_PROBA_START,SWITCH_PROBA_START,SWITCH_PROBA_START,SWITCH_PROBA_START};
    @Override
    void customStart() {
        if(isInProgress()){
            //fisrt choose (weighted-)randomly a player
            int bestval = Integer.MIN_VALUE;
            int index = 0;
            for (int i = 0; i < 4; i++) {
                int val = new Random().nextInt((int)hunterSwitchProbs[i]);
                if(val > bestval){
                    index = i;
                    bestval = val;
                }
            }
            //then divide by 2 the weight of chances to be a hunter
            hunterSwitchProbs[index] = hunterSwitchProbs[index]/2;
            //then check if must not reset weights
            checkHunterSwitchProbs();
            players.get(currentHunterIndex).setHunter(false);
            currentHunterIndex = index;
            players.get(currentHunterIndex).setHunter(true);
        }
        hunterSwitchTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                customStart();
            }
        }, HUNTER_SWITCH_INTERVAL);
    }

    private void checkHunterSwitchProbs() {
        boolean must = true;
        for (int i = 0; i < 4; i++) {
            if(hunterSwitchProbs[i] > SWITCH_PROBA_START - 1){
                must = false;
            }
        }
        if(must){
            for (int i = 0; i < 4; i++) {
                hunterSwitchProbs[i] = hunterSwitchProbs[i] * 2;
            }
        }
    }

    @Override
    public List<Player> getPlayers() {
        return Arrays.asList(players.toArray(new Player[players.size()]));
    }

    @Override
    public Level getLevel() {
        return level;
    }

}
