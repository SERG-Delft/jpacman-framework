package nl.tudelft.jpacman.npc.ghost;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * An antagonist in the game of Pac-Man, a ghost.
 * 
 * @author Jeroen Roosen 
 */
public abstract class Ghost extends NPC {
	
	/**
	 * The sprite map, one sprite for each direction.
	 */
	private Map<Direction, Sprite> sprites;

	private Square home;//Sa case maison
	public Direction[] chemin;
	public Direction[] cheminEnCours;//Ses prochaines directions à prendre lors du tour dans la maison
	private boolean atteintHome;//S'il a atteint sa case maison
	
	/**
	 * Creates a new ghost.
	 * 
	 * @param spriteMap
	 *            The sprites for every direction.
	 */
	protected Ghost(Map<Direction, Sprite> spriteMap,String strategy) {
		super(strategy);
		this.sprites = spriteMap;
	}
	
	protected Ghost(Map<Direction, Sprite> spriteMap, boolean aH, String strategy) {
		super(strategy);
		this.sprites = spriteMap;
		this.atteintHome = aH;
	}

	@Override
	public Sprite getSprite() {
		return sprites.get(getDirection());
	}

	public Square getHome(){
		return this.home;
	}
	
	public void setHome(Square home){
		this.home=home;
	}
	
	public String getStrategy(){
		return this.strategy;
	}

	public void setStrategy(String strategy){
		if (this.getStrategy() != strategy){
			if (strategy == "modePoursuite" && this.atteintHome){
				this.cheminEnCours=this.chemin;
				this.atteintHome=false;
				this.strategy="modePoursuite";
			}
		else if (strategy == "modeDispersion"){
			this.strategy="modeDispersion";
		}
		}
	}
	
	public boolean getAtteintHome(){
		return this.atteintHome;	
	}
	public void setAtteintHome(boolean b){
		this.atteintHome=b;
	}
	public Direction[] getChemin(){
		return this.chemin;
	}
	
	public Direction[] getCheminEnCours(){
		return this.cheminEnCours;
	}
	
	public void setCheminEnCours(Direction[] cheminencours){
		this.cheminEnCours=cheminencours;
	}
	public void avancerChemin(){
		int n = this.getCheminEnCours().length;
		Direction[] dir = new Direction[n];
		for (int i=1; i<n; i++){
			dir[i-1]=this.cheminEnCours[i];
		}
		dir[n-1]=this.cheminEnCours[0];
		setCheminEnCours(dir);
	}
	
	/**
	 * Determines a possible move in a random direction.
	 * 
	 * @return A direction in which the ghost can move, or <code>null</code> if
	 *         the ghost is shut in by inaccessible squares.
	 */
	protected Direction randomMove() {
		Square square = getSquare();
		List<Direction> directions = new ArrayList<>();
		for (Direction d : Direction.values()) {
			if (square.getSquareAt(d).isAccessibleTo(this)) {
				directions.add(d);
			}
		}
		if (directions.isEmpty()) {
			return null;
		}
		int i = new Random().nextInt(directions.size());
		return directions.get(i);
	}
}
