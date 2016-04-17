package nl.tudelft.jpacman.npc.ghost;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.specialcase.SpecialSquare;
import nl.tudelft.jpacman.sprite.EmptySprite;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * An antagonist in the game of Pac-Man, a ghost.
 * 
 * @author Jeroen Roosen 
 */
public abstract class Ghost extends NPC 
{
	
	
	
	/**
	 * The sprite map, one sprite for each direction.
	 */
	protected Map<Direction, Sprite> sprites;
	
	protected Object effect;

	/**
	 * Creates a new ghost.
	 * 
	 * @param spriteMap
	 *            The sprites for every direction.
	 */
	protected Ghost(Map<Direction, Sprite> spriteMap)
	{
		this.sprites = spriteMap;
		effect= new Object();
		this.isDead=false;
		this.trap=false;
	}

	@Override
	public Sprite getSprite() {
		return sprites.get(getDirection());
		
	}

	
	@Override
	public void dead()
	{
		this.isDead=true;
		
	}
	
	@Override
	public boolean isDead()
	{
		return this.isDead;
	}
	
	
	@Override
	public void trap(SpecialSquare square) 
	{
		square.activate();
		effect=square;
		this.trap=true;	
	}
	
	
	/**
	 * Check if ghost can leave the trap
	 */
	
	public void check()
	{
		if(effect instanceof SpecialSquare)
		{
		
			if(((SpecialSquare)effect).check())
			{
				this.trap=false;
			}
		}
	}
	




	@Override
	public boolean isTrap()
	{
	
		return this.trap;
	}
	
	/**
	 * Determines a possible move in a random direction.
	 * 
	 * @return A direction in which the ghost can move, or <code>null</code> if
	 *         the ghost is shut in by inaccessible squares.
	 */
	protected Direction randomMove() {
		Square square = getSquare();
		List<Direction> directions = new ArrayList<>();
		for (Direction d : Direction.values()) {
			if (square.getSquareAt(d).isAccessibleTo(this)) {
				directions.add(d);
			}
		}
		if (directions.isEmpty()) {
			return null;
		}
		int i = new Random().nextInt(directions.size());
		return directions.get(i);
	}
}
