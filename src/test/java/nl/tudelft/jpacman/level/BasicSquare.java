package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * Basic implementation of square.
 * 
 * @author Jeroen Roosen 
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
