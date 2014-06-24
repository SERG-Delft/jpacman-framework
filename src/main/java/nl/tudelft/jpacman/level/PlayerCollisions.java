package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.ghost.Ghost;

/**
 * A simple implementation of a collision map for the JPacman player.
 * <p>
 * It uses a number of instanceof checks to implement the multiple dispatch for the 
 * collisionmap. For more realistic collision maps, this approach will not scale,
 * and the recommended approach is to use a {@link CollisionInteractionMap}.
 * 
 * @author Arie van Deursen, 2014
 *
 */

public class PlayerCollisions implements CollisionMap {

	@Override
	public void collide(Unit mover, Unit collidedOn) {
		
		if (mover instanceof Player) {
			playerColliding((Player) mover, collidedOn);
		}
		else if (mover instanceof Ghost) {
			ghostColliding((Ghost) mover, collidedOn);
		}
	}
	
	private void playerColliding(Player player, Unit collidedOn) {
		if (collidedOn instanceof Ghost) {
			playerVersusGhost(player, (Ghost) collidedOn);
		}
		
		if (collidedOn instanceof Pellet) {
			playerVersusPellet(player, (Pellet) collidedOn);
		}		
		
		if (collidedOn instanceof Fruit) {
			playerVersusFruit(player, (Fruit) collidedOn);
		}
	}
	
	private void ghostColliding(Ghost ghost, Unit collidedOn) {
		if (collidedOn instanceof Player) {
			playerVersusGhost((Player) collidedOn, ghost);
		}
	}
	
	
	/**
	 * Actual case of player bumping into ghost or vice versa.
	 */
	private void playerVersusGhost(Player player, Ghost ghost) {
		player.setAlive(false);
	}
	
	/**
	 * Actual case of player consuming a pellet.
	 */
	private void playerVersusPellet(Player player, Pellet pellet) {
		pellet.leaveSquare();
		player.addPoints(pellet.getValue());		
	}

	/**
	 * Actual case of player consuming a fruit.
	 */
	private void playerVersusFruit(Player player, Fruit fruit) {
		fruit.leaveSquare();
		player.addPoints(fruit.getValue());		
	}
}
