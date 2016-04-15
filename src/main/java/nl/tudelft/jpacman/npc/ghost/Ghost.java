package nl.tudelft.jpacman.npc.ghost;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * An antagonist in the game of Pac-Man, a ghost.
 * 
 * @author Jeroen Roosen 
 */
public abstract class Ghost extends NPC {
	
	/**
	 * The sprite map, one sprite for each direction.
	 */
	private final Map<Direction, Sprite> sprites;

	/**
	 * Creates a new ghost.
	 * 
	 * @param spriteMap
	 *            The sprites for every direction.
	 */
	protected Ghost(Map<Direction, Sprite> spriteMap) {
		this.sprites = spriteMap;
	}

	@Override
	public Sprite getSprite() {
		return sprites.get(getDirection());
	}

	/**
	 * Determines a possible move in a random direction.
	 * 
	 * @return A direction in which the ghost can move, or <code>null</code> if
	 *         the ghost is shut in by inaccessible squares.
	 */
	protected Direction randomMove() {
		Square square = getSquare();
		List<Direction> directions = directions(new ArrayList<>(), square);
		Direction dChosen = null;

		if (!directions.isEmpty()) {
			dChosen = directions.get(new Random().nextInt(directions.size()));
		}

		return dChosen;
	}

	/**
	 * Determmines the accessible adjacent squares next to the square where he is.
	 *
	 * @param directions
	 * 		List of the accessible adjacent squares.
	 *
	 * @param s
	 * 		The ghost square
	 *
     * @return List of the accessible adjacent squares by the ghost from where he is.
     */
	private List<Direction> directions(List<Direction> directions, Square s){
		for (Direction d : Direction.values()) {
			if (s.getSquareAt(d).isAccessibleTo(this)) {
				directions.add(d);
			}
		}

		return directions;
	}
}
