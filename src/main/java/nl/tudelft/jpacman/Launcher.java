package nl.tudelft.jpacman;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.ui.Action;
import nl.tudelft.jpacman.ui.PacManUI;
import nl.tudelft.jpacman.ui.PacManUiBuilder;

/**
 * Creates and launches the JPacMan UI.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public class Launcher {

	private static final PacManSprites SPRITE_STORE = new PacManSprites();

	private PacManUI pacManUI;
	private Game game;

	public Game getGame() {
		return game;
	}

	public Game makeGame() {
		GameFactory gf = getGameFactory();
		Level level = makeLevel();
		return gf.createSinglePlayerGame(level);
	}

	protected Level makeLevel() {
		MapParser parser = getMapParser();
		try {
			return parser.parseMap(Launcher.class.getResourceAsStream("/board.txt"));
		} catch (IOException e) {
			throw new RuntimeException("Unable to create level.", e);
		}
	}

	protected MapParser getMapParser() {
		return new MapParser(getLevelFactory(), getBoardFactory());
	}

	protected BoardFactory getBoardFactory() {
		return new BoardFactory(getSpriteStore());
	}

	protected PacManSprites getSpriteStore() {
		return SPRITE_STORE;
	}

	protected LevelFactory getLevelFactory() {
		return new LevelFactory(getSpriteStore(), getGhostFactory());
	}

	protected GhostFactory getGhostFactory() {
		return new GhostFactory(getSpriteStore());
	}

	protected GameFactory getGameFactory() {
		return new GameFactory(getPlayerFactory());
	}

	protected PlayerFactory getPlayerFactory() {
		return new PlayerFactory(getSpriteStore());
	}

	protected void addSinglePlayerKeys(final PacManUiBuilder builder,
			final Game game) {
		List<Player> players = game.getPlayers();
		if (players.isEmpty()) {
			throw new IllegalArgumentException("Game has 0 players.");
		}
		final Player p1 = players.get(0);

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

	public void launch() {
		game = makeGame();
		PacManUiBuilder builder = new PacManUiBuilder().withDefaultButtons();
		addSinglePlayerKeys(builder, game);
		pacManUI = builder.build(game);
		pacManUI.start();
	}
	
	public void dispose() {
		pacManUI.dispose();
	}

	public static void main(String[] args) throws IOException {
		new Launcher().launch();
	}
}
