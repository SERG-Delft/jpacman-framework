package nl.tudelft.jpacman.ui;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.ui.ScorePanel.ScoreFormatter;

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
    private static final String START_CAPTION = "Start";

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
    private boolean defaultButtons;

    /**
     * Way to format the score.
     */
    private ScoreFormatter scoreFormatter = null;

    /**
     * Creates a new Pac-Man UI builder without any mapped keys or buttons.
     */
    public PacManUiBuilder() {
        this.defaultButtons = false;
        this.buttons = new LinkedHashMap<>();
        this.keyMappings = new HashMap<>();
    }

    /**
     * Creates a new Pac-Man UI with the set keys and buttons.
     *
     * @param game
     *            The game to build the UI for.
     * @return A new Pac-Man UI with the set keys and buttons.
     */
    public PacManUI build(final Game game) {
        assert game != null;

        if (defaultButtons) {
            addStartButton(game);
            addStopButton(game);
        }
        return new PacManUI(game, buttons, keyMappings, scoreFormatter);
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

        buttons.put(STOP_CAPTION, game::stop);
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

        buttons.put(START_CAPTION, game::start);
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
        defaultButtons = true;
        buttons.put(START_CAPTION, null);
        buttons.put(STOP_CAPTION, null);
        return this;
    }

    /**
     * Provide formatter for the score.
     *
     * @param scoreFormatter
     *         The score formatter to be used.
     *
     * @return The builder.
     */
    public PacManUiBuilder withScoreFormatter(ScoreFormatter scoreFormatter) {
        this.scoreFormatter = scoreFormatter;
        return this;
    }
}
