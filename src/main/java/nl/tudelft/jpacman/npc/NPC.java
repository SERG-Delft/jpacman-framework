package nl.tudelft.jpacman.npc;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.specialcase.SpecialSquare;

/**
 * A non-player unit.
 * 
 * @author Jeroen Roosen 
 */
public abstract class NPC extends Unit {

	
      protected  boolean isDead;
      protected boolean  trap;
	
	
	public abstract  void dead();

	public abstract boolean isDead();
	
	public abstract void trap(SpecialSquare square);
	public abstract boolean isTrap();
	
	
	/**
	 * The time that should be taken between moves.
	 * 
	 * @return The suggested delay between moves in milliseconds.
	 */
	public abstract long getInterval();

	/**
	 * Calculates the next move for this unit and returns the direction to move
	 * in.
	 * 
	 * @return The direction to move in, or <code>null</code> if no move could
	 *         be devised.
	 */
	public abstract Direction nextMove();

}
