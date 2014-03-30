package nl.tudelft.jpacman.level;

import java.util.List;
import java.util.Map;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.npc.ghost.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostColor;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * Factory that creates levels and units.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public class LevelFactory {

	/**
	 * The default value of a pellet.
	 */
	private static final int PELLET_VALUE = 10;

	/**
	 * The sprite store that provides sprites for units.
	 */
	private final PacManSprites sprites;

	/**
	 * Used to cycle through the various ghost types.
	 */
	private int ghostIndex;

	/**
	 * The factory providing ghosts.
	 */
	private final GhostFactory ghostFact;

	/**
	 * Creates a new level factory.
	 * 
	 * @param spriteStore
	 *            The sprite store providing the sprites for units.
	 * @param ghostFactory
	 *            The factory providing ghosts.
	 */
	public LevelFactory(PacManSprites spriteStore, GhostFactory ghostFactory) {
		this.sprites = spriteStore;
		this.ghostIndex = -1;
		this.ghostFact = ghostFactory;
	}

	/**
	 * Creates a new level from the provided data.
	 * 
	 * @param board
	 *            The board with all ghosts and pellets occupying their squares.
	 * @param ghosts
	 *            A list of all ghosts on the board.
	 * @param startPositions
	 *            A list of squares from which players may start the game.
	 * @return A new level for the board.
	 */
	public Level createLevel(Board board, List<NPC> ghosts,
			List<Square> startPositions) {

		// We'll adopt the simple collision map for now.
		CollisionMap collisionMap = new PlayerCollisions();
		
		return new Level(board, ghosts, startPositions, collisionMap);
	}

	/**
	 * Creates a new ghost.
	 * 
	 * @return The new ghost.
	 */
	NPC createGhost() {
		ghostIndex++;
		ghostIndex %= 4;
		switch (ghostIndex) {
		case 0:
			return ghostFact.createBlinky();
		case 1:
			return ghostFact.createInky();
		case 2:
			return ghostFact.createPinky();
		case 3:
			return ghostFact.createClyde();
		default:
			return new RandomGhost(sprites.getGhostSprite(GhostColor.RED));
		}
	}

	/**
	 * Creates a new pellet.
	 * 
	 * @return The new pellet.
	 */
	public Pellet createPellet() {
		return new Pellet(PELLET_VALUE, sprites.getPelletSprite());
	}

	/**
	 * Implementation of an NPC that wanders around randomly.
	 * 
	 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
	 */
	private class RandomGhost extends Ghost {

		/**
		 * The suggested delay between moves.
		 */
		private static final long DELAY = 175L;

		/**
		 * Creates a new random ghost.
		 * 
		 * @param ghostSprite
		 *            The sprite for the ghost.
		 */
		private RandomGhost(Map<Direction, Sprite> ghostSprite) {
			super(ghostSprite);
		}

		@Override
		public long getInterval() {
			return DELAY;
		}

		@Override
		public Direction nextMove() {
			return randomMove();
		}
	}
}
