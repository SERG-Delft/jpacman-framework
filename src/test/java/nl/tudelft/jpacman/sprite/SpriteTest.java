package nl.tudelft.jpacman.sprite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

/**
 * Verifies the loading of sprites.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public class SpriteTest {

	/**
	 * Verifies the width of a static sprite.
	 * 
	 * @throws IOException
	 *             when the sprite could not be loaded.
	 */
	@Test
	public void spriteWidth() throws IOException {
		SpriteStore store = new SpriteStore();
		Sprite sprite = store.loadSprite("/sprite/64x64white.png");
		assertEquals(64, sprite.getWidth());
	}

	/**
	 * Verifies the height of a static sprite.
	 * 
	 * @throws IOException
	 *             when the sprite could not be loaded.
	 */
	@Test
	public void spriteHeight() throws IOException {
		SpriteStore store = new SpriteStore();
		Sprite sprite = store.loadSprite("/sprite/64x64white.png");
		assertEquals(64, sprite.getHeight());
	}

	/**
	 * Verifies that an IOException is thrown when the resource could not be
	 * loaded.
	 * 
	 * @throws IOException
	 *             when the sprite could not be loaded.
	 */
	@Test(expected = IOException.class)
	public void resourceMissing() throws IOException {
		SpriteStore store = new SpriteStore();
		store.loadSprite("/sprite/nonexistingresource.png");
	}

	/**
	 * Verifies that an animated sprite is correctly cut from its base image.
	 * 
	 * @throws IOException
	 *             When the sprite could not be loaded.
	 */
	@Test
	public void animationWidth() throws IOException {
		SpriteStore store = new SpriteStore();
		Sprite sprite = store.loadSprite("/sprite/64x64white.png");
		AnimatedSprite animation = store.createAnimatedSprite(sprite, 4, 0,
				false);
		assertEquals(16, animation.getWidth());
	}
	
	/**
	 * Verifies that an animated sprite is correctly cut from its base image.
	 * 
	 * @throws IOException
	 *             When the sprite could not be loaded.
	 */
	@Test
	public void animationHeight() throws IOException {
		SpriteStore store = new SpriteStore();
		Sprite sprite = store.loadSprite("/sprite/64x64white.png");
		AnimatedSprite animation = store.createAnimatedSprite(sprite, 4, 0,
				false);
		assertEquals(64, animation.getHeight());
	}
	
	/**
	 * Verifies that an split sprite is correctly cut from its base image.
	 * 
	 * @throws IOException
	 *             When the sprite could not be loaded.
	 */
	@Test
	public void splitWidth() throws IOException {
		SpriteStore store = new SpriteStore();
		Sprite sprite = store.loadSprite("/sprite/64x64white.png");
		Sprite split = sprite.split(10, 11, 12, 13);
		assertEquals(12, split.getWidth());
	}
	
	/**
	 * Verifies that an split sprite is correctly cut from its base image.
	 * 
	 * @throws IOException
	 *             When the sprite could not be loaded.
	 */
	@Test
	public void splitHeight() throws IOException {
		SpriteStore store = new SpriteStore();
		Sprite sprite = store.loadSprite("/sprite/64x64white.png");
		Sprite split = sprite.split(10, 11, 12, 13);
		assertEquals(13, split.getHeight());
	}
	
	/**
	 * Verifies that a split that isn't within the actual sprite returns an empty sprite.
	 * 
	 * @throws IOException
	 *             When the sprite could not be loaded.
	 */
	@Test
	public void splitOutOfBounds() throws IOException {
		SpriteStore store = new SpriteStore();
		Sprite sprite = store.loadSprite("/sprite/64x64white.png");
		Sprite split = sprite.split(10, 10, 64, 10);
		assertTrue(split instanceof EmptySprite);
	}
}
