package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.Direction;

public class DispersionBlinky extends DispersionMode{

	Ghost g;
	
	public DispersionBlinky(Ghost g){
		super(g);
	}
	
	public Direction nextMove(){
		return super.nextMove();
	}
	

}