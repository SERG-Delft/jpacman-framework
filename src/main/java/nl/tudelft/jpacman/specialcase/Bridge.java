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
	HashMap<Unit,String> listUnite;
	
	public Bridge(Sprite sprite) 
	{
		super(sprite);
		listUnite= new HashMap<Unit,String>();
	}
	
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
	
	public void removeUnit(Unit unite)
	{
		listUnite.remove(unite);
		
	}
	public String getEnterDirection(Unit unite)
	{
	   return	listUnite.get(unite);
	}

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
