package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Direction;

public abstract class PoursuiteMode implements Strategy{

	Ghost g;
	
	public PoursuiteMode(Ghost g){
		this.g=g;
	}
	
	public abstract Direction nextMove();
	
}
