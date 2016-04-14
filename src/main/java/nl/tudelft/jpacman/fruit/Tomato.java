package nl.tudelft.jpacman.fruit;

import nl.tudelft.jpacman.sprite.Sprite;

public class Tomato extends Fruit
{

	public Tomato(int points, Sprite sprite)
	{
		super(points, sprite);	
	}
	
	@Override
	public String effect()
	{
		return "Invisible Pacman";
	}

}
