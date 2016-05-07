package nl.tudelft.jpacman.board;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import nl.tudelft.jpacman.npc.ghost.Ghost;
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
	 * Adds a new occupant to this square. If the occupant was already present,
	 * nothing changed.
	 * 
	 * @param occupant
	 *            The unit to occupy this square.
	 * @return <code>true</code> iff the unit successfully occupied this square.
	 */
	boolean put(Unit occupant) {
		assert occupant != null;
		if (!occupants.contains(occupant)) {
			occupants.add(occupant);
			return true;
		}
		return false;
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
	 * Tests whether all occupants on this square have indeed listed this square
	 * as the square they are currently occupying.
	 * 
	 * @return <code>true</code> iff all occupants of this square have this
	 *         square listed as the square they are currently occupying.
	 */
	protected boolean invariant() {
		for (Unit occupant : occupants) {
			if (occupant.getSquare() != this) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Returns the nearest square which is valid for pacman to respawn in
	 * @return
	 */
	public Square nearestValidRespawn(Unit player, List<Square> visited) {
		if(visited == null){
			visited = new ArrayList<Square>();
		}
		if(this != null){
			if(isAccessibleTo(player) && isValidRespawnPoint(player, 3)
					&& isConnectedTo(player, player.getSquare(), null)){
				return this;
			}
			for(Direction d: Direction.values()){
				Square sq = getSquareAt(d);
				if(!visited.contains(sq) && sq != null){
					visited.add(sq);
					Square result = sq.nearestValidRespawn(player, visited);
					if(result != null)
					return result;
				}
			}
		}
		return null;
		
	}
	
	/**
	 * Determines whether the unit is allowed to respawn on this square
	 * 
	 * @param player
	 * @param x
	 * @return true iff there is no ghost x squares around this square
	 */
	public boolean isValidRespawnPoint(Unit player, int x){
		for(Unit unit : occupants){
			if(unit instanceof Ghost){
				return false;
			}
		}
		if(x > 0){
			for(Direction d : Direction.values()){
				Square s = getSquareAt(d);
				if(!s.isValidRespawnPoint(player, x-1)){
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean isConnectedTo(Unit player, Square s, List<Square> visited){
		if(visited == null){
			visited = new ArrayList<Square>();
		}
		if(s == this){
			return true;
		}
		for(Direction d : Direction.values()){
			Square sq = s.getSquareAt(d);
			if(!visited.contains(sq)){
				visited.add(sq);
				if(sq.isAccessibleTo(player)){
					if(isConnectedTo(player, sq, visited)){
						return true;
					}
				}
			}
		}
		return false;
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
