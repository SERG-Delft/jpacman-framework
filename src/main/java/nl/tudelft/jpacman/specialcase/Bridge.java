package nl.tudelft.jpacman.specialcase;


import java.util.HashMap;



import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.sprite.Sprite;

public class Bridge extends SpecialSquare
{
	/**
	 * Record enter direction for a unit.
	 */
	
	HashMap<Unit,String> listUnite;
	
	public Bridge(Sprite sprite) 
	{
		super(sprite);
		listUnite= new HashMap<Unit,String>();
	}
	
	/**
	 * Add unit to the currentSquare
	 * @param unite new Unit
	 */
	
	public void addUnit(Unit unite)
	{
		String position="";
		switch(unite.getDirection())
		{
		case EAST:
			position="behind";
			break;
		case WEST:
			position="behind";
			break;
		case NORTH:
			position="before";
			break;
		case SOUTH:
			position="before";
			break;

		}
		
		listUnite.put(unite,position);
		
	}
	
	
	/**
	 * Remove unit to the currentSquare
	 * @param unite selected unit
	 */
	public void removeUnit(Unit unite)
	{
		listUnite.remove(unite);
		
	}
	
	/**
	 * Give the direction where was the Unit when is enter on the bridge
	 * @param unite selected unit
	 * @return enter direction of this unit
	 */
	
	public String getEnterDirection(Unit unite)
	{
	   return	listUnite.get(unite);
	}

	/**
	 * Check if the bridge know this Unit
	 * @param unite  Slected unit
	 * @return  yes if he knows, false in other way.
	 */
	
	public boolean knowUnit(Unit unite)
	{
		 Set set = listUnite.entrySet();
	      Iterator iterator = set.iterator();
	      while(iterator.hasNext()) 
	      {
	         Map.Entry mentry = (Map.Entry)iterator.next();
	        if(mentry.getKey().equals(unite)) 
	        {
	        	return true;
	        }
	      }
		
		return false;
		
	
	}
	
}
