package nl.tudelft.jpacman.sprite;

import java.awt.Graphics;

/**
 * Visual representation of some object.
 * 
 * @author Jeroen Roosen
 */
public interface Sprite {

	/**
	 * Draws the sprite on the provided graphics context.
	 * 
	 * @param g
	 *            The graphics context to draw.
	 * @param x
	 *            The destination x coordinate to start drawing.
	 * @param y
	 *            The destination y coordinate to start drawing.
	 * @param width
	 *            The width of the destination draw area.
	 * @param height
	 *            The height of the destination draw area.
	 */
	void draw(Graphics g, int x, int y, int width, int height);
}
