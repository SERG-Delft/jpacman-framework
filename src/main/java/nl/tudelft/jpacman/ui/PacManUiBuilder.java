package nl.tudelft.jpacman.ui;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.ui.ScorePanel.ScoreFormatter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Builder for the JPac-Man UI.
 * 
 * @author Jeroen Roosen 
 */
public class PacManUiBuilder {

	/**
	 * Caption for the default stop button.
	 */
	private static final String STOP_CAPTION = "Stop";

	/**
	 * Caption for the default start button.
	 */
	private static final String START_CAPTION = "Classic";
	private static final String START_CAPTION_HUNTER_1P = "Hunter 1P";
	private static final String START_CAPTION_HUNTER_2P = "Hunter 2P";
	private static final String START_CAPTION_HUNTER_3P = "Hunter 3P";
	private static final String START_CAPTION_HUNTER_4P = "Hunter 4P";
	private static final String START_CAPTION_INFINITE = "Infinite Board";
	private static final String START_CAPTION_TWO_PLAYERS = "Two Player";
    private static final int DEFAULT_BUTTONS  = 0;
    private static final int ADVANCED_BUTTONS = 1;

    /**
	 * Map of buttons and their actions.
	 */
	private final Map<String, Action> buttons;

	/**
	 * Map of key events and their actions.
	 */
	private final Map<Integer, Action> keyMappings;

	/**
	 * <code>true</code> iff this UI has the default buttons.
	 */
	private int buttonsType;
	
	/**
	 * Way to format the score.
	 */
	private ScoreFormatter scoreFormatter = null;

	/**
	 * Creates a new Pac-Man UI builder without any mapped keys or buttons.
	 */
	public PacManUiBuilder() {
		this.buttonsType = DEFAULT_BUTTONS;
		this.buttons = new LinkedHashMap<>();
		this.keyMappings = new HashMap<>();
	}

    private Launcher launcher;
    
	/**
	 * Creates a new Pac-Man UI with the set keys and buttons.
	 * 
	 * @param launcher
	 *            The launcher on to act with the UI.
	 * @return A new Pac-Man UI with the set keys and buttons.
	 */
	public PacManUI build(final Launcher launcher) {
		assert launcher != null;
        this.launcher = launcher;
        Game game = launcher.getGame();

        switch(buttonsType){
            case DEFAULT_BUTTONS:
                addStartButton(game);
                addStopButton(game);
                break;
            case ADVANCED_BUTTONS:
                addStartButton(game);
				addTwoPlayersButton(game);
				addinfiniteButton(game);
				addHunterButtons(game);
                addStopButton(game);
                break;
        }
		return new PacManUI(game, buttons, keyMappings, scoreFormatter);
	}

	private void addTwoPlayersButton(Game game) {
		assert game != null;

		buttons.put(START_CAPTION_TWO_PLAYERS, () -> launcher.makeGame(Launcher.TWO_PLAYERS).start());
	}


	private void addinfiniteButton(Game game) {
		assert game != null;

		buttons.put(START_CAPTION_INFINITE, () -> launcher.makeGame(Launcher.INFINITE_BOARD).start());
	}

	/**
     * Adds a button with the caption {@value #STOP_CAPTION} that stops the
     * game.
     *
     * @param game
     *            The game to stop.
     */
    private void addStopButton(final Game game) {
        assert game != null;

        buttons.put(STOP_CAPTION, () -> launcher.getGame().stop());
    }
    /**
     * Adds the buttons with the caption {@value #START_CAPTION_HUNTER_1P}, {@value #START_CAPTION_HUNTER_2P}, {@value #START_CAPTION_HUNTER_3P}, {@value #START_CAPTION_HUNTER_4P} that starts the
     * corresponding gamemode.
     *
     * @param game
     *            The game to setup.
     */
    private void addHunterButtons(final Game game) {
        assert game != null;

        buttons.put(START_CAPTION_HUNTER_1P, () -> launcher.makeGame(Launcher.MULTI_GHOST, 1).start());
        buttons.put(START_CAPTION_HUNTER_2P, () -> launcher.makeGame(Launcher.MULTI_GHOST, 2).start());
        buttons.put(START_CAPTION_HUNTER_3P, () -> launcher.makeGame(Launcher.MULTI_GHOST, 3).start());
        buttons.put(START_CAPTION_HUNTER_4P, () -> launcher.makeGame(Launcher.MULTI_GHOST, 4).start());
    }

	/**
	 * Adds a button with the caption {@value #START_CAPTION} that starts the
	 * game.
	 * 
	 * @param game
	 *            The game to start.
	 */
	private void addStartButton(final Game game) {
		assert game != null;

		buttons.put(START_CAPTION, () -> launcher.makeGame(Launcher.CLASSIC).start());
	}

	/**
	 * Adds a key listener to the UI.
	 * 
	 * @param keyCode
	 *            The key code of the key as used by {@link java.awt.event.KeyEvent}.
	 * @param action
	 *            The action to perform when the key is pressed.
	 * @return The builder.
	 */
	public PacManUiBuilder addKey(Integer keyCode, Action action) {
		assert keyCode != null;
		assert action != null;

		keyMappings.put(keyCode, action);
		return this;
	}

	/**
	 * Adds a button to the UI.
	 * 
	 * @param caption
	 *            The caption of the button.
	 * @param action
	 *            The action to execute when the button is clicked.
	 * @return The builder.
	 */
	public PacManUiBuilder addButton(String caption, Action action) {
		assert caption != null;
		assert !caption.isEmpty();
		assert action != null;

		buttons.put(caption, action);
		return this;
	}

	/**
	 * Adds a start and stop button to the UI. The actual actions for these
	 * buttons will be added upon building the UI.
	 * 
	 * @return The builder.
	 */
	public PacManUiBuilder withDefaultButtons() {
		buttonsType = DEFAULT_BUTTONS;
		buttons.put(START_CAPTION, null);
		buttons.put(STOP_CAPTION, null);
		return this;
	}
	
	public PacManUiBuilder withAdvancedButtons() {
		buttonsType = ADVANCED_BUTTONS;
		buttons.put(START_CAPTION, null);
		buttons.put(START_CAPTION_TWO_PLAYERS, null);
		buttons.put(START_CAPTION_INFINITE, null);
		buttons.put(START_CAPTION_HUNTER_1P, null);
		buttons.put(START_CAPTION_HUNTER_2P, null);
		buttons.put(START_CAPTION_HUNTER_3P, null);
		buttons.put(START_CAPTION_HUNTER_4P, null);
		buttons.put(STOP_CAPTION, null);
		return this;
	}
	
	/**
	 * Provide formatter for the score.
	 * 
	 * @param sf
	 *         The score formatter to be used.
	 * 
	 * @return The builder.
	 */
	public PacManUiBuilder withScoreFormatter(ScoreFormatter sf) {
		scoreFormatter = sf;
		return this;
	}
}
