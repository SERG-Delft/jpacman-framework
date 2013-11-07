package nl.tudelft.jpacman.ui;

import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.tudelft.jpacman.game.Player;

/**
 * A panel consisting of a column for each player, with the numbered players on
 * top and their respective scores underneath.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 * 
 */
class ScorePanel extends JPanel {

	/**
	 * Default serialisation ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The map of players and the labels their scores are on.
	 */
	private final Map<Player, JLabel> scoreLabels;

	/**
	 * Creates a new score panel with a column for each player.
	 * 
	 * @param players
	 *            The players to display the scores of.
	 */
	ScorePanel(List<Player> players) {
		super();
		assert players != null;

		setLayout(new GridLayout(2, players.size()));

		for (int i = 1; i <= players.size(); i++) {
			add(new JLabel("Player " + i, JLabel.CENTER));
		}
		scoreLabels = new LinkedHashMap<>();
		for (Player p : players) {
			JLabel scoreLabel = new JLabel("0", JLabel.CENTER);
			scoreLabels.put(p, scoreLabel);
			add(scoreLabel);
		}
	}

	/**
	 * Refreshes the scores of the players.
	 */
	void refresh() {
		for (Player p : scoreLabels.keySet()) {
			scoreLabels.get(p).setText(String.valueOf(p.getScore()));
		}
	}
}
