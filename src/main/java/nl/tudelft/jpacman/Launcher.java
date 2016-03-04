package nl.tudelft.jpacman;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.ui.Action;
import nl.tudelft.jpacman.ui.PacManUI;
import nl.tudelft.jpacman.ui.PacManUiBuilder;

/**
 * Creates and launches the JPacMan UI.
 * 
 * @author Jeroen Roosen 
 */
public class Launcher {

	private static final PacManSprites SPRITE_STORE = new PacManSprites();

	private PacManUI pacManUI;
	private Game game;

	/**
	 * @return The game object this launcher will start when {@link #launch(boolean)}
	 *         is called.
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Creates a new game using the level from {@link #makeLevel(boolean)}.
	 *
	 * @param infinite
	 * 				true if the level to create is an {@link InfiniteLevel}
	 * @return a new Game.
	 */
	public Game makeGame(boolean infinite) {
		GameFactory gf = getGameFactory();
		Level level = makeLevel(infinite);
		return gf.createSinglePlayerGame(level);
	}

	/**
	 * Creates a new level. By default this method will use the map parser to
	 * parse the default board stored in the <code>board.txt</code> resource.
	 *
	 * @param infinite
	 * 				true if the level to create is an {@link InfiniteLevel}
	 * @return A new level.
	 */
	public Level makeLevel(boolean infinite) {
		MapParser parser = getMapParser();
        String mapName = "/board.txt";
        if(infinite) mapName = "/board_infinite";
		try (InputStream boardStream = Launcher.class
				.getResourceAsStream(mapName)) {
			return parser.parseMap(boardStream, infinite);
		} catch (IOException e) {
			throw new PacmanConfigurationException("Unable to create level.", e);
		}
	}

	/**
	 * @return A new map parser object using the factories from
	 *         {@link #getLevelFactory()} and {@link #getBoardFactory()}.
	 */
	protected MapParser getMapParser() {
		return new MapParser(getLevelFactory(), getBoardFactory());
	}

	/**
	 * @return A new board factory using the sprite store from
	 *         {@link #getSpriteStore()}.
	 */
	protected BoardFactory getBoardFactory() {
		return new BoardFactory(getSpriteStore());
	}

	/**
	 * @return The default {@link PacManSprites}.
	 */
	protected PacManSprites getSpriteStore() {
		return SPRITE_STORE;
	}

	/**
	 * @return A new factory using the sprites from {@link #getSpriteStore()}
	 *         and the ghosts from {@link #getGhostFactory()}.
	 */
	protected LevelFactory getLevelFactory() {
		return new LevelFactory(getSpriteStore(), getGhostFactory());
	}

	/**
	 * @return A new factory using the sprites from {@link #getSpriteStore()}.
	 */
	protected GhostFactory getGhostFactory() {
		return new GhostFactory(getSpriteStore());
	}

	/**
	 * @return A new factory using the players from {@link #getPlayerFactory()}.
	 */
	protected GameFactory getGameFactory() {
		return new GameFactory(getPlayerFactory());
	}

	/**
	 * @return A new factory using the sprites from {@link #getSpriteStore()}.
	 */
	protected PlayerFactory getPlayerFactory() {
		return new PlayerFactory(getSpriteStore());
	}

	/**
	 * Adds key events UP, DOWN, LEFT and RIGHT to a game.
	 * 
	 * @param builder
	 *            The {@link PacManUiBuilder} that will provide the UI.
	 * @param game
	 *            The game that will process the events.
	 */
	protected void addSinglePlayerKeys(final PacManUiBuilder builder,
			final Game game) {
		final Player p1 = getSinglePlayer(game);

		builder.addKey(KeyEvent.VK_UP, new Action() {

			@Override
			public void doAction() {
				game.move(p1, Direction.NORTH);
			}
		}).addKey(KeyEvent.VK_DOWN, new Action() {

			@Override
			public void doAction() {
				game.move(p1, Direction.SOUTH);
			}
		}).addKey(KeyEvent.VK_LEFT, new Action() {

			@Override
			public void doAction() {
				game.move(p1, Direction.WEST);
			}
		}).addKey(KeyEvent.VK_RIGHT, new Action() {

			@Override
			public void doAction() {
				game.move(p1, Direction.EAST);
			}
		});

	}

	private Player getSinglePlayer(final Game game) {
		List<Player> players = game.getPlayers();
		if (players.isEmpty()) {
			throw new IllegalArgumentException("Game has 0 players.");
		}
		final Player p1 = players.get(0);
		return p1;
	}

	/**
	 * Creates and starts a JPac-Man game.
	 * @param infinite
	 * 				true if the board to create is an {@link nl.tudelft.jpacman.board.InfiniteBoard}
	 */
	public void launch(boolean infinite) {
		game = makeGame(infinite);
		PacManUiBuilder builder = new PacManUiBuilder().withDefaultButtons();
		addSinglePlayerKeys(builder, game);
		pacManUI = builder.build(game);
		pacManUI.start();
	}

	/**
	 * Disposes of the UI. For more information see {@link javax.swing.JFrame#dispose()}.
	 */
	public void dispose() {
		pacManUI.dispose();
	}

	/**
	 * Main execution method for the Launcher.
	 * 
	 * @param args
	 *            The command line arguments - which are ignored.
	 * @throws IOException
	 *             When a resource could not be read.
	 */
	public static void main(String[] args) throws IOException {
		new Launcher().launch(true);
//		new Launcher().launchInfinite();
	}
}
