package nl.tudelft.jpacman.specialcase;

import java.util.Date;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.sprite.Sprite;

public class SpecialSquare extends Unit
{

	/**
	 * The sprite of this unit.
	 */
	public Sprite image;
	
	/**
	 * Begin time where an Unit fall in this square.
	 */
	
	protected Date beginDate;
	
	
	public SpecialSquare(Sprite sprite)
	{
		this.image=sprite;
	}
	
	@Override
	public Sprite getSprite() {
		return image;
	}
	
	/**
	 * Activate effect of the SpecialCase
	 */
	
	public void activate()
	{
		beginDate= new Date();	
	}
	
	/**
	 * Check if the unit can leave this square
	 * @return
	 */
	
	public boolean check()
	{
     Date date = new Date();
		
		long timeDate=date.getTime();
		long begin=beginDate.getTime();
		
		long diffSecond=(date.getTime()-beginDate.getTime());
		if(diffSecond>300)
		{
			return true;
		}else
		{
			return false;
		}
	}

}
