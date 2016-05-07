package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

/**
 * A class to test some function of the SinglePlayerGame class
 * @author santorin
 *
 */
public class SinglePlayerGameTest {
	
	private SinglePlayerGame game;
	private Level lvl1;
	private Level lvl2;
	
	@Before
	public void setUp() {
		PacManSprites sprites = new PacManSprites();
		lvl1 = mock(Level.class);
		lvl2 = mock(Level.class);
		Level[] lvls = {lvl1, lvl2};
		GameFactory gf = new GameFactory(new PlayerFactory(sprites));
		game = (SinglePlayerGame) gf.createSinglePlayerGame(lvls);
	}
	
	/**
	 * Basic test to verify that the method levelWon indeed switches the level
	 * to the good one
	 * Also test that the method previousLevel rolls back to the desired level
	 */
	@Test
	public void wonTest(){
		assertTrue(game.getLevelIndex() == 0);
		Level l1 = game.getLevel();
		assertSame(lvl1, l1);
		
		game.levelWon();
		
		assertTrue(game.getLevelIndex() == 1);
		Level l2 = game.getLevel();
		assertNotSame(l1, l2);
		assertSame(lvl2, l2);
		
		game.previousLevel();
		assertSame(l1, game.getLevel());
	}

}
