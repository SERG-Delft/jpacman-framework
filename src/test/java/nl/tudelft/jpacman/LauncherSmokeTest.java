package nl.tudelft.jpacman;

import static org.assertj.core.api.Assertions.assertThat;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
public class LauncherSmokeTest {

    private Launcher launcher;

    /**
     * Launch the user interface.
     */
    @BeforeEach
    void setUpPacman() {
        launcher = new Launcher();
        launcher.launch();
    }

    /**
     * Quit the user interface when we're done.
     */
    @AfterEach
    void tearDown() {
        launcher.dispose();
    }

    /**
     * Launch the game, and imitate what would happen in a typical game.
     * The test is only a smoke test, and not a focused small test.
     * Therefore it is OK that the method is a bit too long.
     *
     * @throws InterruptedException Since we're sleeping in this test.
     */
    @SuppressWarnings({"magicnumber", "methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    @Test
    void smokeTest() throws InterruptedException {
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // get points
        game.move(player, Direction.EAST);
        assertThat(player.getScore()).isEqualTo(10);

        // now moving back does not change the score
        game.move(player, Direction.WEST);
        assertThat(player.getScore()).isEqualTo(10);

        // try to move as far as we can
        move(game, Direction.EAST, 7);
        assertThat(player.getScore()).isEqualTo(60);

        // move towards the monsters
        move(game, Direction.NORTH, 6);
        assertThat(player.getScore()).isEqualTo(120);

        // no more points to earn here.
        move(game, Direction.WEST, 2);
        assertThat(player.getScore()).isEqualTo(120);

        move(game, Direction.NORTH, 2);

        // Sleeping in tests is generally a bad idea.
        // Here we do it just to let the monsters move.
        Thread.sleep(500L);

        // we're close to monsters, this will get us killed.
        move(game, Direction.WEST, 10);
        move(game, Direction.EAST, 10);
        assertThat(player.isAlive()).isFalse();

        game.stop();
        assertThat(game.isInProgress()).isFalse();
    }

    /**
     * Make number of moves in given direction.
     *
     * @param game The game we're playing
     * @param dir The direction to be taken
     * @param numSteps The number of steps to take
     */
    public static void move(Game game, Direction dir, int numSteps) {
        Player player = game.getPlayers().get(0);
        for (int i = 0; i < numSteps; i++) {
            game.move(player, dir);
        }
    }
}
