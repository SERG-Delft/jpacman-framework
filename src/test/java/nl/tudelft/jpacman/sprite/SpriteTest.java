package nl.tudelft.jpacman.sprite;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    @BeforeEach
    public void setUp() throws IOException {
        store = new SpriteStore();
        sprite = store.loadSprite("/sprite/64x64white.png");
    }

    /**
     * Verifies the width of a static sprite.
     */
    @Test
    public void spriteWidth() {
        assertThat(sprite.getWidth()).isEqualTo(SPRITE_SIZE);
    }

    /**
     * Verifies the height of a static sprite.
     */
    @Test
    public void spriteHeight() {
        assertThat(sprite.getHeight()).isEqualTo(SPRITE_SIZE);
    }

    /**
     * Verifies that an IOException is thrown when the resource could not be
     * loaded.
     *
     * @throws java.io.IOException
     *             since the sprite cannot be loaded.
     */
    @Test
    public void resourceMissing() throws IOException {
        assertThatThrownBy(() -> store.loadSprite("/sprite/nonexistingresource.png"))
            .isInstanceOf(IOException.class);
    }

    /**
     * Verifies that an animated sprite is correctly cut from its base image.
     */
    @Test
    public void animationWidth() {
        AnimatedSprite animation = store.createAnimatedSprite(sprite, 4, 0,
            false);
        assertThat(animation.getWidth()).isEqualTo(16);
    }

    /**
     * Verifies that an animated sprite is correctly cut from its base image.
     */
    @Test
    public void animationHeight() {
        AnimatedSprite animation = store.createAnimatedSprite(sprite, 4, 0,
            false);
        assertThat(animation.getHeight()).isEqualTo(64);
    }

    /**
     * Verifies that an split sprite is correctly cut from its base image.
     */
    @Test
    public void splitWidth() {
        Sprite split = sprite.split(10, 11, 12, 13);
        assertThat(split.getWidth()).isEqualTo(12);
    }

    /**
     * Verifies that an split sprite is correctly cut from its base image.
     */
    @Test
    public void splitHeight() {
        Sprite split = sprite.split(10, 11, 12, 13);
        assertThat(split.getHeight()).isEqualTo(13);
    }

    /**
     * Verifies that a split that isn't within the actual sprite returns an empty sprite.
     */
    @Test
    public void splitOutOfBounds() {
        Sprite split = sprite.split(10, 10, 64, 10);
        assertThat(split).isInstanceOf(EmptySprite.class);
    }
}
