package nl.tudelft.jpacman.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import nl.tudelft.jpacman.fruit.Fruit;
import nl.tudelft.jpacman.fruit.Pepper;
import nl.tudelft.jpacman.fruit.Pomegranate;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.npc.ghost.Ghost;
import nl.tudelft.jpacman.specialcase.Bridge;

/**
 * A level of Pac-Man. A level consists of the board with the players and the
 * AIs on it.
 * 
 * @author Jeroen Roosen 
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
	 * The NPCs of this level and, if they are running, their schedules.
	 */
	private  Map<NPC, ScheduledExecutorService> npcs;

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
	private final List<LevelObserver> observers;
	
	private float coefficientVitesse;

	

	/**
	 * Creates a new level for the board.
	 * 
	 * @param b
	 *            The board for the level.
	 * @param ghosts
	 *            The ghosts on the board.
	 * @param startPositions
	 *            The squares on which players start on this board.
	 * @param collisionMap
	 *            The collection of collisions that should be handled.
	 */
	public Level(Board b, List<NPC> ghosts, List<Square> startPositions,
			CollisionMap collisionMap) {
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
		this.collisions = collisionMap;
		this.observers = new ArrayList<>();
		this.coefficientVitesse=1;
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
		p.setSpawn(startSquares.get(startSquareIndex));
		players.add(p);
		Square square= p.getSpawn();
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
	public void move(Unit unit, Direction direction) 
	{
		assert unit != null;
		assert direction != null;

		if (!isInProgress())
		{
			return;
		}
		if(unit instanceof Player)
		{
			if((((Player)unit).isInvisible())||(((Player)unit).isStun()))
		    {
			 if(((Fruit) ((Player)unit).getEffect()).check())
			 {
				((Player)unit).resetEffect();
			 }
		    }
		}
		if((unit instanceof Ghost)&&((Ghost)unit).isTrap())
		{
			((Ghost)unit).check();
		
		}
		
		

		synchronized (moveLock) 
		{
			unit.setDirection(direction);
			Square location = unit.getSquare();
			Square destination = location.getSquareAt(direction);
			Object researchBridge=unit.checkOnBridge(location);
			
			if(researchBridge instanceof Bridge)
			{
				Bridge bridge= (Bridge) researchBridge;
				String position= bridge.getEnterDirection(unit);
				switch (direction)
				{
				case EAST:
					if(position.equals("before"))
					{
						destination=location;
					}else
					{
						bridge.removeUnit(unit);
						if(unit instanceof Player)
						{
							((Player)unit).addPoints(10);
						}
					}

					break;
				case WEST:
					if(position.equals("before"))
					{
						destination=location;
						
					}
					else
					{
						bridge.removeUnit(unit);
						if(unit instanceof Player)
						{
							((Player)unit).addPoints(10);
						}
					}
					break;
					
				case NORTH:
					if(position.equals("behind"))
					{
						destination=location;
						
					}
					else
					{
						bridge.removeUnit(unit);
					
					
					}
					
					break;
				case SOUTH:
					if(position.equals("behind"))
					{
						destination=location;
						
					}
					else
					{
						bridge.removeUnit(unit);
					}
					
					break;
				
				}
			
			}
			
			
			if((unit instanceof Player)&&((Player)unit).isStun())
			{
				destination=location;
			}
			if((unit instanceof Ghost)&&((Ghost)unit).isTrap())
			{
				destination=location;
			}

			if (destination.isAccessibleTo(unit))
			{
				List<Unit> occupants = destination.getOccupants();
				unit.occupy(destination);
				for (Unit occupant : occupants)
				{
					collisions.collide(unit, occupant);
					if((occupant instanceof Fruit)&&(unit instanceof Player))
					{
						fruitEffect(occupant,unit.getSquare());
					}
				}
			}
			updateObservers();
		}
	}
	
	
	

	
	
	
	
	
	
	
	
	/**
	 * performs the effect according to the fruit
	 * @param fruit  CollisionFruit
	 * @param unit Destination square
	 */
	public void fruitEffect(Unit fruit,Square unit)
	{
		players.get(0).defineEffect((Fruit) fruit);
		
		switch(fruit.getClass().getSimpleName())
		{
		case "Pomegranate":	
			
			int abscisseUnit= board.getAbscisseSquare(unit);
			int ordonneUnit= board.getOrdonneSquare(unit);
			
			for ( NPC npc : npcs.keySet())
			{
				
				Square location=npc.getSquare();	
			    int abscisseNpc=board.getAbscisseSquare(location);
			    int ordonneNpc=board.getOrdonneSquare(location);
			    
			
			   if(((Pomegranate)fruit).isClose(abscisseUnit, ordonneUnit, abscisseNpc, ordonneNpc)) 
			   {
				   npc.dead();
			   }

			}
			players.get(0).resetEffect();
		break;
		
		case "Pepper":
					if(this.coefficientVitesse!=1)
					{
						this.coefficientVitesse=1;
					}
					else
					{
						this.coefficientVitesse=1.4f;
				        ((Fruit)fruit).activate();
				       				 
					}
				
		break;
		
		case "Tomato":
					players.get(0).setInvisible(true);
					((Fruit) players.get(0).getEffect()).activate();
		break;
		
		case "Bean":
		break;
		
		case "Potato":
			if(this.coefficientVitesse!=1)
			{
				this.coefficientVitesse=1;
			}
			else
			{
			this.coefficientVitesse=0.7f;
			((Fruit)fruit).activate();
			}
			
		break;		
		case "Fish":
			players.get(0).setStun(true);
			((Fruit) players.get(0).getEffect()).activate();
		break;
		
		default:
		break;
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
		synchronized (startStopLock) 
		{
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
	private void startNPCs()
	{
		
		for ( NPC npc : npcs.keySet())
		{
			ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();					
			service.schedule(new NpcMoveTask(service, npc), ((npc.getInterval() /2)), TimeUnit.MILLISECONDS);	
			npcs.put(npc, service);
		}
	}

	/**
	 * Stops all NPC movement scheduling and interrupts any movements being
	 * executed.
	 */
	private void stopNPCs()
	{
		for (Entry<NPC, ScheduledExecutorService> e : npcs.entrySet())
		{
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
			for (LevelObserver o : observers) {
				o.levelLost();
			}
		}
		if (remainingPellets() == 0) {
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
	public boolean isAnyPlayerAlive() {
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
	public int remainingPellets() {
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
	 * @author Jeroen Roosen 
	 */
	private final class NpcMoveTask implements Runnable {

		/**
		 * The service executing the task.
		 */
		private  ScheduledExecutorService service;

		/**
		 * The NPC to move.
		 */
		private  NPC npc;

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
					
			if(players.get(0).getEffect() instanceof Fruit)
			{
				if(((Fruit)players.get(0).getEffect() ).check())
				{
					coefficientVitesse=1;
					players.get(0).resetEffect();
					players.get(0).resetEffect();
				}
			}
			
		
			if(npc.isDead()==false)
			{
					Direction nextMove = npc.nextMove();
			if (nextMove != null)
			{
				move(npc, nextMove);
			}
			long interval =(long) (npc.getInterval()*coefficientVitesse);
			service.schedule(this, interval, TimeUnit.MILLISECONDS);
			}
			else
			{
				npc.leaveSquare();
			}
		
				
	
			
			
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
