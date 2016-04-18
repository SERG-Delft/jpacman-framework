package nl.tudelft.jpacman.game;

import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;

import com.google.common.collect.ImmutableList;

/**
 * A game with one player and a single level.
 * 
 * @author Jeroen Roosen 
 */
public class SinglePlayerGame extends Game {

	/**
	 * The player of this game.
	 */
	private final Player player;

	/**
	 * The level of this game.
	 */
	private Level level;
	
	private Level[] levels;
	
	private int levelIndex;

	/**
	 * Create a new single player game for the provided level and player.
	 * 
	 * @param p
	 *            The player.
	 * @param l
	 *            The level.
	 */
	protected SinglePlayerGame(Player p, Level l) {
		assert p != null;
		assert l != null;

		this.player = p;
		this.level = l;
		level.registerPlayer(p);
	}
	
	/**
	 * Create a new single player game for the provided levels and player.
	 * 
	 * @param p
	 *            The player.
	 * @param lvls
	 *            An Array of levels.
	 */
	protected SinglePlayerGame(Player p, Level[] lvls){
		assert p != null;
		
		this.player = p;
		this.levels = lvls;
		this.level = lvls[0];
		this.levelIndex = 0;
		level.registerPlayer(p);
	}
	
	@Override
	public List<Player> getPlayers() {
		return ImmutableList.of(player);
	}

	@Override
	public Level getLevel() {
		return level;
	}
	
	public int getLevelIndex(){
		return levelIndex;
	}

	/**
	 * Moves the player one square to the north if possible.
	 */
	public void moveUp() {
		move(player, Direction.NORTH);
	}

	/**
	 * Moves the player one square to the south if possible.
	 */
	public void moveDown() {
		move(player, Direction.SOUTH);
	}

	/**
	 * Moves the player one square to the west if possible.
	 */
	public void moveLeft() {
		move(player, Direction.WEST);
	}

	/**
	 * Moves the player one square to the east if possible.
	 */
	public void moveRight() {
		move(player, Direction.EAST);
	}
	
	/**
	 * Override the levelWon method to handle the win of a level
	 */
	@Override
	public void levelWon() {
		stop();
		if(levels != null){
			if(levelIndex + 1 > player.getMaxLevel()){
				notifyVictory(levelIndex+1);
				player.setMaxLevel(levelIndex+1);
				System.out.println(player.getMaxLevel());
			}
			nextLevel();
			start();
		}
	}
	
	/**
	 * Override the levelLost method to handle the death of pacman
	 */
	@Override
	public void levelLost() {
		stop();
		Random rand = new Random();
		int x = rand.nextInt(level.getBoard().getWidth());
		int y = rand.nextInt(level.getBoard().getHeight());
		if(player.hasLifeRemaining()){
			Square target = level.getBoard().squareAt(x, y);
			player.occupy(target.nearestValidRespawn(player, null));
			player.setAlive(true);
			start();
		}else{
			notifyDefeat();
		}
	}
	
	/**
	 * Switches to the next level if the game is in a state accepting it
	 */
	public void nextLevel() {
		if((!getLevel().hasBeenStarted() && !isInProgress())
			|| getLevel().remainingPellets() <= 0){
			level.unregisterPlayer(player);
			levelIndex = (levelIndex + 1) % levels.length;
			level = levels[levelIndex];
			level.registerPlayer(player);
		}
		
	}

	/**
	 * Switches to the previous level if the game is in a state accepting it
	 */
	public void previousLevel() {
		if(!getLevel().hasBeenStarted() && !isInProgress()){
			level.unregisterPlayer(player);
			levelIndex = (levels.length + levelIndex - 1) % levels.length;
			level = levels[levelIndex];
			level.registerPlayer(player);
		}
		
	}

	/**
	 * Sets the maximum level reached by the player
	 */
	@Override
	public void setPlayerLevel(int level) {
		player.setMaxLevel(level);
		
	}
	
	/**
	 * Overrides the start method to handle the fact that
	 * a player could be unable to start a level if his maximum
	 * level is too low
	 */
	@Override
	public void start() {
		synchronized (progressLock) {
			if (isInProgress()) {
				return;
			}
			if (getLevel().isAnyPlayerAlive()
					&& getLevel().remainingPellets() > 0
					&& player.getMaxLevel() >= levelIndex) {
				inProgress = true;
				getLevel().addObserver(this);
				getLevel().start();
			}else{
				notifyCantStart();
			}
		}
	}

}
