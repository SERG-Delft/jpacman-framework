package nl.tudelft.jpacman.sprite;

import java.awt.Graphics;

/**
 * Empty Sprite which does not contain any data. When this sprite is drawn,
 * nothing happens.
 *
 * @author Jeroen Roosen 
 */
public class EmptySprite implements Sprite {

    @Override
    public void draw(Graphics graphics, int x, int y, int width, int height) {
        // nothing to draw.
    }

    @Override
    public Sprite split(int x, int y, int width, int height) {
        return new EmptySprite();
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

}
