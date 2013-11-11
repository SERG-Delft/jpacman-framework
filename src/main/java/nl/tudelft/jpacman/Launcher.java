package nl.tudelft.jpacman;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.game.Player;
import nl.tudelft.jpacman.game.SinglePlayerGame;
import nl.tudelft.jpacman.level.Level;
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

		final Board board = new Board() {

			@Override
			public Square squareAt(int x, int y) {
				return new Square() {

					@Override
					public List<Unit> getOccupants() {
						return new ArrayList<>();
					}
				};
			}

			@Override
			public int getWidth() {
				return 10;
			}

			@Override
			public int getHeight() {
				return 10;
			}
		};

		Level level = new Level(board);

		final Player player = new Player() {
			
			@Override
			public int getScore() {
				return 42;
			}

			@Override
			public Sprite getSprite() {
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
