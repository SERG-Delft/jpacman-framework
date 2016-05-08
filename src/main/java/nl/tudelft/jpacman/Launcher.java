package nl.tudelft.jpacman;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.Game.GameObserver;
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
 * @author Jeroen Roosen 
 */
public class Launcher implements GameObserver{

	private static final PacManSprites SPRITE_STORE = new PacManSprites();

	private PacManUI pacManUI;
	private Game game;
	private String playerName;

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
		if(hasMultipleLevels()){
			Level[] lvls = makeLevels();
			return gf.createSinglePlayerGame(lvls);
		}
		Level level = makeLevel();
		return gf.createSinglePlayerGame(level);
	}
	
	/**
	 * Return files of the different levels
	 * 
	 * @return Array of level files
	 */
	private File[] getLevelFiles(){
		File[] files = new File(Launcher.class.getResource("/").getPath())
		.listFiles(new FilenameFilter(){

			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith("board") && name.endsWith(".txt");
			}
			
		});
		return files;
	}
	
	/**
	 * Return filenames of the different levels in order
	 * 
	 * @return Array of names of the files containing levels
	 */
	private String[] getLevelNames(){
		File[] files = getLevelFiles();
		ArrayList<String> filenames = new ArrayList<String>();
		for(File f: files){
			filenames.add(f.getName());
		}
		Collections.sort(filenames);
		return filenames.toArray(new String[filenames.size()]);
		
	}

	/**
	 * Determines if more than a single level can be loaded
	 * 
	 * @return true iff multiple levels are accessible in ressources in files
	 * board*.txt
	 */
	private boolean hasMultipleLevels() {
		return getLevelFiles().length >= 2;
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
	 * Load levels stored in board*.txt using the default parser
	 * 
	 * @return an Array on levels
	 */
	public Level[] makeLevels() {
		MapParser parser = getMapParser();
		String[] filenames = getLevelNames();
		Level[] lvls = new Level[filenames.length];
		for(int i = 0;i < filenames.length; i++){
			try (InputStream boardStream = Launcher.class
					.getResourceAsStream("/" + filenames[i])) {
				lvls[i] = parser.parseMap(boardStream);
			} catch (IOException e) {
				throw new PacmanConfigurationException("Unable to create level.", e);
			}
		}
		return lvls;
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
	public void launch(boolean askForName) {
		game = makeGame();
		game.addObserver(this);
		PacManUiBuilder builder;
		if(hasMultipleLevels()){
			builder = new PacManUiBuilder().withLevelsButtons();
		}else{
			builder = new PacManUiBuilder().withDefaultButtons();
		}
		addSinglePlayerKeys(builder, game);
		pacManUI = builder.build(game);
		pacManUI.start();
		if(askForName){
			askForPlayerName(pacManUI);
		}
	}

	/**
	 * Display a pop-up window to ask the player his name
	 * 
	 * @param UI The User Interface the game uses
	 */
	private void askForPlayerName(PacManUI UI) {
		String name = null;
		while(name == null || name == ""){
			name = JOptionPane.showInputDialog(UI,
					"Enter your player name","Player Name",
					JOptionPane.QUESTION_MESSAGE);
		}
		playerName = name;
		loadProgression(name);
	}

	/**
	 * Loads the progression of a player
	 * 
	 * @param name The name of the player
	 */
	private void loadProgression(String name) {
		File playerSave = new File(Launcher.class.getResource("/").getPath()
				+ "/" + name + ".save");
		if(playerSave.exists()){
			String line;
			try {
				BufferedReader br = new BufferedReader(new FileReader(playerSave));
				while((line = br.readLine()) != null){
					if(line.startsWith("max_level")){
						game.setPlayerLevel(Integer.parseInt(line.split("=")[1]));
					}
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Save the progression of the current player
	 * 
	 * @param i Highest level beaten
	 */
	private void saveProgression(int i) {
		if(playerName != null && playerName != ""){
			File playerSave = new File(Launcher.class.getResource("/").getPath()
					+ "/" + playerName + ".save");
			try {
				if(playerSave.exists()){
					String line;
					String fileResult = "";
					BufferedReader br = new BufferedReader(new FileReader(playerSave));
					while((line = br.readLine()) != null){
						if(line.startsWith("max_level")){
							fileResult += "max_level=" + i + "\n";
						}else{
							fileResult += line + "\n";
						}
					}
					br.close();
					PrintWriter pw = new PrintWriter(playerSave);
					pw.print(fileResult);
					pw.flush();
					pw.close();
				}else{
					PrintWriter pw = new PrintWriter(playerSave);
					pw.println("max_level=" + i);
					pw.flush();
					pw.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void playerWon(int i) {
		saveProgression(i);
	}

	@Override
	public void playerDied() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cantStart() {
		JOptionPane.showMessageDialog(pacManUI, "You haven't beated " +
				"the previous level\n" +
				"You are not allowed to play this level !");
		
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
	}
}
