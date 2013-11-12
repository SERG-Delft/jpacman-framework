package nl.tudelft.jpacman.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.game.Player;
import nl.tudelft.jpacman.npc.NPC;

/**
 * A level of Pac-Man. A level consists of the board with the players and the
 * AIs on it.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public class Level {

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
	 * The service that executes the NPC moves.
	 */
	private final ScheduledExecutorService service;

	/**
	 * The NPCs of this level and, if they are running, their schedules.
	 */
	private final Map<NPC, ScheduledFuture<?>> npcs;

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
	 * The table of possible collisions between units.
	 */
	private final CollisionTable collisions;

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
	public Level(Board b, List<NPC> ghosts, List<Square> startPositions) {
		assert b != null;
		assert ghosts != null;
		assert startPositions != null && !startPositions.isEmpty();

		this.service = Executors.newScheduledThreadPool(1);
		this.board = b;
		this.inProgress = false;
		this.npcs = new HashMap<>();
		for (NPC g : ghosts) {
			npcs.put(g, null);
		}
		this.startSquares = startPositions;
		this.startSquareIndex = 0;
		this.players = new ArrayList<>();
		
		this.collisions = new CollisionInteractionMap();
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
		
		if (players.contains(p)) {
			return;
		}
		
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
			unit.setDirection(direction);
			Square location = unit.getSquare();
			Square destination = location.getSquareAt(direction);
			
			if (destination.isAccessibleTo(unit)) {
				unit.occupy(destination);
				List<Unit> occupants = destination.getOccupants();
				for (Unit occupant : occupants) {
					collisions.collide(unit, occupant);
				}
			}
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
			startNPCs();
			inProgress = true;
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
			stopNPCs();
			inProgress = false;
		}
	}

	/**
	 * Starts all NPC movement scheduling.
	 */
	private void startNPCs() {
		for (final NPC npc : npcs.keySet()) {
			long interval = npc.getInterval();
			ScheduledFuture<?> schedule = service.scheduleAtFixedRate(
					new Runnable() {

						@Override
						public void run() {
							move(npc, npc.nextMove());
						}
					}, interval / 2, interval, TimeUnit.MILLISECONDS);
			npcs.put(npc, schedule);
		}
	}

	/**
	 * Stops all NPC movement scheduling and interrupts any movements being
	 * executed.
	 */
	private void stopNPCs() {
		for (ScheduledFuture<?> schedule : npcs.values()) {
			schedule.cancel(true);
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
}
