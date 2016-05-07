package nl.tudelft.jpacman.npc.ghost;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.Player;

public class PoursuiteClyde extends PoursuiteMode{

	
	
	private static final int SHYNESS = 8;
	
	
	private static final Map<Direction, Direction> OPPOSITES = new EnumMap<Direction, Direction>(
			Direction.class);
	{
		OPPOSITES.put(Direction.NORTH, Direction.SOUTH);
		OPPOSITES.put(Direction.SOUTH, Direction.NORTH);
		OPPOSITES.put(Direction.WEST, Direction.EAST);
		OPPOSITES.put(Direction.EAST, Direction.WEST);
	}
	
	public PoursuiteClyde(Ghost g){
		super(g);
	}
	public Direction nextMove() {
		Square target = Navigation.findNearest(Player.class, g.getSquare())
				.getSquare();
		if (target == null) {
			return g.randomMove();
		}

		List<Direction> path = Navigation.shortestPath(g.getSquare(), target,
				g);
		if (path != null && !path.isEmpty()) {
			Direction d = path.get(0);
			if (path.size() <= SHYNESS) {
				Direction oppositeDir = OPPOSITES.get(d);
				return oppositeDir;
			}
			return d;
		}
		Direction d = g.randomMove();
		return d;
	}
}
