package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;

/**
 * Created by helldog136 on 10/03/16.
 */
public class Menu extends SinglePlayerGame {
    /**
     * Create a new single player game for the provided level and player.
     *
     * @param l
     */
    protected Menu(Level l) {
        super(new PlayerFactory(new PacManSprites()).createPacMan(), l);
    }
}
