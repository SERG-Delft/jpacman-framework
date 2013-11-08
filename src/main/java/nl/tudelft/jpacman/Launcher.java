package nl.tudelft.jpacman;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.Level;
import nl.tudelft.jpacman.game.Player;
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

		final Level level = new Level() {

			@Override
			public Board getBoard() {
				return board;
			}
		};

		final Player player = new Player() {
			
			@Override
			public int getScore() {
				return 42;
			}
		};

		final List<Player> players = new ArrayList<>();
		players.add(player);

		final Game game = new Game() {

			@Override
			public void stop() {
				// TODO Auto-generated method stub
			}

			@Override
			public void start() {
				// TODO Auto-generated method stub
			}

			@Override
			public boolean isInProgress() {
				return false;
			}

			@Override
			public List<Player> getPlayers() {
				return players;
			}

			@Override
			public Level getLevel() {
				return level;
			}

			@Override
			public void move(Player player, Direction direction) {
				// TODO Auto-generated method stub
			}
		};

		new PacManUiBuilder().withDefaultButtons()
		.addKey(KeyEvent.VK_UP, new Action() {
			
			@Override
			public void doAction() {
				game.move(player, Direction.NORTH);
			}
		})
		.addKey(KeyEvent.VK_DOWN, new Action() {
			
			@Override
			public void doAction() {
				game.move(player, Direction.SOUTH);
			}
		})
		.addKey(KeyEvent.VK_LEFT, new Action() {
			
			@Override
			public void doAction() {
				game.move(player, Direction.WEST);
			}
		})
		.addKey(KeyEvent.VK_RIGHT, new Action() {
			
			@Override
			public void doAction() {
				game.move(player, Direction.EAST);
			}
		})
		.build(game).start();
	}
}
