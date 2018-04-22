package nl.tudelft.jpacman.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.Ghost;

/**
 * A level of Pac-Man. A level consists of the board with the players and the
 * AIs on it.
 *
 * @author Jeroen Roosen 
 */
@SuppressWarnings("PMD.TooManyMethods")
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
     * The NPCs of this level and, if they are running, their schedules.
     */
    private final Map<Ghost, ScheduledExecutorService> npcs;

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
    private final CollisionMap collisions;

    /**
     * The objects observing this level.
     */
    private final Set<LevelObserver> observers;

    /**
     * Creates a new level for the board.
     *
     * @param board
     *            The board for the level.
     * @param ghosts
     *            The ghosts on the board.
     * @param startPositions
     *            The squares on which players start on this board.
     * @param collisionMap
     *            The collection of collisions that should be handled.
     */
    public Level(Board board, List<Ghost> ghosts, List<Square> startPositions,
                 CollisionMap collisionMap) {
        assert board != null;
        assert ghosts != null;
        assert startPositions != null;

        this.board = board;
        this.inProgress = false;
        this.npcs = new HashMap<>();
        for (Ghost ghost : ghosts) {
            npcs.put(ghost, null);
        }
        this.startSquares = startPositions;
        this.startSquareIndex = 0;
        this.players = new ArrayList<>();
        this.collisions = collisionMap;
        this.observers = new HashSet<>();
    }

    /**
     * Adds an observer that will be notified when the level is won or lost.
     *
     * @param observer
     *            The observer that will be notified.
     */
    public void addObserver(LevelObserver observer) {
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
     * @param player
     *            The player to register.
     */
    public void registerPlayer(Player player) {
        assert player != null;
        assert !startSquares.isEmpty();

        if (players.contains(player)) {
            return;
        }
        players.add(player);
        Square square = startSquares.get(startSquareIndex);
        player.occupy(square);
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
        assert unit.hasSquare();

        if (!isInProgress()) {
            return;
        }

        synchronized (moveLock) {
            unit.setDirection(direction);
            Square location = unit.getSquare();
            Square destination = location.getSquareAt(direction);

            if (destination.isAccessibleTo(unit)) {
                List<Unit> occupants = destination.getOccupants();
                unit.occupy(destination);
                for (Unit occupant : occupants) {
                    collisions.collide(unit, occupant);
                }
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
            stopNPCs();
            inProgress = false;
        }
    }

    /**
     * Starts all NPC movement scheduling.
     */
    private void startNPCs() {
        for (final Ghost npc : npcs.keySet()) {
            ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

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
        for (Entry<Ghost, ScheduledExecutorService> entry : npcs.entrySet()) {
            ScheduledExecutorService schedule = entry.getValue();
            assert schedule != null;
            schedule.shutdownNow();
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
            for (LevelObserver observer : observers) {
                observer.levelLost();
            }
        }
        if (remainingPellets() == 0) {
            for (LevelObserver observer : observers) {
                observer.levelWon();
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
    public boolean isAnyPlayerAlive() {
        for (Player player : players) {
            if (player.isAlive()) {
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
    public int remainingPellets() {
        Board board = getBoard();
        int pellets = 0;
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                for (Unit unit : board.squareAt(x, y).getOccupants()) {
                    if (unit instanceof Pellet) {
                        pellets++;
                    }
                }
            }
        }
        assert pellets >= 0;
        return pellets;
    }

    /**
     * A task that moves an NPC and reschedules itself after it finished.
     *
     * @author Jeroen Roosen
     */
    private final class NpcMoveTask implements Runnable {

        /**
         * The service executing the task.
         */
        private final ScheduledExecutorService service;

        /**
         * The NPC to move.
         */
        private final Ghost npc;

        /**
         * Creates a new task.
         *
         * @param service
         *            The service that executes the task.
         * @param npc
         *            The NPC to move.
         */
        NpcMoveTask(ScheduledExecutorService service, Ghost npc) {
            this.service = service;
            this.npc = npc;
        }

        @Override
        public void run() {
            Direction nextMove = npc.nextMove();
            if (nextMove != null) {
                move(npc, nextMove);
            }
            long interval = npc.getInterval();
            service.schedule(this, interval, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * An observer that will be notified when the level is won or lost.
     *
     * @author Jeroen Roosen
     */
    public interface LevelObserver {

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
