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
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.npc.ghost.GhostColor;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.ui.Action;
import nl.tudelft.jpacman.ui.PacManUI;
import nl.tudelft.jpacman.ui.PacManUiBuilder;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates and launches the JPacMan UI.
 * 
 * @author Jeroen Roosen 
 */
public class Launcher {
    /* TODO after merging
     * - change scorepanel to be built by an arraylist of Scorers (new interface to create) to draw score of AI in
     *   multiplayer
     * - Maybe rethink logic in LevelFactory (a Game should create his own levels not the opposite)
     */
	private static final PacManSprites SPRITE_STORE = new PacManSprites();
    public static final int CLASSIC = 1;
    public static final int MULTI_GHOST = 2;
	public static final int INFINITE_BOARD = 3;
	public static final int TWO_PLAYERS = 4;
    private static final int MENU = 0;

    private PacManUI pacManUI;
	private Game game;
	private boolean infinite = false;

	/**
	 * @return The game object this launcher will start when {@link #launch()}
	 *         is called.
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Creates a new game using the level from {@link #makeLevel(String source)}.
	 * 
	 * @return a new Game.
     * @param gameMode the gamemode to create
	 */
	public Game makeGame(int gameMode) {
        assert gameMode != MULTI_GHOST;
        return this.makeGame(gameMode, 1);
    }
    /**
     * Creates a new game using the level from {@link #makeLevel(String source)}.
     *
	 * @param gameMode the gamemode to create
	 * @param playerNumber the number of players in the game
     * @return a new Game.
     */
	public Game makeGame(int gameMode, int playerNumber) {
        if(game != null){
            game.stop();
        }
        infinite = false;
        GameFactory gf = getGameFactory();
        Level level;
        switch (gameMode){
            case MENU:
                level = makeLevel("/menu.txt");
                game = gf.makeMenu(level);
                break;
            case CLASSIC:
                level = makeLevel("/board.txt");
                game = gf.createSinglePlayerGame(level);
				pacManUI.setKeys(getSinglePlayerKeys(game));
                break;
            case MULTI_GHOST:
				// ask players color
				ArrayList<GhostColor> playerColors = new ArrayList<>();
                switch (playerNumber){
                    case 4:
                        playerColors.add(GhostColor.CYAN);
                    case 3:
                        playerColors.add(GhostColor.PINK);
                    case 2:
                        playerColors.add(GhostColor.ORANGE);
                    default:
                        playerColors.add(GhostColor.RED);
                }
				// create game
                level = makeLevel("/boardMultiGhost.txt");
				level.setNPCs(getLevelFactory().createGhosts(GhostColor.getOtherColors(playerColors)));
                game = gf.createMultiGhostPlayerGame(level, playerColors);
                pacManUI.setKeys(getMultiGhostPlayerKeys(game));
                break;
			case INFINITE_BOARD:
				infinite = true;
				level = makeLevel("/board_infinite.txt");
				game = gf.createSinglePlayerGame(level);
				pacManUI.setKeys(getSinglePlayerKeys(game));
                break;
			case TWO_PLAYERS:
				level = makeLevel("/board.txt");
				game = gf.createDoublePlayersGame(level);
				pacManUI.setKeys(addDoublePlayersKeys(game));
				
				break;
            default:
                game = null;
                break;
        }
        if(pacManUI != null){
            pacManUI.setGame(game);
        }
        return game;
    }

	/**
	 * Creates a new level. By default this method will use the map parser to
	 * parse the default board stored in the <code>board.txt</code> resource.
	 *
     * @param source the filename describing the level
	 * @return A new level.
	 */
	public Level makeLevel(String source) {
		MapParser parser = getMapParser();
		try (InputStream boardStream = Launcher.class
				.getResourceAsStream(source)) {
			return parser.parseMap(boardStream);
		} catch (IOException e) {
			throw new PacmanConfigurationException("Unable to create level.", e);
		}
	}

