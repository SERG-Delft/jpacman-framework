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
     * @param graphics
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
    void draw(Graphics graphics, int x, int y, int width, int height);

    /**
     * Returns a portion of this sprite as a new Sprite.
     *
     * @param x
     *            The x start coordinate.
     * @param y
     *            The y start coordinate.
     * @param width
     *            The width of the target sprite.
     * @param height
     *            The height of the target sprite.
     * @return A new sprite of width x height, or a new {@link EmptySprite} if
     *         the region was not in the current sprite.
     */
    Sprite split(int x, int y, int width, int height);

    /**
     * Returns the width of this sprite.
     *
     * @return The width in pixels.
     */
    int getWidth();

    /**
     * Returns the height of this sprite.
     *
     * @return The height of this sprite in pixels.
     */
    int getHeight();
}
