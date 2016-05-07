package nl.tudelft.jpacman.npc.ghost;

import java.util.List;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.level.Player;

public class PoursuiteInky extends PoursuiteMode{


	private static final int SQUARES_AHEAD = 2;
	
	public PoursuiteInky(Ghost g){
			super(g);
	}
	
	public Direction nextMove() {
		Unit blinky = Navigation.findNearest(Blinky.class, g.getSquare());
		if (blinky == null) {
			Direction d = g.randomMove();
			return d;
		}

		Unit player = Navigation.findNearest(Player.class, g.getSquare());
		if (player == null) {
			Direction d = g.randomMove();
			return d;
		}

		Direction targetDirection = player.getDirection();
		Square playerDestination = player.getSquare();
		for (int i = 0; i < SQUARES_AHEAD; i++) {
			playerDestination = playerDestination.getSquareAt(targetDirection);
		}

		Square destination = playerDestination;
		List<Direction> firstHalf = Navigation.shortestPath(blinky.getSquare(),
				playerDestination, null);
		if (firstHalf == null) {
			Direction d = g.randomMove();
			return d;
		}

		for (Direction d : firstHalf) {
			destination = playerDestination.getSquareAt(d);
		}

		List<Direction> path = Navigation.shortestPath(g.getSquare(),
				destination, g);
		if (path != null && !path.isEmpty()) {
			Direction d = path.get(0);
			//System.out.println(d);
			return d;
		}
		Direction d = g.randomMove();
		return d;
	}
}
