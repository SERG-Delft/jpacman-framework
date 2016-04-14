package nl.tudelft.jpacman.fruit;

import nl.tudelft.jpacman.sprite.Sprite;

public class Potato extends Fruit
{

	public Potato(int points, Sprite sprite)
	{
		super(points, sprite);
	}
	
	@Override
	public String effect()
	{
		return "Boost Ghosts speed";
	}

}
