package nl.tudelft.jpacman.ui;

import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.ui.ScorePanel.ScoreFormatter;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The default JPacMan UI frame. The PacManUI consists of the following
 * elements:
 * 
 * <ul>
 * <li>A score panel at the top, displaying the score of the player(s).
 * <li>A board panel, displaying the current level, i.e. the board and all units
 * on it.
 * <li>A button panel, containing all buttons provided upon creation.
 * </ul>
 * 
 * @author Jeroen Roosen 
 * 
 */
public class PacManUI extends JFrame {

	/**
	 * Default serialisation UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The desired frame rate interval for the graphics in milliseconds, 40
	 * being 25 fps.
	 */
	private static final int FRAME_INTERVAL = 40;

	/**
	 * The panel displaying the player scores.
	 */
	private ScorePanel scorePanel;

	/**
	 * The panel displaying the game.
	 */
	private BoardPanel boardPanel;
	private Game game;

	/**
	 * Creates a new UI for a JPac-Man game.
	 * 
	 * @param game
	 *            The game to play.
	 * @param buttons
	 *            The map of caption-to-action entries that will appear as
	 *            buttons on the interface.
	 * @param keyMappings
	 *            The map of keyCode-to-action entries that will be added as key
	 *            listeners to the interface.
	 * @param sf
	 *            The formatter used to display the current score. 
	 */
	public PacManUI(final Game game, final Map<String, Action> buttons,
			final Map<Integer, Action> keyMappings, ScoreFormatter sf) {
		super("JPac-Man");
		assert game != null;
		assert buttons != null;
		assert keyMappings != null;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);

        setKeys(keyMappings);

        Map<String, Action> buttons1 = new HashMap<>();
        Map<String, Action> buttons2 = new HashMap<>();

        for(String key : buttons.keySet()){
            if(key.contains("Hunter")){
                buttons2.put(key, buttons.get(key));
            }
            else{
                buttons1.put(key, buttons.get(key));
            }
        }

		JPanel buttonPanel = new ButtonPanel(buttons1, this);
		JPanel buttonPanel2 = new ButtonPanel(buttons2, this);
		JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BorderLayout());
        buttonContainer.add(buttonPanel, BorderLayout.NORTH);
        buttonContainer.add(buttonPanel2, BorderLayout.SOUTH);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(buttonContainer, BorderLayout.SOUTH);

        setGame(game, sf);
	}

    public void setKeys(Map<Integer, Action> keyMappings) {
        if(getKeyListeners().length > 0) {
            removeKeyListener(getKeyListeners()[0]);
        }
        PacKeyListener keys = new PacKeyListener(keyMappings);
        addKeyListener(keys);
    }

    public void setGame(Game game) {
        this.setGame(game,null);
    }
    public void setGame(Game game, ScoreFormatter sf) {
		try {
			getContentPane().remove(scorePanel);
			getContentPane().remove(boardPanel);
		}catch (Exception ignored){}
		
		scorePanel = new ScorePanel(game.getScorers());
		if (sf != null) {
			scorePanel.setScoreFormatter(sf);
		}
		boardPanel = new BoardPanel(game);
		getContentPane().add(scorePanel, BorderLayout.NORTH);
		getContentPane().add(boardPanel, BorderLayout.CENTER);
		
		pack();
	}

	/**
	 * Starts the "engine", the thread that redraws the interface at set
	 * intervals.
	 */
	public void start() {
		setVisible(true);

		ScheduledExecutorService service = Executors
				.newSingleThreadScheduledExecutor();

		service.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				nextFrame();
			}
		}, 0, FRAME_INTERVAL, TimeUnit.MILLISECONDS);

	}

	/**
	 * Draws the next frame, i.e. refreshes the scores and game.
	 */
	private void nextFrame() {
		boardPanel.repaint();
		scorePanel.refresh();
	}
}
