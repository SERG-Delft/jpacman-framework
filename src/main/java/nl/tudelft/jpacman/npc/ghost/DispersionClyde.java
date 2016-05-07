package nl.tudelft.jpacman.npc.ghost;

import java.util.List;

import nl.tudelft.jpacman.board.Direction;

public class DispersionClyde extends DispersionMode{

	Ghost g;
	
	public DispersionClyde(Ghost g){
		super(g);
	}
	public Direction nextMove(){
			return super.nextMove();
	}
	
}
