package nl.tudelft.jpacman.game;

import com.google.common.collect.ImmutableList;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.ghost.GhostColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by helldog136 on 25/02/16.
 */
public class MultiGhostPlayerGame extends Game {

    /**
     * The players of this game.
     */
    private final HashMap<GhostColor, Player> players;

    /**
     * The level of this game.
     */
    private final Level level;

    /**
     * Create a new multi players game for the provided level and players.
     * @param _players The players.
     * @param l The level.
     */
    protected MultiGhostPlayerGame(HashMap<GhostColor,Player> _players, Level l) {
        assert _players != null;
        assert l != null;

        this.players = _players;
        this.level = l;
        for (GhostColor c : players.keySet())
        level.registerPlayer(players.get(c));
    }

    @Override
    public List<Player> getPlayers() {
        ArrayList<Player> res = new ArrayList<>();
        for (GhostColor c : players.keySet())
            res.add(players.get(c));
        return res;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    /**
     * Moves the players one square to the north if possible.
     */
    public void moveUp() {
        //move(players, Direction.NORTH);
    }

    /**
     * Moves the players one square to the south if possible.
     */
    public void moveDown() {
        //move(players, Direction.SOUTH);
    }

    /**
     * Moves the players one square to the west if possible.
     */
    public void moveLeft() {
        //move(players, Direction.WEST);
    }

    /**
     * Moves the players one square to the east if possible.
     */
    public void moveRight() {
        //move(players, Direction.EAST);
    }

}
