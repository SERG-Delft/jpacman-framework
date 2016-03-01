package nl.tudelft.jpacman;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
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
	 * @return The game object this launcher will start when {@link #launch()}
	 *         is called.
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Creates a new game using the level from {@link #makeLevel()}.
	 * 
	 * @return a new Game.
	 */
	public Game makeGame() {
		GameFactory gf = getGameFactory();
		Level level = makeLevel();
		return gf.createDoublePlayersGame(level);
		//return gf.createSinglePlayerGame(level);
	}

	/**
	 * Creates a new level. By default this method will use the map parser to
	 * parse the default board stored in the <code>board.txt</code> resource.
	 * 
	 * @return A new level.
	 */
	public Level makeLevel() {
		MapParser parser = getMapParser();
		try (InputStream boardStream = Launcher.class
				.getResourceAsStream("/board.txt")) {
			return parser.parseMap(boardStream);
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
	 */
	public void launch() {
		game = makeGame();
		PacManUiBuilder builder = new PacManUiBuilder().withDefaultButtons();
		//addSinglePlayerKeys(builder, game);
		//addSinglePlayerKeysContinuousMove(builder, game);
		addDoublePlayersKeys(builder, game);
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
		new Launcher().launch();
	}

	/* !!!!!!!!!!! NEW FUNCTIONALITY : CONTINUOUS MOVE  !!!!!!!!!!! !! */

	protected Direction directionPlayer;

	protected void addSinglePlayerKeysContinuousMove(final PacManUiBuilder builder,
													 final Game game) {
		final Player p1 = getSinglePlayer(game);

		ScheduledExecutorService service = Executors
				.newSingleThreadScheduledExecutor();

		service.schedule(new PlayerMoveTask(p1, service),
				200, TimeUnit.MILLISECONDS);

		builder.addKey(KeyEvent.VK_UP, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.NORTH, p1))
					directionPlayer = Direction.NORTH;
			}
		}).addKey(KeyEvent.VK_DOWN, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.SOUTH, p1))
					directionPlayer = Direction.SOUTH;
			}
		}).addKey(KeyEvent.VK_LEFT, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.WEST, p1))
					directionPlayer = Direction.WEST;
			}
		}).addKey(KeyEvent.VK_RIGHT, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.EAST, p1))
					directionPlayer = Direction.EAST;
			}
		});

	}

	private final class PlayerMoveTask implements Runnable{

		private final Player player;

		private final ScheduledExecutorService service;

		private PlayerMoveTask(Player player, ScheduledExecutorService service) {
			this.player = player;
			this.service = service;
		}

		@Override
		public void run() {
			Direction nextMove = directionPlayer;
			if(nextMove != null)
				game.move(player, nextMove);
			service.schedule(this, 200, TimeUnit.MILLISECONDS);
		}
	}

	private final class GhostPlayerMoveTask implements Runnable{

		private final  GhostPlayer gp;

		private final ScheduledExecutorService service;

		private GhostPlayerMoveTask(GhostPlayer gp, ScheduledExecutorService service) {
			this.gp = gp;
			this.service = service;
		}

		@Override
		public void run() {
			Direction nextMove = directionGhostPlayer;
			if(nextMove != null)
				game.move(gp, nextMove);
			service.schedule(this, 200, TimeUnit.MILLISECONDS);
		}
	}

	boolean isAccessible(Direction direction, Player player){
		Square square = player.getSquare();
		List<Direction> directions = new ArrayList<>();
		for (Direction d : Direction.values()) {
			if (square.getSquareAt(d).isAccessibleTo(getSinglePlayer(game))) {
				directions.add(d);
			}
		}
		if (direction != null && directions.contains(direction)) {
			return true;
		}
		else{
			return false;
		}
	}

	protected Direction directionGhostPlayer;

	protected void addDoublePlayersKeys(final PacManUiBuilder builder,
									   final Game game){

		final Player p = game.getPlayers().get(0);
		final GhostPlayer gp = (GhostPlayer)game.getPlayers().get(1);

		ScheduledExecutorService service1 = Executors
				.newSingleThreadScheduledExecutor();

		ScheduledExecutorService service2 = Executors
				.newSingleThreadScheduledExecutor();

		service1.schedule(new PlayerMoveTask(p, service1),
				200, TimeUnit.MILLISECONDS);

		service2.schedule(new GhostPlayerMoveTask(gp, service2),
				200, TimeUnit.MILLISECONDS);

		builder.addKey(KeyEvent.VK_UP, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.NORTH, p))
					directionPlayer = Direction.NORTH;
			}
		}).addKey(KeyEvent.VK_DOWN, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.SOUTH, p))
					directionPlayer = Direction.SOUTH;
			}
		}).addKey(KeyEvent.VK_LEFT, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.WEST, p))
					directionPlayer = Direction.WEST;
			}
		}).addKey(KeyEvent.VK_RIGHT, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.EAST, p))
					directionPlayer = Direction.EAST;
			}
		});


		builder.addKey(KeyEvent.VK_Z, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.NORTH, gp))
					directionGhostPlayer = Direction.NORTH;
			}
		}).addKey(KeyEvent.VK_S, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.SOUTH, gp))
					directionGhostPlayer = Direction.SOUTH;
			}
		}).addKey(KeyEvent.VK_Q, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.WEST, gp))
					directionGhostPlayer = Direction.WEST;
			}
		}).addKey(KeyEvent.VK_D, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.EAST, gp))
					directionGhostPlayer = Direction.EAST;
			}
		});
	}
}
