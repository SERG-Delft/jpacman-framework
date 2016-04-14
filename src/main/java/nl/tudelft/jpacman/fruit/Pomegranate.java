package nl.tudelft.jpacman.fruit;
import nl.tudelft.jpacman.sprite.Sprite;

public class Pomegranate extends Fruit
{

	public Pomegranate(int points, Sprite sprite) 
	{
		super(points, sprite);	
	}

	public boolean isClose(int abscisseUnit,int ordonneUnit,int abscisseNpc,int ordonneNpc)
	{
		int diffAbscisse=0;
		int diffOrdonne=0;
		
		if(abscisseUnit>abscisseNpc)
		{
			diffAbscisse=abscisseUnit-abscisseNpc;
		}else
		{
			diffAbscisse=abscisseNpc-abscisseUnit;
		}
		if(ordonneUnit>ordonneNpc)
		{
			diffOrdonne=ordonneUnit-ordonneNpc;
		}else
		{
			diffOrdonne=ordonneNpc-ordonneUnit;
		}
		
		if((diffAbscisse<=4)&&(diffOrdonne<=4))
		{
			return true;
		}else
		{
			return false;
		}
		
	}
	
	@Override
	public String effect()
	{
		return "Bomb !!!";
	}




}
