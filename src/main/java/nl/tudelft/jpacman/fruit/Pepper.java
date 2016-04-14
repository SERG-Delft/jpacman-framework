package nl.tudelft.jpacman.fruit;


import nl.tudelft.jpacman.sprite.Sprite;

public class Pepper extends Fruit
{
	

	public Pepper(int points, Sprite sprite)
	{
		super(points, sprite);	
	}
	
	@Override
	public String effect()
	{
		return "Boost PacMan Speed ";
	}
	

}
