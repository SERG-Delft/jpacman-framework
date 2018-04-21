package nl.tudelft.jpacman.board;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import nl.tudelft.jpacman.sprite.Sprite;
/**
 * A square on a {@link Board}, which can (or cannot, depending on the type) be
 * occupied by units.
 *
 * @author Jeroen Roosen 
 */
public abstract class Square {

    /**
     * The units occupying this square, in order of appearance.
     */
    private final List<Unit> occupants;

    /**
     * The collection of squares adjacent to this square.
     */
    private final Map<Direction, Square> neighbours;

    /**
     * Creates a new, empty square.
     */
    protected Square() {
        this.occupants = new ArrayList<>();
        this.neighbours = new EnumMap<>(Direction.class);
        assert invariant();
    }

    /**
     * Returns the square adjacent to this square.
     *
     * @param direction
     *            The direction of the adjacent square.
     * @return The adjacent square in the given direction.
     */
    public Square getSquareAt(Direction direction) {
        return neighbours.get(direction);
    }

    /**
     * Links this square to a neighbour in the given direction. Note that this
     * is a one-way connection.
     *
     * @param neighbour
     *            The neighbour to link.
     * @param direction
     *            The direction the new neighbour is in, as seen from this cell.
     */
    public void link(Square neighbour, Direction direction) {
        neighbours.put(direction, neighbour);
        assert invariant();
    }

    /**
     * Returns an immutable list of units occupying this square, in the order in
     * which they occupied this square (i.e. oldest first.)
     *
     * @return An immutable list of units occupying this square, in the order in
     *         which they occupied this square (i.e. oldest first.)
     */
    public List<Unit> getOccupants() {
        return ImmutableList.copyOf(occupants);
    }

    /**
     * Adds a new occupant to this square.
     *
     * @param occupant
     *            The unit to occupy this square.
     */
    void put(Unit occupant) {
        assert occupant != null;
        assert !occupants.contains(occupant);

        occupants.add(occupant);
    }

    /**
     * Removes the unit from this square if it was present.
     *
     * @param occupant
     *            The unit to be removed from this square.
     */
    void remove(Unit occupant) {
        assert occupant != null;
        occupants.remove(occupant);
    }

    /**
     * Verifies that all occupants on this square have indeed listed this square
     * as the square they are currently occupying.
     *
     * @return <code>true</code> iff all occupants of this square have this
     *         square listed as the square they are currently occupying.
     */
    protected final boolean invariant(Square this) {
        for (Unit occupant : occupants) {
            if (occupant.hasSquare() && occupant.getSquare() != this) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines whether the unit is allowed to occupy this square.
     *
     * @param unit
     *            The unit to grant or deny access.
     * @return <code>true</code> iff the unit is allowed to occupy this square.
     */
    public abstract boolean isAccessibleTo(Unit unit);

    /**
     * Returns the sprite of this square.
     *
     * @return The sprite of this square.
     */
    public abstract Sprite getSprite();

}
