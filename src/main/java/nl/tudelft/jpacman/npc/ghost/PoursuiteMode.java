package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.sprite.Sprite;

public abstract class PoursuiteMode implements Strategy{

	Ghost g;
	
	public PoursuiteMode(Ghost g){
		this.g=g;
	}
	
	public abstract Direction nextMove();
	
}
