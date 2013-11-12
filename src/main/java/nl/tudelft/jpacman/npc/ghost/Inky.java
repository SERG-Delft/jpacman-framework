package nl.tudelft.jpacman.npc.ghost;

import java.util.List;
import java.util.Random;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.GhostColor;
import nl.tudelft.jpacman.sprite.PacManSprites;

/**
 * <p>
 * An implementation of the classic Pac-Man ghost Bashful.
 * </p>
 * <p>
 * Nickname: Inky. Bashful has truly earned his name. In a game of chicken
 * between Pac-Man and Bashful, Bashful might just run away. This isn't always
 * the case, but if Blinky is right behind you, it's a safe bet. He can't be
 * scared off while he patrols the southeast portion of the maze. In Japan, his
 * name is Kimagure/Aosuke.
 * </p>
 * <p>
 * <b>AI:</b> Bashful has the most complicated AI of all. When the ghosts are
 * not patrolling their home corners, Bashful considers two things: Shadow's
 * location, and the location two grid spaces ahead of Pac-Man. Bashful draws a
 * line from Shadow to the spot that is two squares in front of Pac-Man and
 * extends that line twice as far. Therefore, if Bashful is alongside Shadow
 * when they are behind Pac-Man, Bashful will usually follow Shadow the whole
 * time. But if Bashful is in front of Pac-Man when Shadow is far behind him,
 * Bashful tends to want to move away from Pac-Man (in reality, to a point very
 * far ahead of Pac-Man). Bashful is affected by a similar targeting bug that
 * affects Speedy. When Pac-Man is moving or facing up, the spot Bashful uses to
 * draw the line is two squares above and left of Pac-Man.
 * </p>
 * <p>
 * Source: http://strategywiki.org/wiki/Pac-Man/Getting_Started
 * </p>
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 * 
 */
public class Inky extends Ghost {

	public Inky(PacManSprites spriteStore) {
		super(spriteStore.getGhostSprite(GhostColor.CYAN));
	}

	@Override
	public long getInterval() {
		// TODO Auto-generated method stub
		return 175 + new Random().nextInt(50);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Bashful has the most complicated AI of all. When the ghosts are not
	 * patrolling their home corners, Bashful considers two things: Shadow's
	 * location, and the location two grid spaces ahead of Pac-Man. Bashful
	 * draws a line from Shadow to the spot that is two squares in front of
	 * Pac-Man and extends that line twice as far. Therefore, if Bashful is
	 * alongside Shadow when they are behind Pac-Man, Bashful will usually
	 * follow Shadow the whole time. But if Bashful is in front of Pac-Man when
	 * Shadow is far behind him, Bashful tends to want to move away from Pac-Man
	 * (in reality, to a point very far ahead of Pac-Man). Bashful is affected
	 * by a similar targeting bug that affects Speedy. When Pac-Man is moving or
	 * facing up, the spot Bashful uses to draw the line is two squares above
	 * and left of Pac-Man.
	 * </p>
	 * 
	 * <p>
	 * <b>Implementation:</b> by lack of a coordinate system there is a
	 * workaround: first determine the square of Blinky (A) and the square 2
	 * squares away from Pac-Man (B). Then determine the shortest path from A to
	 * B regardless of terrain and walk that same path from B. This is the
	 * destination.
	 * </p>
	 */
	@Override
	public Direction nextMove() {
		Unit blinky = Navigation.findNearest(Blinky.class, getSquare());
		if (blinky == null) {
			return null;
		}

		Unit player = Navigation.findNearest(Player.class, getSquare());
		if (player == null) {
			return null;
		}

		Direction targetDirection = player.getDirection();
		Square playerDestination = player.getSquare();
		for (int i = 0; i < 2; i++) {
			playerDestination = playerDestination.getSquareAt(targetDirection);
		}

		Square destination = playerDestination;
		List<Direction> path = Navigation.shortestPath(blinky.getSquare(),
				playerDestination, null);
		if (path == null) {
			return null;
		}
		for (Direction d : path) {
			destination = playerDestination.getSquareAt(d);
		}

		List<Direction> route = Navigation.shortestPath(getSquare(),
				destination, this);
		if (route != null && !route.isEmpty()) {
			return route.get(0);
		}
		return null;
	}

}
