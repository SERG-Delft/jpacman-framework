package nl.tudelft.jpacman.npc.ghost;

import java.util.Map;
import java.util.Random;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * <p>
 * An implementation of the classic Pac-Man ghost Shadow.
 * </p>
 * <p>
 * Nickname: Blinky. As his name implies, Shadow is usually a constant shadow on
 * Pac-Man's tail. When he's not patrolling the top-right corner of the maze,
 * Shadow tries to find the quickest route to Pac-Man's position. Despite the
 * fact that Pinky's real name is Speedy, Shadow is actually the fastest of the
 * ghosts because of when there are only a few pellets left, Blinky drastically
 * speeds up, which can make him quite deadly. In the original Japanese version,
 * his name is Oikake/Akabei.
 * </p>
 * <p>
 * <b>AI:</b> When the ghosts are not patrolling in their home corners (Blinky:
 * top-right, Pinky: top-left, Inky: bottom-right, Clyde: bottom-left), Blinky
 * will attempt to shorten the distance between Pac-Man and himself. If he has
 * to choose between shortening the horizontal or vertical distance, he will
 * choose to shorten whichever is greatest. For example, if Pac-Man is four grid
 * spaces to the left, and seven grid spaces above Blinky, he'll try to move up
 * towards Pac-Man before he moves to the left.
 * </p>
 * <p>
 * Source: http://strategywiki.org/wiki/Pac-Man/Getting_Started
 * </p>
 *
 * @author Jeroen Roosen
 *
 */
public class  Blinky extends VulnerableGhost {

	/**
	 * The variation in intervals, this makes the ghosts look more dynamic and
	 * less predictable.
	 */
	private static final int INTERVAL_VARIATION = 50;

	/**
	 * The base movement interval.
	 */
	private /*static final*/ int MOVE_INTERVAL = 250;


	/**
	 * Creates a new "Blinky", a.k.a. "Shadow".
	 *
	 * @param spriteMap
	 *            The sprites for this ghost.
	 */
	public Blinky(Map<Direction, Sprite> spriteMap, boolean aH, String strategy, Direction[] dir) {
		super(spriteMap, aH, strategy);
		this.cheminEnCours=dir;
		this.chemin=dir;
		this.strategyMode=new DispersionBlinky(this);
	}
	public Blinky(Map<Direction, Sprite> spriteMap){
		super(spriteMap);
	}

	@Override
	public void setPoursuiteMode() {
		strategyMode=new PoursuiteBlinky(this);
	}

	@Override
	public void setDispersionMode() {
		strategyMode=new DispersionBlinky(this);
	}

	public Direction[] getChemin(){
		return this.chemin;
	}
	@Override
	public long getInterval() {
		// TODO Blinky should speed up when there are a few pellets left, but he
		// has no way to find out how many there are.
		if ((int)(Math.random()*(10))==5){
		MOVE_INTERVAL = MOVE_INTERVAL - 5;
		}
		return MOVE_INTERVAL+ new Random().nextInt(INTERVAL_VARIATION);
	}



	
	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * When the ghosts are not patrolling in their home corners (Blinky:
	 * top-right, Pinky: top-left, Inky: bottom-right, Clyde: bottom-left),
	 * Blinky will attempt to shorten the distance between Pac-Man and himself.
	 * If he has to choose between shortening the horizontal or vertical
	 * distance, he will choose to shorten whichever is greatest. For example,
	 * if Pac-Man is four grid spaces to the left, and seven grid spaces above
	 * Blinky, he'll try to move up towards Pac-Man before he moves to the left.
	 * </p>
	 */
	@Override
	public Direction nextMove() {

		if(!isHunter()){
			return randomMove();
		}
		/*
		if (this.getStrategy() == "modePoursuite"){
			PoursuiteBlinky pp = new PoursuiteBlinky(this);
			return pp.nextMove();
		}
		else if (this.getStrategy() == "modeDispersion"){
			DispersionBlinky db = new DispersionBlinky(this);
			return db.nextMove();
		}
		return this.randomMove();
		*/
		return strategyMode.nextMove();
	}
}