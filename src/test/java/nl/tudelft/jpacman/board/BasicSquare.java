package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.sprite.Sprite;

/**
 * Basic implementation of square.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
class BasicSquare extends Square {

	/**
	 * Creates a new basic square.
	 */
	BasicSquare() {
		super();
	}

	@Override
	public boolean isAccessibleTo(Unit unit) {
		return true;
	}

	@Override
	public Sprite getSprite() {
		return null;
	}
}
