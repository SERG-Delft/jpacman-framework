package nl.tudelft.jpacman.ui;


import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.tudelft.jpacman.fruit.Fruit;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.ui.ScorePanel.ScoreFormatter;

public class StatePanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	/**
	 * The map of players and the labels their scores are on.
	 */
	private final Map<Player, JLabel> stateLabels;
	
	/**
	 * The default way in which the score is shown.
	 */
	public static final StateFormatter DEFAULT_SCORE_FORMATTER = 
		
			new StateFormatter() {
				public String format(Player player) {
					
					if(player.isEffect())
					{
						return String.format("Etat: "+ ((Fruit) player.getEffect()).effect());	
					}else
					{
						return String.format("No effect");
					}
					
				}
			};
	
	/**
	 * The way to format the score information.
	 */
	private StateFormatter stateFormatter = DEFAULT_SCORE_FORMATTER;

	/**
	 * Creates a new score panel with a column for each player.
	 * 
	 * @param players
	 *            The players to display the scores of.
	 */
	public StatePanel(List<Player> players) {
		super();
		assert players != null;

		setLayout(new GridLayout(2, players.size()));

		for (int i = 1; i <= players.size(); i++) {
			add(new JLabel("Player " + i, JLabel.CENTER));
		}
		stateLabels = new LinkedHashMap<>();
		for (Player player : players) {
			JLabel stateLabel = new JLabel("No effect", JLabel.CENTER);
			stateLabels.put(player, stateLabel);
			add(stateLabel);
		}
	}

	/**
	 * Refreshes the scores of the players.
	 */
	protected void refresh() {
		
			for (Player p : stateLabels.keySet()) 
			{
				String score = "";
				if (!p.isAlive()) {
					score = "Dead ";
				}
				score += stateFormatter.format(p);
				stateLabels.get(p).setText(score);
			}
		
	}
	
	/**
	 * Provide means to format the score for a given player.
	 */
	public interface StateFormatter {
		
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
	public void setScoreFormatter(nl.tudelft.jpacman.ui.ScorePanel.ScoreFormatter sf) {
		assert sf != null;
		stateFormatter = (StateFormatter) sf;
	}
}
