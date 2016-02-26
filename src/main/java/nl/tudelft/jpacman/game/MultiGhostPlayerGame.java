package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.GhostPlayer;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.MultiGhostPlayerCollisions;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.ghost.Ghost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by helldog136 on 25/02/16.
 */
public class MultiGhostPlayerGame extends Game {

    /**
     * The players of this game.
     */
    private final ArrayList<GhostPlayer> players;

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
    protected MultiGhostPlayerGame(ArrayList<GhostPlayer> _players, Level l) {
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


    @Override
    void customStart() {
        //TODO
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
