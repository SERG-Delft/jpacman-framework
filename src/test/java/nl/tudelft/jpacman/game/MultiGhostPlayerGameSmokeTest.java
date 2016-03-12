package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.HunterGhostPlayer;
import nl.tudelft.jpacman.level.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Smoke test launching the full game,
 * and attempting to make a number of typical moves.
 *
 * This is <strong>not</strong> a <em>unit</em> test -- it is an end-to-end test
 * trying to execute a large portion of the system's behavior directly from the
 * user interface. It uses the actual sprites and monster AI, and hence
 * has little control over what is happening in the game.
 *
 * Because it is an end-to-end test, it is somewhat longer
 * and has more assert statements than what would be good
 * for a small and focused <em>unit</em> test.
 *
 * @author Arie van Deursen, March 2014.
 */
@SuppressWarnings("magicnumber")
public class MultiGhostPlayerGameSmokeTest {

    private Launcher launcher;

    /**
     * Launch the user interface.
     */
    @Before
    public void setUpPacman() {
        launcher = new Launcher();
        launcher.launch();
        launcher.makeGame(Launcher.MULTI_GHOST, 2);
        launcher.getGame().stop();
    }

    /**
     * Quit the user interface when we're done.
     */
    @After
    public void tearDown() {
        launcher.dispose();
    }

    /**
     * Launch the game, and imitate what would happen in a typical game.
     * The test is only a smoke test, and not a focused small test.
     * Therefore it is OK that the method is a bit too long.
     *
     * @throws InterruptedException Since we're sleeping in this test.
     */
    @SuppressWarnings("methodlength")
    @Test
    public void smokeTest() throws InterruptedException {
        Game game = launcher.getGame();
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);

        // start cleanly.
        assertFalse(game.isInProgress());
        game.start();
        assertTrue(game.isInProgress());
        ((HunterGhostPlayer)player1).setHunter(false);
        ((HunterGhostPlayer)player2).setHunter(false);
        assertEquals(0, player1.getScore());
        assertEquals(0, player2.getScore());

        // get points
        game.move(player1, Direction.EAST);
        assertEquals(10, player1.getScore());
        game.move(player2, Direction.EAST);
        assertEquals(10, player2.getScore());

        // now moving back does not change the score
        game.move(player1, Direction.WEST);
        assertEquals(10, player1.getScore());
        game.move(player2, Direction.WEST);
        assertEquals(10, player2.getScore());

        // try to move as far as we can
        move(game, Direction.EAST, 7, 0);
        assertEquals(60, player1.getScore());
        move(game, Direction.EAST, 7, 1);
        assertEquals(60, player2.getScore());

        game.stop();
        assertFalse(game.isInProgress());
    }

    /**
     * Make number of moves in given direction.
     *
     * @param game The game we're playing
     * @param dir The direction to be taken
     * @param numSteps The number of steps to take
     */
    public static void move(Game game, Direction dir, int numSteps, int playerIndex) {
        Player player = game.getPlayers().get(playerIndex);
        for (int i = 0; i < numSteps; i++) {
            game.move(player, dir);
        }
    }
}