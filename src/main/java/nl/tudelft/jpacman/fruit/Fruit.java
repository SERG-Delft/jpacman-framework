package nl.tudelft.jpacman.fruit;

import java.util.Date;

import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.sprite.Sprite;

public  class Fruit  extends Pellet
{
	
	/**
	 * Begin time for effect.
	 */
	protected Date beginDate;
	
	/**
	 * Define if effect is active or not
	 */
	protected boolean isActive;

	

	public Fruit(int points, Sprite sprite) 
	{
		super(points, sprite);
	}
	
	/**
	 * Give effect.
	 * @return null
	 */
	
	public String effect()
	{
		return "";
	}
	
	/**
	 * Activate effect
	 */
	
	public void activate()
	{	
		beginDate= new Date();	
		isActive=true;
	}
	
	/**
	 * Check if effect is over
	 * @return true if yes, false per default
	 */
	
	public boolean check()
	{
		Date date = new Date();
		
		long timeDate=date.getTime();
		long begin=beginDate.getTime();
		
		long diffSecond=(date.getTime()-beginDate.getTime());
		if(diffSecond>4000)
		{
			isActive=false;
			return true;
		}else
		{
			return false;
		}
		
	}
	



}
