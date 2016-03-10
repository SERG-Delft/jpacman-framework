package nl.tudelft.jpacman.game;

import com.google.common.collect.ImmutableList;
import nl.tudelft.jpacman.level.DoublePlayersCollisions;
import nl.tudelft.jpacman.level.GhostPlayer;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;

import java.util.List;

/**
 * A game with two players and a single level.
 *
 * @author François Marbais
 */
public class DoublePlayerGame extends Game{

    /**
     * The player (pacman) of this game.
     */
    private final Player player;

    /**
     * The player (ghost) of this game.
     */
    private final GhostPlayer ghostPlayer;

    /**
     * The level of this game.
     */
    private final Level level;

    /**
     * Create a new single player game for the provided level and player.
     *
     * @param p1
     *            The player(pacman).
     * @param gp
     *            The player(ghost).
     * @param l
     *            The level.
     */
    protected DoublePlayerGame(Player p1, GhostPlayer gp, Level l) {
        assert p1 != null;
        assert gp != null;
        assert l != null;

        this.player = p1;
        this.ghostPlayer = gp;
        this.level = l;
        this.level.setCollisions(new DoublePlayersCollisions());
        level.registerPlayer(gp);
        level.registerPlayer(p1);
    }

        @Override
        public List<Player> getPlayers() {
            return ImmutableList.of(ghostPlayer, player);
        }

    @Override
    void customStart() { }


    @Override
        public Level getLevel() {
            return level;
        }

}
