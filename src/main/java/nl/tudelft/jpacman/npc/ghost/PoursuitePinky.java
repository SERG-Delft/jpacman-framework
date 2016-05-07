package nl.tudelft.jpacman.npc.ghost;

import java.util.List;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.level.Player;

public class PoursuitePinky extends PoursuiteMode{


	
	private static final int SQUARES_AHEAD = 4;
	
	public PoursuitePinky(Ghost g){
		super(g);
	}
	
	public Direction nextMove() {
		Unit player = Navigation.findNearest(Player.class, g.getSquare());
		if (player == null) {
			Direction d = g.randomMove();
			return d;
		}

		Direction targetDirection = player.getDirection();
		Square destination = player.getSquare();
		for (int i = 0; i < SQUARES_AHEAD; i++) {
			destination = destination.getSquareAt(targetDirection);
		}

		List<Direction> path = Navigation.shortestPath(g.getSquare(),
				destination, g);
		if (path != null && !path.isEmpty()) {
			Direction d = path.get(0);
			return d;
		}
		Direction d = g.randomMove();
		return d;
	}
}
