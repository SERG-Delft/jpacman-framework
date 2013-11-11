package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.game.Game;

public class Level {

	private final Board board;
	
	public Level(Board b) {
		assert b != null;
		this.board = b;
	}
	
	public Board getBoard() {
		return board;
	}

	public void move(Unit unit, Direction direction) {
		
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public void start(Game game) {
		// TODO Auto-generated method stub
		
	}

}
