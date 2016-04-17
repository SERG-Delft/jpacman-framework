package nl.tudelft.jpacman;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/*
 * This is a complete test to ensure that the game react as expected when pacman dies
 */
public class DeathTest {
		
	private Launcher launcher;
		
	/**
	 * Launch the user interface.
	 */
	@Before
	public void setUpPacman() {
		launcher = new Launcher();
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
     * Launch the game, and imitate pacman's deaths
     * 
     * @throws InterruptedException Since we're sleeping in this test.
     */
    @Test
    public void deathTest() throws InterruptedException {
        Game game = launcher.getGame();        
        Player player = game.getPlayers().get(0);
 
        // start cleanly.
        assertFalse(game.isInProgress());
        game.start();
        assertTrue(game.isInProgress());
        
        player.setAlive(false);
        Thread.sleep(150L);
        assertTrue(player.isAlive());
        assertTrue(game.isInProgress());
        
        player.setAlive(false);
        Thread.sleep(150L);
        assertTrue(player.isAlive());
        assertTrue(game.isInProgress());
        
        player.setAlive(false);
        Thread.sleep(150L);
        assertTrue(player.isAlive());
        assertTrue(game.isInProgress());

        player.setAlive(false);
        Thread.sleep(50L);
        assertFalse(player.isAlive());
        assertFalse(game.isInProgress());
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
