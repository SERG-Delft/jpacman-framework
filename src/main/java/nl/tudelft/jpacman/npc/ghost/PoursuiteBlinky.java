package nl.tudelft.jpacman.npc.ghost;

import java.util.List;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.Player;

public class PoursuiteBlinky extends PoursuiteMode{


	
	public PoursuiteBlinky(Ghost g){
		super(g);
	}
	
	public Direction nextMove() {
		Square target = Navigation.findNearest(Player.class, g.getSquare())
				.getSquare();

		if (target == null) {
			Direction d = g.randomMove();
			return d;
		}
		
		List<Direction> path = Navigation.shortestPath(g.getSquare(), target,
				g);
		if (path != null && !path.isEmpty()) {
			Direction d = path.get(0);
			return d;
		}
		Direction d = g.randomMove();
		return d;
	}
}