	/**
	 * @return A new map parser object using the factories from
	 * {@link #getLevelFactory()} and {@link #getBoardFactory()}.
	 */
	protected MapParser getMapParser() {
		return new MapParser(getLevelFactory(), getBoardFactory(), isInfinite());
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
     * @param game
     *            The game that will process the events.
     */
	protected Map<Integer, Action> getSinglePlayerKeys(final Game game) {
		final Player p1 = game.getPlayers().get(0);
        HashMap<Integer, Action> ret = new HashMap<>();

        ret.put(KeyEvent.VK_UP, new Action() {

            @Override
            public void doAction() {
                game.move(p1, Direction.NORTH);
            }
        });
        ret.put(KeyEvent.VK_DOWN, new Action() {

            @Override
            public void doAction() {
                game.move(p1, Direction.SOUTH);
            }
        });
        ret.put(KeyEvent.VK_LEFT, new Action() {

            @Override
            public void doAction() {
                game.move(p1, Direction.WEST);
            }
        });
        ret.put(KeyEvent.VK_RIGHT, new Action() {

            @Override
            public void doAction() {
                game.move(p1, Direction.EAST);
            }
        });
        return ret;

	}

	/**
	 * Adds key events UP, DOWN, LEFT and RIGHT to a game.
	 *
	 * @param game
	 *            The game that will process the events.
	 */
	protected HashMap<Integer,Action> getMultiGhostPlayerKeys(final Game game) {
		final List<Player> players = game.getPlayers();
        HashMap<Integer,Action> ret = new HashMap<>();

		switch (players.size()){
            case 4:
                ret.put(KeyEvent.VK_G, () -> game.move(players.get(3), Direction.NORTH)); //P4 gets GVBN
                ret.put(KeyEvent.VK_B, () -> game.move(players.get(3), Direction.SOUTH));
                ret.put(KeyEvent.VK_V, () -> game.move(players.get(3), Direction.WEST));
                ret.put(KeyEvent.VK_N, () -> game.move(players.get(3), Direction.EAST));
			case 3:
                ret.put(KeyEvent.VK_O, () -> game.move(players.get(2), Direction.NORTH)); //P3 gets OKLM
                ret.put(KeyEvent.VK_L, () -> game.move(players.get(2), Direction.SOUTH));
                ret.put(KeyEvent.VK_K, () -> game.move(players.get(2), Direction.WEST));
                ret.put(KeyEvent.VK_M, () -> game.move(players.get(2), Direction.EAST));
			case 2:
                ret.put(KeyEvent.VK_Z, () -> game.move(players.get(1), Direction.NORTH)); //P2 gets ZQSD
                ret.put(KeyEvent.VK_S, () -> game.move(players.get(1), Direction.SOUTH));
                ret.put(KeyEvent.VK_Q, () -> game.move(players.get(1), Direction.WEST));
                ret.put(KeyEvent.VK_D, () -> game.move(players.get(1), Direction.EAST));
			default:
                ret.put(KeyEvent.VK_UP, () -> game.move(players.get(0), Direction.NORTH)); // P1 gets UpDownLeftRight
                ret.put(KeyEvent.VK_DOWN, () -> game.move(players.get(0), Direction.SOUTH));
                ret.put(KeyEvent.VK_LEFT, () -> game.move(players.get(0), Direction.WEST));
                ret.put(KeyEvent.VK_RIGHT, () -> game.move(players.get(0), Direction.EAST));
				break;
		}
        return ret;

	}

	/**
	 * Creates and starts a JPac-Man game.
	 */
	public void launch() {
        game = makeGame(MENU);
        PacManUiBuilder builder = new PacManUiBuilder().withAdvancedButtons();
        pacManUI = builder.build(this);
        pacManUI.setGame(game);
        game.start();
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

	public boolean isInfinite() {
		return infinite;
	}

	/* !!!!!!!!!!! NEW FUNCTIONALITY : CONTINUOUS MOVE  !!!!!!!!!!! !! */

	protected Direction directionPlayer;


	/**
	 * Adds key events UP, DOWN, LEFT and RIGHT to a game for continuous move.
	 *
	 * @param builder
	 *            The {@link PacManUiBuilder} that will provide the UI.
	 * @param game
	 *            The game that will process the events.
	 */
	protected void addSinglePlayerKeysContinuousMove(final PacManUiBuilder builder,
													 final Game game) {
		final Player p1 = getSinglePlayer(game);

		ScheduledExecutorService service = Executors
				.newSingleThreadScheduledExecutor();

		service.schedule(new PlayerMoveTask(p1, service),
				200, TimeUnit.MILLISECONDS);

		res.put(KeyEvent.VK_UP, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.NORTH, p1))
					directionPlayer = Direction.NORTH;
			}
		});
