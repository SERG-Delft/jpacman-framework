package nl.tudelft.jpacman;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import nl.tudelft.jpacman.npc.ghost.GhostColor;
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
    private static final int CLASSIC = 1;
    private static final int MULTI_GHOST = 2;

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
	 * Creates a new game using the level from {@link #makeLevel(String source)}.
	 * 
	 * @return a new Game.
     * @param gameMode the gamemode to create
	 */
	public Game makeGame(int gameMode) {
        GameFactory gf;
        Level level;
        switch (gameMode){
            case CLASSIC:
                gf = getGameFactory();
                level = makeLevel("/board.txt");
                return gf.createSinglePlayerGame(level);
            case MULTI_GHOST:
				// ask players color
				ArrayList<GhostColor> playerColors = new ArrayList<>();
                playerColors.add(GhostColor.RED);
                playerColors.add(GhostColor.ORANGE);
				// create game
                gf = getGameFactory();
                level = makeLevel("/boardMultiGhost.txt");
				level.setNPCs(getLevelFactory().createGhost(GhostColor.getOtherColors(playerColors)));
                return gf.createMultiGhostPlayerGame(level, playerColors); //TODO add number of player choice
            default:
                return null;
        }
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
	
	/**
	 * Adds key events UP, DOWN, LEFT and RIGHT to a game.
	 *
	 * @param builder
	 *            The {@link PacManUiBuilder} that will provide the UI.
	 * @param game
	 *            The game that will process the events.
	 */
	protected void addMultiGhostPlayerKeys(final PacManUiBuilder builder,
									   final Game game) {
		final List<Player> players = game.getPlayers();

		switch (players.size()){
            case 4:
                builder.addKey(KeyEvent.VK_UP, () -> game.move(players.get(3), Direction.NORTH)) // P4 gets UpDownLeftRight
                        .addKey(KeyEvent.VK_DOWN, () -> game.move(players.get(3), Direction.SOUTH))
                        .addKey(KeyEvent.VK_LEFT, () -> game.move(players.get(3), Direction.WEST))
                        .addKey(KeyEvent.VK_RIGHT, () -> game.move(players.get(3), Direction.EAST));
            case 3:
                builder.addKey(KeyEvent.VK_G, () -> game.move(players.get(2), Direction.NORTH)) //P3 gets GVBN
                        .addKey(KeyEvent.VK_B, () -> game.move(players.get(2), Direction.SOUTH))
                        .addKey(KeyEvent.VK_V, () -> game.move(players.get(2), Direction.WEST))
                        .addKey(KeyEvent.VK_N, () -> game.move(players.get(2), Direction.EAST));
			case 2:
                builder.addKey(KeyEvent.VK_Z, () -> game.move(players.get(0), Direction.NORTH)) //P1 gets ZQSD
                        .addKey(KeyEvent.VK_S, () -> game.move(players.get(0), Direction.SOUTH))
                        .addKey(KeyEvent.VK_Q, () -> game.move(players.get(0), Direction.WEST))
                        .addKey(KeyEvent.VK_D, () -> game.move(players.get(0), Direction.EAST));
                builder.addKey(KeyEvent.VK_O, () -> game.move(players.get(1), Direction.NORTH)) //P2 gets OKLM
                        .addKey(KeyEvent.VK_L, () -> game.move(players.get(1), Direction.SOUTH))
                        .addKey(KeyEvent.VK_K, () -> game.move(players.get(1), Direction.WEST))
                        .addKey(KeyEvent.VK_M, () -> game.move(players.get(1), Direction.EAST));
				break;
		}

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
        makeMultiGhostPlayer();
		//makeSinglePlayer();
	}

    private void makeSinglePlayer(){
        game = makeGame(CLASSIC);
        PacManUiBuilder builder = new PacManUiBuilder().withDefaultButtons();
        addSinglePlayerKeys(builder, game);
        pacManUI = builder.build(game);
        pacManUI.start();
    }

    private void makeMultiGhostPlayer(){
        game = makeGame(MULTI_GHOST);
        PacManUiBuilder builder = new PacManUiBuilder().withDefaultButtons();
        addMultiGhostPlayerKeys(builder, game);
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
}
