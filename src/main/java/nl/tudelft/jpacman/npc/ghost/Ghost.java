package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.sprite.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
	 * <code>currentMove</code>: Move currently followed by the ghost.
	 * <code>dispersion</code>: Ghost dispersion move.
	 * <code>pursuitMove</code>: Ghost pursuit move.
	 */
	private MoveStrategy currentMove, dispersion, pursuit;

	/**
	 * The square to state the ghost home place.
	 */
	private Square home;

	/**
	 * Boolean to know if it is in corner.
	 */
	private boolean inMyCorner;

	/**
	 * Creates a new ghost.
	 * 
	 * @param spriteMap
	 *            The sprites for every direction.
	 */
	protected Ghost(Map<Direction, Sprite> spriteMap) {
		this.sprites = spriteMap;
		this.inMyCorner = false;
	}

	@Override
	public Sprite getSprite() { return sprites.get(getDirection()); }

	/**
	 * Assign a place for ghost house.
	 *
	 * @param h
	 * 		The place for the ghost house.
     */
	public void setHome(Square h){ this.home = h; }

	/**
	 * Allow to a ghost to say if it is in its corner.
	 *
	 * @param t
	 * 		Boolean value. <code>true</code> if it is in its corner, <code>false</code> else.
     */
	public void inMyCorner(boolean t){ inMyCorner = t; }

	@Override
	public boolean isInMyCorner(){ return inMyCorner; }

	/**
	 * Return the house place.
	 *
	 * @return The place for the ghost house.
	 */
	public Square getHome(){ return this.home; }

	@Override
	public MoveStrategy changeMove() {
		if(!inPursuitMove()){
			setCurrentMove(pursuit());
		}else{
			setCurrentMove(dispersion());
		}

		return getCurrentMove();
	}

	/**
	 *@return The move currently followed by the ghost.
     */
	protected MoveStrategy getCurrentMove(){ return currentMove; }

	/**
	 * Modify the ghost current move.
	 * @param m
	 * 		A move strategy.
     */
	private void setCurrentMove(MoveStrategy m){
		currentMove = m;
	}

	/**
	 * @return The dispersion move.
     */
	private MoveStrategy dispersion(){
		return dispersion;
	}

	/**
	 * @return The pursuit move.
	 */
	private MoveStrategy pursuit(){
		((DispersionMove)dispersion).resetDirectionCounter();

		return pursuit;
	}

	public boolean inPursuitMove(){ return currentMove instanceof PursuitMove; }

	/**
	 * Define the ghost move moveStrategies.
	 *
	 * @param d
	 * 		The ghost dispersion move.
	 *
	 * @param p
	 * 		The ghost pursuit move.
     */
	protected void moveStrategies(MoveStrategy d, MoveStrategy p){
		this.dispersion = d;
		this.pursuit = p;
		this.currentMove = d;
	}

	/**
	 * Determines a possible move in a random direction.
	 * 
	 * @return A direction in which the ghost can move, or <code>null</code> if
	 *         the ghost is shut in by inaccessible squares.
	 */
	protected Direction randomMove() {
		List<Direction> thePath = path(new ArrayList<>(), getSquare());
		Direction d = null;

		if (!thePath.isEmpty()) d = thePath.get(new Random().nextInt(thePath.size()));

		return d;
	}

	/**
	 * Determmines the accessible adjacent squares next to the square where he is.
	 *
	 * @param path
	 * 		List of the accessible adjacent squares.
	 *
	 * @param s
	 * 		The ghost square
	 *
     * @return List of the accessible adjacent squares by the ghost from where he is.
     */
	private List<Direction> path(List<Direction> path, Square s){
		for (Direction d : Direction.values())
			if (s.getSquareAt(d).isAccessibleTo(this)) path.add(d);


		return path;
	}
}
