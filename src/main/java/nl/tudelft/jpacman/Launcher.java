package nl.tudelft.jpacman;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.game.Player;
import nl.tudelft.jpacman.game.SinglePlayerGame;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.ui.Action;
import nl.tudelft.jpacman.ui.PacManUiBuilder;

/**
 * Creates and launches the JPacMan UI.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public class Launcher {

	public static void main(String[] args) throws IOException {

		PacManSprites sprites = new PacManSprites();
		
		LevelFactory lf = new LevelFactory(sprites);
		BoardFactory bf = new BoardFactory(sprites);
		
		MapParser parser = new MapParser(lf, bf);
		Level level = parser.parseMap(Launcher.class.getResourceAsStream("/board.txt"));
		
		final Player player = new Player(sprites.getPacmanSprites(), sprites.getPacManDeathAnimation());

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
