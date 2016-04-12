package nl.tudelft.jpacman.sprite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies the loading of sprites.
 * 
 * @author Jeroen Roosen 
 */
@SuppressWarnings("magicnumber")
public class SpriteTest {

    private Sprite sprite;
    private SpriteStore store;
    
    private static final int SPRITE_SIZE = 64;

    /**
     * The common fixture of this test class is
     * a 64 by 64 pixel white sprite.
     *
     * @throws java.io.IOException
     *      when the sprite could not be loaded.
     */
    @Before
    public void setUp() throws IOException {
        store = new SpriteStore();
        sprite = store.loadSprite("/sprite/64x64white.png");
    }

	/**
	 * Verifies the width of a static sprite.
	 */
	@Test
	public void spriteWidth() {
		assertEquals(SPRITE_SIZE, sprite.getWidth());
	}

	/**
	 * Verifies the height of a static sprite.
	 */
	@Test
	public void spriteHeight() {
		assertEquals(SPRITE_SIZE, sprite.getHeight());
	}

	/**
	 * Verifies that an IOException is thrown when the resource could not be
	 * loaded.
	 *
	 * @throws java.io.IOException
	 *             since the sprite cannot be loaded.
	 */
	@Test(expected = IOException.class)
	public void resourceMissing() throws IOException {
		store.loadSprite("/sprite/nonexistingresource.png");
	}

	/**
	 * Verifies that an animated sprite is correctly cut from its base image.
	 */
	@Test
	public void animationWidth() {
		AnimatedSprite animation = store.createAnimatedSprite(sprite, 4, 0,
				false);
		assertEquals(16, animation.getWidth());
	}
	
	/**
	 * Verifies that an animated sprite is correctly cut from its base image.
	 */
	@Test
	public void animationHeight() {
		AnimatedSprite animation = store.createAnimatedSprite(sprite, 4, 0,
				false);
		assertEquals(64, animation.getHeight());
	}
	
	/**
	 * Verifies that an split sprite is correctly cut from its base image.
	 */
	@Test
	public void splitWidth() {
		Sprite split = sprite.split(10, 11, 12, 13);
		assertEquals(12, split.getWidth());
	}
	
	/**
	 * Verifies that an split sprite is correctly cut from its base image.
	 */
	@Test
	public void splitHeight() {
		Sprite split = sprite.split(10, 11, 12, 13);
		assertEquals(13, split.getHeight());
	}
	
	/**
	 * Verifies that a split that isn't within the actual sprite returns an empty sprite.
	 */
	@Test
	public void splitOutOfBounds() {
		Sprite split = sprite.split(10, 10, 64, 10);
		assertTrue(split instanceof EmptySprite);
	}
}
