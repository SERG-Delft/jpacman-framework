package nl.tudelft.jpacman;

import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.LevelFactorySuperPellet;
import nl.tudelft.jpacman.level.LevelSuperPellet;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.npc.ghost.VulnerableGhostFactory;

import java.io.IOException;

/**
 * Launch with Super pellet level
 */
public class LauncherSuperPellet extends Launcher {
    /**
     * Main execution method for the Launcher.
     *
     * @param args
     *            The command line arguments - which are ignored.
     * @throws IOException
     *             When a resource could not be read.
     */
    public static void main(String[] args) throws IOException {
        new LauncherSuperPellet().launch(false);
    }

    @Override
    public Game getGame() {
        return super.getGame();
    }

    @Override
    protected LevelFactory getLevelFactory() {
        return new LevelFactorySuperPellet(getSpriteStore(),getGhostFactory());
    }

    @Override
    protected GhostFactory getGhostFactory() {
        return new VulnerableGhostFactory(getSpriteStore());
    }
}
