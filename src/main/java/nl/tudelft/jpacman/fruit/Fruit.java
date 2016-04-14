package nl.tudelft.jpacman.fruit;

import java.util.Date;

import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.sprite.Sprite;

public  class Fruit  extends Pellet
{
	protected Date beginDate;
	protected boolean isActive;

	
	public Fruit(int points, Sprite sprite) 
	{
		super(points, sprite);
	}
	
	
	
	public void activate()
	{	
		beginDate= new Date();	
		isActive=true;
	}
	
	public boolean check()
	{
		Date date = new Date();
		
		long timeDate=date.getTime();
		long begin=beginDate.getTime();
		
		long diffSecond=(date.getTime()-beginDate.getTime());
		if(diffSecond>5000)
		{
			isActive=false;
			return true;
		}else
		{
			return false;
		}
		
	}
	



}
