package nl.tudelft.jpacman.level;

import java.util.Map;

import CraeyeMathieu.ChoiceMonster;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.fruit.Fruit;
import nl.tudelft.jpacman.sprite.AnimatedSprite;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * A player operated unit in our game.
 * 
 * @author Jeroen Roosen 
 */
public class Player extends Unit {

	private String name;
	/**
	 * The amount of points accumulated by this player.
	 */
	private int score;

	/**
	 * The animations for every direction.
	 */
	private final Map<Direction, Sprite> sprites;

	/**
	 * The animation that is to be played when Pac-Man dies.
	 */
	private final AnimatedSprite deathSprite;

	/**
	 * <code>true</code> iff this player is alive.
	 */
	private boolean alive;
	
	
	/**
	 * <code>true</code> if this player is invisible.
	 */
	private boolean invisible;
	
	/**
	 * Effect of a player
	 */
	private Object effect;
	
	/**
	 * Define if the player is stun
	 */
	private boolean stun;
	
	/**
	 * Define The spawn square of the player
	 */
	private Square spawn;
	

	/**
	 * Creates a new player with a score of 0 points.
	 * 
	 * @param spriteMap
	 *            A map containing a sprite for this player for every direction.
	 * @param deathAnimation
	 *            The sprite to be shown when this player dies.
	 */
	Player(Map<Direction, Sprite> spriteMap, AnimatedSprite deathAnimation,String name) {
		this.score = 0;
		this.alive = true;
		this.sprites = spriteMap;
		this.deathSprite = deathAnimation;
		this.setName(name);
		deathSprite.setAnimating(false);
		this.invisible=false;
		this.effect= new Object();
		this.stun=false;
	}
	
	/**
	 * Define the effect of a player
	 * @param fruit Fruit act as effect
	 */
	
	public void defineEffect(Fruit fruit)
	{
		this.effect=fruit;
	}
	
	/**
	 * Reset default effect
	 */
	
	public void resetEffect()
	{
		this.effect= new Object();
		this.invisible=false;
		this.stun=false;
	}
	
	/**
	 * Give current effect
	 * @return the effect
	 */
	
	public Object getEffect()
	{
		return effect;
	}
	
	/**
	 * Define if playr has different effect taht the default
	 * @return true if yess, false in other way
	 */
	
	public boolean isEffect()
	{
		if(effect instanceof Fruit)
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	/**
	 * Define if the player is invisible.
	 * @return true if yess, false in other way
	 */
	
	public boolean isInvisible()
	{
		return this.invisible;
	}
	
	/**
	 * Define if the player is Stun.
	 * @return true if yess, false in other way
	 */
	
	public boolean isStun()
	{
		return this.stun;
	}
	
	/**
	 * Change invisible status
	 * @param invisible_ true if invisble, false in other way
	 */
	
	public void setInvisible(boolean invisible_)
	{
		invisible=invisible_;
	}
	
	/**
	 * Change stun status
	 * @param stun_ true if stun, false in other way
	 */
	
	public void setStun(boolean stun_)
	{
		stun=stun_;
	}
	

	/**
	 * Returns whether this player is alive or not.
	 * 
	 * @return <code>true</code> iff the player is alive.
	 */
	public boolean isAlive() {
	
		return alive;
	}

	/**
	 * Sets whether this player is alive or not.
	 * 
	 * @param isAlive
	 *            <code>true</code> iff this player is alive.
	 */
	public void setAlive(boolean isAlive) {
		if (isAlive) {
			deathSprite.setAnimating(false);
		}
		if (!isAlive) {
			deathSprite.restart();
		}
		this.alive = isAlive;
	}

	/**
	 * Returns the amount of points accumulated by this player.
	 * 
	 * @return The amount of points accumulated by this player.
	 */
	public int getScore() {
		return score;
	}

	@Override
	public Sprite getSprite() {
		if (isAlive()) {
			return sprites.get(getDirection());
		}
		return deathSprite;
	}

	/**
	 * Adds points to the score of this player.
	 * 
	 * @param points
	 *            The amount of points to add to the points this player already
	 *            has.
	 */
	public void addPoints(int points) {
		score += points;
	}

	
	/**
	 * Change square spawn of the player
	 * @param spawn_ current spawn
	 */
	
	
	public void setSpawn(Square spawn_)
	{
		this.spawn=spawn_;
	}
	
	/**
	 * Give spawn of the player
	 * @return  current spawn
	 */
	public Square getSpawn()
	{
		return this.spawn;
	}
	


	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

}
