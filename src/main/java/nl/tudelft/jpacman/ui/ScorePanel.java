package nl.tudelft.jpacman.ui;

import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.tudelft.jpacman.level.Player;

/**
 * A panel consisting of a column for each player, with the numbered players on
 * top and their respective scores underneath.
 * 
 * @author Jeroen Roosen 
 * 
 */
public class ScorePanel extends JPanel {

	/**
	 * Default serialisation ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The map of players and the labels their scores are on.
	 */
	private final Map<Player, JLabel> scoreLabels;
	
	/**
	 * The default way in which the score is shown.
	 */
	public static final ScoreFormatter DEFAULT_SCORE_FORMATTER =
			// this lambda breaks cobertura 2.7 ...
			// player) -> String.format("Score: %3d", player.getScore());
			new ScoreFormatter() {
				public String format(Player p) {
					return String.format("Score: %3d", p.getScore());
				}
			};

	/**
	 * The way to format the score information.
	 */
	private ScoreFormatter scoreFormatter = DEFAULT_SCORE_FORMATTER;

	/**
	 * Creates a new score panel with a column for each player.
	 * 
	 * @param players
	 *            The players to display the scores of.
	 */
	public ScorePanel(List<Player> players) {
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
	protected void refresh() {
		for (Map.Entry<Player, JLabel> entry : scoreLabels.entrySet()) {
			Player p = entry.getKey();
			String score = "";
			if (!p.isAlive()) {
				score = "You died. ";
			}
			score += scoreFormatter.format(p);
			entry.getValue().setText(score);
		}
	}
	
	/**
	 * Provide means to format the score for a given player.
	 */
	public interface ScoreFormatter {
		
		/**
		 * Format the score of a given player.
		 * @param p The player and its score
		 * @return Formatted score.
		 */
		String format(Player p);
	}
	
	/**
	 * Let the score panel use a dedicated score formatter.
	 * @param sf Score formatter to be used.
	 */
	public void setScoreFormatter(ScoreFormatter sf) {
		assert sf != null;
		scoreFormatter = sf;
	}
}
