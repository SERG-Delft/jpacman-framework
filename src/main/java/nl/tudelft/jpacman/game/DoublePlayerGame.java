package nl.tudelft.jpacman.game;

import com.google.common.collect.ImmutableList;
import nl.tudelft.jpacman.level.GhostPlayer;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;

import java.util.List;

/**
 * Created by francois on 28/02/16.
 */
public class DoublePlayerGame extends Game{

    private final Player player;
    private final GhostPlayer ghostPlayer;

    private final Level level;

    protected DoublePlayerGame(Player p1, GhostPlayer gp, Level l) {
        assert p1 != null;
        assert gp != null;
        assert l != null;

        this.player = p1;
        this.ghostPlayer = gp;
        this.level = l;
        level.registerPlayer(gp);
        level.registerPlayer(p1);
    }

        @Override
        public List<Player> getPlayers() {
            return ImmutableList.of(ghostPlayer, player);
        }

        @Override
        public Level getLevel() {
            return level;
        }

}
