package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.sprite.EmptySprite;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * A player operated unit in our game.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public class Player extends Unit {

	/**
	 * The amount of points accumulated by this player.
	 */
	private int score;

	/**
	 * Creates a new player with a score of 0 points.
	 */
	public Player() {
		this.score = 0;
	}

	/**
	 * Returns the amount of points accumulated by this player.
	 * 
	 * @return The amount of points accumulated by this player.
	 */
	public int getScore() {
		return score;
	}

	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return new EmptySprite();
	}

}
