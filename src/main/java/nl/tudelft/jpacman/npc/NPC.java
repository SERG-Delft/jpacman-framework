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

	
	/**
	 * Define if Npc is dead or not.
	 */
      protected  boolean isDead;
      
      /**
       * Define if the npc is trap or not.
       */
      protected boolean  trap;
	
      /**
       * Define Npc death.
       */
	
	public abstract  void dead();

	/**
	 * Check if Npc is dead.
	 * @return true if yess, false in other way
	 */
	
	public abstract boolean isDead();
	
	
	/**
	 * Define trap where is Npc.
	 * @param square current trap
	 */
	
	public abstract void trap(SpecialSquare square);
	/**
	 * Check if Npc is trap.
	 * @return true if yess, false in other way
	 */
	
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