res.put(KeyEvent.VK_DOWN, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.SOUTH, p1))
					directionPlayer = Direction.SOUTH;
			}
		});
res.put(KeyEvent.VK_LEFT, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.WEST, p1))
					directionPlayer = Direction.WEST;
			}
		});
res.put(KeyEvent.VK_RIGHT, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.EAST, p1))
					directionPlayer = Direction.EAST;
			}
		});

	}

	/**
	 * A task that moves an player and reschedules itself after it finished.
	 *
	 */
	 private final class PlayerMoveTask implements Runnable{


		private final Player player;

		/**
		 * The service executing the task.
		 */
		private final ScheduledExecutorService service;

		/**
		 * Creates a new task.
		 *
		 * @param service
		 *            The service that executes the task.
		 * @param player
		 *            The player to move.
		 */
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

	/**
	 * A task that moves an ghostPlayer and reschedules itself after it finished.
	 *
	 */
	private final class GhostPlayerMoveTask implements Runnable{

		private final  GhostPlayer gp;

		/**
		 * The service executing the task.
		 */
		private final ScheduledExecutorService service;

		/**
		 * Creates a new task.
		 *
		 * @param service
		 *            The service that executes the task.
		 * @param gp
		 *            The ghostplayer to move.
		 */
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


	/**
	 * Adds key events UP, DOWN, LEFT and RIGHT to a double players game with continuous move (for the player).
	 * Adds key events Q,S,D,Z to a double players game with continuous (for the ghostplayer)
	 *
	 * @param builder
	 *            The {@link PacManUiBuilder} that will provide the UI.
	 * @param game
	 *            The game that will process the events.
	 */
	protected HashMap<Integer, Action> getDoublePlayersKeys(final Game game){

		final Player p = game.getPlayers().get(1);
		final HashMap<Integer, Action> res = new HashMap<>();
		final GhostPlayer gp = (GhostPlayer)game.getPlayers().get(0);

		ScheduledExecutorService service1 = Executors
				.newSingleThreadScheduledExecutor();

		ScheduledExecutorService service2 = Executors
				.newSingleThreadScheduledExecutor();

		service1.schedule(new PlayerMoveTask(p, service1),
				200, TimeUnit.MILLISECONDS);

		service2.schedule(new GhostPlayerMoveTask(gp, service2),
				200, TimeUnit.MILLISECONDS);

		res.put(KeyEvent.VK_UP, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.NORTH, p))
					directionPlayer = Direction.NORTH;
			}
		});
res.put(KeyEvent.VK_DOWN, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.SOUTH, p))
					directionPlayer = Direction.SOUTH;
			}
		});
res.put(KeyEvent.VK_LEFT, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.WEST, p))
					directionPlayer = Direction.WEST;
			}
		});
res.put(KeyEvent.VK_RIGHT, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.EAST, p))
					directionPlayer = Direction.EAST;
			}
		});


		res.put(KeyEvent.VK_Z, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.NORTH, gp))
					directionGhostPlayer = Direction.NORTH;
			}
		});
res.put(KeyEvent.VK_S, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.SOUTH, gp))
					directionGhostPlayer = Direction.SOUTH;
			}
		});
res.put(KeyEvent.VK_Q, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.WEST, gp))
					directionGhostPlayer = Direction.WEST;
			}
		});
res.put(KeyEvent.VK_D, new Action() {

			@Override
			public void doAction() {
				if(isAccessible(Direction.EAST, gp))
					directionGhostPlayer = Direction.EAST;
			}
		});
	}
}
