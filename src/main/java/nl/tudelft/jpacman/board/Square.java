package nl.tudelft.jpacman.board;

import java.util.List;

import nl.tudelft.jpacman.sprite.Sprite;

/**
 * A square on a {@link Board}, which can (or cannot, depending on the type) be
 * occupied by units.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public interface Square {

	/**
	 * Returns an immutable list of units occupying this square, in the order in
	 * which they occupied this square (i.e. oldest first.)
	 * 
	 * @return An immutable list of units occupying this square, in the order in
	 *         which they occupied this square (i.e. oldest first.)
	 */
	List<Unit> getOccupants();

	/**
	 * Returns the square adjacent to this square.
	 * 
	 * @param direction
	 *            The direction of the adjacent square.
	 * @return The adjacent square in the given direction.
	 */
	Square getSquareAt(Direction direction);

	/**
	 * Determines whether the unit is allowed to occupy this square.
	 * 
	 * @param unit
	 *            The unit to grant or deny access.
	 * @return <code>true</code> iff the unit is allowed to occupy this square.
	 */
	boolean isAccessibleTo(Unit unit);

	/**
	 * Returns the sprite of this square.
	 * 
	 * @return The sprite of this square.
	 */
	Sprite getSprite();

}
