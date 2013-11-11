package nl.tudelft.jpacman;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.game.Player;
import nl.tudelft.jpacman.game.SinglePlayerGame;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.sprite.EmptySprite;
import nl.tudelft.jpacman.sprite.Sprite;
import nl.tudelft.jpacman.ui.Action;
import nl.tudelft.jpacman.ui.PacManUiBuilder;

/**
 * Creates and launches the JPacMan UI.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public class Launcher {

	public static void main(String[] args) {

		Square square = new Square() {
			
			@Override
			public boolean isAccessibleTo(Unit unit) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Sprite getSprite() {
				return new EmptySprite();
			}
		};
		Square[][] grid = new Square[][] {{square}};
		
		final Board board = new Board(grid);
		
		List<Square> startSquares = new ArrayList<>();
		startSquares.add(new Square() {
			
			@Override
			public List<Unit> getOccupants() {
				return new ArrayList<>();
			}

			@Override
			public Square getSquareAt(Direction direction) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isAccessibleTo(Unit unit) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Sprite getSprite() {
				// TODO Auto-generated method stub
				return new EmptySprite();
			}
		});
		Level level = new Level(board, new ArrayList<NPC>(), startSquares );

		final Player player = new Player() {
			
			@Override
			public int getScore() {
				return 42;
			}

			@Override
			public Sprite getSprite() {
				return null;
			}

			@Override
			public void occupy(Square target) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Square getSquare() {
				// TODO Auto-generated method stub
				return null;
			}
		};

		final List<Player> players = new ArrayList<>();
		players.add(player);

		final SinglePlayerGame game = new SinglePlayerGame(player, level);

		new PacManUiBuilder().withDefaultButtons()
		.addKey(KeyEvent.VK_UP, new Action() {
			
			@Override
			public void doAction() {
				game.moveUp();
			}
		})
		.addKey(KeyEvent.VK_DOWN, new Action() {
			
			@Override
			public void doAction() {
				game.moveDown();
			}
		})
		.addKey(KeyEvent.VK_LEFT, new Action() {
			
			@Override
			public void doAction() {
				game.moveLeft();
			}
		})
		.addKey(KeyEvent.VK_RIGHT, new Action() {
			
			@Override
			public void doAction() {
				game.moveRight();
			}
		})
		.build(game).start();
	}
}
