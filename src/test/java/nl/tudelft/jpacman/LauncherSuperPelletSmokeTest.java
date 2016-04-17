package nl.tudelft.jpacman;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.npc.ghost.VulnerableGhost;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by coco- on 17-04-16.
 */
public class LauncherSuperPelletSmokeTest extends LauncherSmokeTest{



    private Launcher launcher;

    /**
     * Launch the user interface.
     */
    @Before
    public void setUpPacman() {
        launcher = new LauncherSuperPellet();
        launcher.launch();
    }

    /**
     * Quit the user interface when we're done.
     */
    @After
    public void tearDown() {
        launcher.dispose();
    }

    /**
     * Launch the game, and imitate what would happen when you eat a super pellet.
     *
     */
    @SuppressWarnings("methodlength")
    @Test
    public void smokeTestSuperPellet() throws InterruptedException {
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);

        // start cleanly.
        assertFalse(game.isInProgress());
        game.start();
        assertTrue(game.isInProgress());
        assertEquals(0, player.getScore());

        //vérifie que les ghost sont repassé en mode chasseur
        for (Map.Entry<NPC, ScheduledExecutorService> e : game.getLevel().getNpcs().entrySet()){
            assertTrue(((VulnerableGhost)e.getKey()).isHunter());
        }
        // get super pelet and verify score
        move(game, Direction.EAST,6);
        move(game, Direction.SOUTH,2);
        move(game, Direction.EAST,4);
        move(game, Direction.SOUTH,2);
        assertEquals(180, player.getScore());

        //vérifie que les ghost sont en mode chassé
        for (Map.Entry<NPC, ScheduledExecutorService> e : game.getLevel().getNpcs().entrySet()){
            assertFalse(((VulnerableGhost)e.getKey()).isHunter());
        }
        //on test si le timer se met en pause
        game.stop();
        Thread.sleep(2000L);
        game.start();
        Thread.sleep(5000L);

        //vérifie que les ghost sont pas encore repassé en mode chasseur
        for (Map.Entry<NPC, ScheduledExecutorService> e : game.getLevel().getNpcs().entrySet()){
            assertFalse(((VulnerableGhost)e.getKey()).isHunter());
        }

        Thread.sleep(2000L);
        //vérifie que les ghost sont repassé en mode chasseur
        for (Map.Entry<NPC, ScheduledExecutorService> e : game.getLevel().getNpcs().entrySet()){
            assertTrue(((VulnerableGhost)e.getKey()).isHunter());
        }
        game.stop();
        assertFalse(game.isInProgress());
    }
}
