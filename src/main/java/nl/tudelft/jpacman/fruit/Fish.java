package nl.tudelft.jpacman.fruit;

import nl.tudelft.jpacman.sprite.Sprite;

public class Fish extends Fruit
{

	public Fish(int points, Sprite sprite) 
	{
		super(points, sprite);
	}
	
	
	@Override
	public String effect()
	{
		return "Freeze PacMan";
	}

}
