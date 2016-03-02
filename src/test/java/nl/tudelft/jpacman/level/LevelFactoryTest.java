package nl.tudelft.jpacman.level;

import com.google.common.collect.Lists;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.npc.ghost.*;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by Angeall on 03/03/2016.
 */
public class LevelFactoryTest {

    /**
     * The GhostFactory linked with the LevelFactory
     */
    private GhostFactory ghostFactory;
    /**
     * The BoardFactory linked with the LevelFactory
     */
    private BoardFactory boardFactory;
    /**
     * The LevelFactory under test
     */
    private LevelFactory levelFactory;
    /**
     * A mock start position for the level to create
     */
    private Square square = mock(Square.class);
    /**
     * A mock NPC for the level to test
     */
    private NPC ghost = mock(NPC.class);
    /**
     * A square of the board of the level
     */
    private BasicSquare s0_0 = new BasicSquare();
    /**
     * A square of the board of the level
     */
    private BasicSquare s0_1 = new BasicSquare();
    /**
     * A square of the board of the level
     */
    private BasicSquare s0_2 = new BasicSquare();
    /**
     * The square grid of the board of the level
     */
    private Square[][] grid = new Square[][]{{s0_0, s0_1, s0_2}};

    /**
     * Cleans the factories before each test
     */
    @Before
    public void setUp(){
        PacManSprites sprites = mock(PacManSprites.class);
        boardFactory = new BoardFactory(sprites);
        ghostFactory = new GhostFactory(sprites);
        levelFactory = new LevelFactory(sprites, ghostFactory);
    }

    /**
     * Tests if the Level creator creates the good level
     */
    @Test
    public void testCreateLevel() throws Exception {
        Level level = levelFactory.createLevel(boardFactory.createBoard(grid), Lists.newArrayList(ghost),
                Lists.newArrayList(square));
        assertEquals(s0_0, level.getBoard().squareAt(0, 0));
        assertEquals(s0_1, level.getBoard().squareAt(0, 1));
        assertEquals(s0_2, level.getBoard().squareAt(0, 2));
    }

    /**
     * Tests if the infinite Level creator creates the food level
     */
    @Test
    public void testCreateInfiniteLevel() throws Exception {
        Level level = levelFactory.createInfiniteLevel(boardFactory.createInfiniteBoard(grid),
                Lists.newArrayList(ghost), Lists.newArrayList(square));
        assertEquals(s0_0, level.getBoard().squareAt(0, 0));
        assertEquals(s0_1, level.getBoard().squareAt(0, 1));
        assertEquals(s0_2, level.getBoard().squareAt(0, 2));
    }

    /**
     * Tests if the ghosts are properly created
     */
    @Test
    public void testCreateGhost() throws Exception {
        NPC blinky = levelFactory.createGhost();
        assertTrue(blinky instanceof Blinky);
        NPC inky = levelFactory.createGhost();
        assertTrue(inky instanceof Inky);
        NPC pinky = levelFactory.createGhost();
        assertTrue(pinky instanceof Pinky);
        NPC clyde = levelFactory.createGhost();
        assertTrue(clyde instanceof Clyde);
        blinky = levelFactory.createGhost();
        assertTrue(blinky instanceof Blinky);
        inky = levelFactory.createGhost();
        assertFalse(inky instanceof Blinky);
    }

    /**
     * Tests if the pellets are well created
     */
    @Test
    public void testCreatePellet() throws Exception {
        Pellet pellet = levelFactory.createPellet();
        assertEquals(LevelFactory.PELLET_VALUE, pellet.getValue());
    }
}