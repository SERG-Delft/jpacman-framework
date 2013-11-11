package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.sprite.Sprite;

/**
 * A unit that can be placed on a {@link Square}.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public interface Unit {

	/**
	 * Returns the sprite of this unit.
	 * 
	 * @return The sprite of this unit.
	 */
	Sprite getSprite();

	/**
	 * Occupies the target square iff this unit is allowed to as decided by
	 * {@link Square#isAccessibleTo(Unit)}.
	 * 
	 * @param target
	 *            The square to occupy.
	 */
	void occupy(Square target);

	/**
	 * Returns the square this unit is currently occupying.
	 * 
	 * @return The square this unit is currently occupying, or <code>null</code>
	 *         if this unit is not on a square.
	 */
	Square getSquare();

}
