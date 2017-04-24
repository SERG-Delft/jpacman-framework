package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;

/**
 * A table containing all (relevant) collisions between different types of
 * units.
 *
 * @author Jeroen Roosen 
 */
public interface CollisionMap {

    /**
     * Collides the two units and handles the result of the collision, which may
     * be nothing at all.
     *
     * @param <C1>
     *            The collider type.
     * @param <C2>
     *            The collidee (unit that was moved into) type.
     *
     * @param collider
     *            The unit that causes the collision by occupying a square with
     *            another unit already on it.
     * @param collidee
     *            The unit that is already on the square that is being invaded.
     */
    <C1 extends Unit, C2 extends Unit> void collide(C1 collider, C2 collidee);

}
