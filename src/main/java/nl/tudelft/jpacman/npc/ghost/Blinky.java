package nl.tudelft.jpacman.npc.ghost;

import java.util.List;
import java.util.Random;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.GhostColor;
import nl.tudelft.jpacman.sprite.PacManSprites;

/**
 * <p>
 * An implementation of the classic Pac-Man ghost Shadow.
 * </p>
 * <p>
 * Nickname: Blinky. As his name implies, Shadow is usually a constant shadow on
 * Pac-Man's tail. When he's not patrolling the top-right corner of the maze,
 * Shadow tries to find the quickest route to Pac-Man's position. Despite the
 * fact that Pinky's real name is Speedy, Shadow is actually the fastest of the
 * ghosts because of when there are only a few pellets left, Blinky drastically
 * speeds up, which can make him quite deadly. In the original Japanese version,
 * his name is Oikake/Akabei.
 * </p>
 * <p>
 * <b>AI:</b> When the ghosts are not patrolling in their home corners (Blinky:
 * top-right, Pinky: top-left, Inky: bottom-right, Clyde: bottom-left), Blinky
 * will attempt to shorten the distance between Pac-Man and himself. If he has
 * to choose between shortening the horizontal or vertical distance, he will
 * choose to shorten whichever is greatest. For example, if Pac-Man is four grid
 * spaces to the left, and seven grid spaces above Blinky, he'll try to move up
 * towards Pac-Man before he moves to the left.
 * </p>
 * <p>
 * Source: http://strategywiki.org/wiki/Pac-Man/Getting_Started
 * </p>
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 * 
 */
public class Blinky extends Ghost {

	public Blinky(PacManSprites spriteStore) {
		super(spriteStore.getGhostSprite(GhostColor.RED));
	}

	@Override
	public long getInterval() {
		// TODO Blinky should speed up when there are a few pellets left, but he
		// has no way to find out how many there are.
		return 175 + new Random().nextInt(50);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * When the ghosts are not patrolling in their home corners (Blinky:
	 * top-right, Pinky: top-left, Inky: bottom-right, Clyde: bottom-left),
	 * Blinky will attempt to shorten the distance between Pac-Man and himself.
	 * If he has to choose between shortening the horizontal or vertical
	 * distance, he will choose to shorten whichever is greatest. For example,
	 * if Pac-Man is four grid spaces to the left, and seven grid spaces above
	 * Blinky, he'll try to move up towards Pac-Man before he moves to the left.
	 * </p>
	 */
	@Override
	public Direction nextMove() {
		// TODO Blinky should patrol his corner every once in a while
		// TODO Implement his actual behaviour instead of simply chasing.

		Square target = Navigation.findNearest(Player.class, getSquare())
				.getSquare();

		if (target == null) {
			return null;
		}

		List<Direction> path = Navigation.shortestPath(getSquare(), target,
				this);
		if (path != null && !path.isEmpty()) {
			return path.get(0);
		}
		return null;
	}
}
