package nl.tudelft.jpacman.level;

import static org.junit.Assert.*;
import nl.tudelft.jpacman.sprite.PacManSprites;

import org.junit.Before;
import org.junit.Test;

/**
 * Teste plusieurs méthodes de la classe Player
 * @author santorin
 *
 */
public class PlayerTest {
	
	Player p;
	PlayerFactory pf;
	
	@Before
	public void setUp() {
		PacManSprites sprites = new PacManSprites();
		pf = new PlayerFactory(sprites);
	}
	
	/**
	 * We check that the method hasLifeRemaining fonctionne correctement
	 */
	@Test 
	public void hasLifeTest(){
		p = pf.createPacMan();
		assertTrue(p.hasLifeRemaining());
		p.setAlive(false);
		assertTrue(p.hasLifeRemaining());
		p.setAlive(true);
		p.setAlive(false);
		assertTrue(p.hasLifeRemaining());
		p.setAlive(true);
		p.setAlive(false);
		assertTrue(p.hasLifeRemaining());
		p.setAlive(true);
		p.setAlive(false);
		assertFalse(p.hasLifeRemaining());
	}
	
	/**
	 * Test that the score evolves well and we effectively wins lifes
	 * with score
	 */
	@Test
	public void scoreTest(){
		p = pf.createPacMan();
		assertEquals(p.getScore(),0);
		p.addPoints(238);
		assertEquals(p.getScore(),238);
		
		//we re supposed to win a life
		p.addPoints(10000);
		
		//we'll check that by dying 5 times
		p.setAlive(false);
		p.setAlive(true);
		p.setAlive(false);
		p.setAlive(true);
		p.setAlive(false);
		p.setAlive(true);
		p.setAlive(false);
		
		//we're supposed to be alive
		assertTrue(p.hasLifeRemaining());
		p.setAlive(true);
		p.setAlive(false);
		//we're supposed to be dead now 
		assertFalse(p.hasLifeRemaining());
	}

}
