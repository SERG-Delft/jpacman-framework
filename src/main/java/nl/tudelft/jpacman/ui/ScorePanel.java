package nl.tudelft.jpacman.ui;

import nl.tudelft.jpacman.level.Scorer;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	private final Map<Scorer, JLabel> scoreLabels;
	
	/**
	 * The default way in which the score is shown.
	 */
	public static final ScoreFormatter DEFAULT_SCORE_FORMATTER = 
			// this lambda breaks cobertura 2.7 ...
			// player) -> String.format("Score: %3d", player.getScore());
			new ScoreFormatter() {
				public String format(Scorer p) {
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
	 * @param scorers
	 *            The scorers to display the scores of.
	 */
	public ScorePanel(List<Scorer> scorers) {
		super();
		assert scorers != null;

		setLayout(new GridLayout(2, scorers.size()));

		for (int i = 1; i <= scorers.size(); i++) {
			add(new JLabel(scorers.get(i-1).getName() + " " + i, JLabel.CENTER));
		}
		scoreLabels = new LinkedHashMap<>();
		for (Scorer s : scorers) {
			JLabel scoreLabel = new JLabel("0", JLabel.CENTER);
			scoreLabels.put(s, scoreLabel);
			add(scoreLabel);
		}
	}

	/**
	 * Refreshes the scores of the players.
	 */
	protected void refresh() {
		for (Scorer s : scoreLabels.keySet()) {
			String score = "";
			if (!s.isAlive()) {
				score = "Dead x_x. ";
			}
			score += scoreFormatter.format(s);
			scoreLabels.get(s).setText(score);
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
		String format(Scorer p);
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
