package nl.tudelft.jpacman.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.NPC;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A level of Pac-Man. A level consists of the board with the players and the
 * AIs on it.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public class Level {

	/**
	 * The log.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(Level.class);

	/**
	 * The board of this level.
	 */
	private final Board board;

	/**
	 * The lock that ensures moves are executed sequential.
	 */
	private final Object moveLock = new Object();

	/**
	 * The lock that ensures starting and stopping can't interfere with each
	 * other.
	 */
	private final Object startStopLock = new Object();

	/**
	 * The NPCs of this level and, if they are running, their schedules.
	 */
	private final Map<NPC, ScheduledExecutorService> npcs;

	/**
	 * <code>true</code> iff this level is currently in progress, i.e. players
	 * and NPCs can move.
	 */
	private boolean inProgress;

	/**
	 * The squares from which players can start this game.
	 */
	private final List<Square> startSquares;

	/**
	 * The start current selected starting square.
	 */
	private int startSquareIndex;

	/**
	 * The players on this level.
	 */
	private final List<Player> players;

	/**
	 * The objects observing this level.
	 */
	private final List<LevelObserver> observers;

	/**
	 * Creates a new level for the board.
	 * 
	 * @param b
	 *            The board for the level.
	 * @param ghosts
	 *            The ghosts on the board.
	 * @param startPositions
	 *            The squares on which players start on this board.
	 */
	protected Level(Board b, List<NPC> ghosts, List<Square> startPositions) {
		assert b != null;
		assert ghosts != null;
		assert startPositions != null;

		this.board = b;
		this.inProgress = false;
		this.npcs = new HashMap<>();
		for (NPC g : ghosts) {
			npcs.put(g, null);
		}
		this.startSquares = startPositions;
		this.startSquareIndex = 0;
		this.players = new ArrayList<>();
		this.observers = new ArrayList<>();
	}

	/**
	 * Adds an observer that will be notified when the level is won or lost.
	 * 
	 * @param observer
	 *            The observer that will be notified.
	 */
	public void addObserver(LevelObserver observer) {
		if (observers.contains(observer)) {
			return;
		}
		observers.add(observer);
	}

	/**
	 * Removes an observer if it was listed.
	 * 
	 * @param observer
	 *            The observer to be removed.
	 */
	public void removeObserver(LevelObserver observer) {
		observers.remove(observer);
	}

	/**
	 * Registers a player on this level, assigning him to a starting position. A
	 * player can only be registered once, registering a player again will have
	 * no effect.
	 * 
	 * @param p
	 *            The player to register.
	 */
	public void registerPlayer(Player p) {
		assert p != null;
		assert !startSquares.isEmpty();

		if (players.contains(p)) {
			return;
		}
		LOG.info("Registered player and put him on position {}",
				startSquareIndex);
		players.add(p);
		Square square = startSquares.get(startSquareIndex);
		p.occupy(square);
		startSquareIndex++;
		startSquareIndex %= startSquares.size();
	}

	/**
	 * Returns the board of this level.
	 * 
	 * @return The board of this level.
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Moves the unit into the given direction if possible and handles all
	 * collisions.
	 * 
	 * @param unit
	 *            The unit to move.
	 * @param direction
	 *            The direction to move the unit in.
	 */
	public void move(Unit unit, Direction direction) {
		assert unit != null;
		assert direction != null;

		if (!isInProgress()) {
			return;
		}

		synchronized (moveLock) {
			LOG.debug("Move: {} to the {}", unit, direction);
			unit.setDirection(direction);
			Square location = unit.getSquare();
			Square destination = location.getSquareAt(direction);

			if (destination.isAccessibleTo(unit)) {
				List<Unit> occupants = destination.getOccupants();
				unit.occupy(destination);
				LOG.debug("Unit moved, resolving collisions.");
				for (Unit occupant : occupants) {
					LOG.debug("Colliding {} with {}", unit, occupant);
					unit.collideWith(occupant);
					occupant.collideWith(unit);
				}
			} else {
				LOG.debug("Destination square not accessible to {}", unit);
			}
			updateObservers();
		}
	}

	/**
	 * Starts or resumes this level, allowing movement and (re)starting the
	 * NPCs.
	 */
	public void start() {
		synchronized (startStopLock) {
			if (isInProgress()) {
				return;
			}
			LOG.info("Starting or resuming level.");
			startNPCs();
			inProgress = true;
			updateObservers();
		}
	}

	/**
	 * Stops or pauses this level, no longer allowing any movement on the board
	 * and stopping all NPCs.
	 */
	public void stop() {
		synchronized (startStopLock) {
			if (!isInProgress()) {
				return;
			}
			LOG.info("Stopping level.");
			stopNPCs();
			inProgress = false;
		}
	}

	/**
	 * Starts all NPC movement scheduling.
	 */
	private void startNPCs() {
		for (final NPC npc : npcs.keySet()) {
			ScheduledExecutorService service = Executors
					.newSingleThreadScheduledExecutor();
			LOG.debug("Starting NPC thread for {}", npc);
			service.schedule(new NpcMoveTask(service, npc),
					npc.getInterval() / 2, TimeUnit.MILLISECONDS);
			npcs.put(npc, service);
		}
	}

	/**
	 * Stops all NPC movement scheduling and interrupts any movements being
	 * executed.
	 */
	private void stopNPCs() {
		for (Entry<NPC, ScheduledExecutorService> e : npcs.entrySet()) {
			LOG.debug("Shutting down NPC thread for {}", e.getKey());
			e.getValue().shutdownNow();
		}
	}

	/**
	 * Returns whether this level is in progress, i.e. whether moves can be made
	 * on the board.
	 * 
	 * @return <code>true</code> iff this level is in progress.
	 */
	public boolean isInProgress() {
		return inProgress;
	}

	/**
	 * Updates the observers about the state of this level.
	 */
	private void updateObservers() {
		if (!isAnyPlayerAlive()) {
			LOG.info("No players alive. Game over (lost)");
			for (LevelObserver o : observers) {
				o.levelLost();
			}
		}
		if (remainingPellets() == 0) {
			LOG.info("No pellets remaining. Game over (won).");
			for (LevelObserver o : observers) {
				o.levelWon();
			}
		}
	}

	/**
	 * Returns <code>true</code> iff at least one of the players in this level
	 * is alive.
	 * 
	 * @return <code>true</code> if at least one of the registered players is
	 *         alive.
	 */
	private boolean isAnyPlayerAlive() {
		for (Player p : players) {
			if (p.isAlive()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Counts the pellets remaining on the board.
	 * 
	 * @return The amount of pellets remaining on the board.
	 */
	private int remainingPellets() {
		Board b = getBoard();
		int pellets = 0;
		for (int x = 0; x < b.getWidth(); x++) {
			for (int y = 0; y < b.getHeight(); y++) {
				for (Unit u : b.squareAt(x, y).getOccupants()) {
					if (u instanceof Pellet) {
						pellets++;
					}
				}
			}
		}
		return pellets;
	}

	/**
	 * A task that moves an NPC and reschedules itself after it finished.
	 * 
	 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
	 */
	private class NpcMoveTask implements Runnable {

		/**
		 * Log for this inner class.
		 */
		private final Logger LOG = LoggerFactory.getLogger(NpcMoveTask.class);

		/**
		 * The service executing the task.
		 */
		private final ScheduledExecutorService service;

		/**
		 * Time stamp of last execution.
		 */
		private long lastExecution = 0;

		/**
		 * The supposed interval between the last move and this one.
		 */
		private long lastDelay = 0;

		/**
		 * The NPC to move.
		 */
		private final NPC npc;

		/**
		 * Creates a new task.
		 * 
		 * @param s
		 *            The service that executes the task.
		 * @param n
		 *            The NPC to move.
		 */
		private NpcMoveTask(ScheduledExecutorService s, NPC n) {
			this.service = s;
			this.npc = n;
		}

		@Override
		public void run() {
			Direction nextMove = npc.nextMove();
			if (nextMove != null) {
				move(npc, nextMove);
			}
			debugLogging();
			long interval = npc.getInterval();
			LOG.debug("Executed move for {}, next move in {} ms.", npc, interval);
			service.schedule(this, interval, TimeUnit.MILLISECONDS);
			lastDelay = interval;
			lastExecution = System.currentTimeMillis();
		}

		private void debugLogging() {
			if (lastExecution == 0) {
				lastExecution = System.currentTimeMillis();
			}
			long diff = System.currentTimeMillis() - lastExecution;
			LOG.debug("Time since last move for {}: {}ms, expected interval: {}ms (diff = {}ms)",
					npc, diff, lastDelay, diff - lastDelay);
		}
	}

	/**
	 * An observer that will be notified when the level is won or lost.
	 * 
	 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
	 */
	public static interface LevelObserver {

		/**
		 * The level has been won. Typically the level should be stopped when
		 * this event is received.
		 */
		void levelWon();

		/**
		 * The level has been lost. Typically the level should be stopped when
		 * this event is received.
		 */
		void levelLost();
	}
}
