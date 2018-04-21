package nl.tudelft.jpacman.integration;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StartupSystemTest {

    private Launcher launcher;

    @BeforeEach
    public void before() {
        launcher = new Launcher();
    }

    @AfterEach
    public void after() {
        launcher.dispose();
    }

    @Test
    public void gameIsRunning() {
        launcher.launch();

        getGame().start();

        assertThat(getGame().isInProgress()).isTrue();
    }


    private Game getGame() {
        return launcher.getGame();
    }
}
