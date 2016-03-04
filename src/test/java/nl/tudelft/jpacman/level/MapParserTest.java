package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.InfiniteBoard;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.npc.ghost.Blinky;
import nl.tudelft.jpacman.npc.ghost.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.npc.ghost.Navigation;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by Angeall on 03/03/2016.
 */
public class MapParserTest {

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
     * The player factory used in the tests
     */
    private PlayerFactory playerFactory;
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
     * The map parser under test
     */
    private MapParser mapParser;

    @Before
    public void setUp(){
        PacManSprites sprites = mock(PacManSprites.class);
        boardFactory = new BoardFactory(sprites);
        ghostFactory = new GhostFactory(sprites);
        levelFactory = new LevelFactory(sprites, ghostFactory);
        playerFactory = new PlayerFactory(new PacManSprites());
        mapParser = new MapParser(levelFactory, boardFactory);
    }

    @After
    public void tearDown(){
        Navigation.playerList = new ArrayList<>();
    }

    /**
     * Verifies if the level / board is created properly
     */
    @Test
    public void testParseMap() throws Exception {
        String str = "####\n" +
                     "#..#\n" +
                     "#PG#\n" +
                     "#P.#\n" +
                     "####\n";
        InputStream stream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
        Level level = mapParser.parseMap(stream, false);
        List<Unit> units = level.getBoard().squareAt(1, 1).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Pellet); // Tests the position of a pellet
        units = level.getBoard().squareAt(2, 2).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Ghost); // Tests the position of a Ghost
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);
        player = playerFactory.createPacMan();
        level.registerPlayer(player);
        units = level.getBoard().squareAt(1, 2).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Player); // Tests the position of a Player
        units = level.getBoard().squareAt(1, 3).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Player); // Tests the position of a Player
        assertFalse(level.getBoard().squareAt(0, 3).isAccessibleTo(player)); // A wall should be inaccessible
    }

    /**
     * Verifies if the infinite level / board is created properly
     */
    @Test
    public void testParseMapToInfinite() throws Exception {
        String str = "####\n" +
                     "#..#\n" +
                     "#PG#\n" +
                     "#P.#\n" +
                     "####\n";
        InputStream stream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
        InfiniteLevel level =(InfiniteLevel) mapParser.parseMap(stream, true);
        List<Unit> units = level.getBoard().squareAt(1, 1).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Pellet); // Tests the position of a pellet
        units = ((InfiniteBoard)level.getBoard()).squareAtUnchecked(2, 2).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Ghost); // Tests the position of a Ghost
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);
        player = playerFactory.createPacMan();
        level.registerPlayer(player);
        units = ((InfiniteBoard)level.getBoard()).squareAtUnchecked(1, 2).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Player); // Tests the position of a Player
        units = ((InfiniteBoard)level.getBoard()).squareAtUnchecked(1, 3).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Player); // Tests the position of a Player
        assertFalse(level.getBoard().squareAt(0, 3).isAccessibleTo(player)); // A wall should be inaccessible
    }

    /**
     * Verifies if the level / board is created properly
     */
    @Test
    public void testParseMap1() throws Exception {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("####");
        strings.add("#..#");
        strings.add("#PG#");
        strings.add("#P.#");
        strings.add("####");
        Level level = mapParser.parseMap(strings, false);
        List<Unit> units = level.getBoard().squareAt(1, 1).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Pellet); // Tests the position of a pellet
        units = level.getBoard().squareAt(2, 2).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Ghost); // Tests the position of a Ghost
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);
        player = playerFactory.createPacMan();
        level.registerPlayer(player);
        units = level.getBoard().squareAt(1, 2).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Player); // Tests the position of a Player
        units = level.getBoard().squareAt(1, 3).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Player); // Tests the position of a Player
        assertFalse(level.getBoard().squareAt(0, 3).isAccessibleTo(player)); // A wall should be inaccessible
    }

    /**
     * Verifies if the infinite level / board is created properly
     */
    @Test
    public void testParseMapToInfinite1() throws Exception {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("####");
        strings.add("#..#");
        strings.add("#PG#");
        strings.add("#P.#");
        strings.add("####");
        InfiniteLevel level = (InfiniteLevel) mapParser.parseMap(strings, true);
        List<Unit> units = level.getBoard().squareAt(1, 1).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Pellet); // Tests the position of a pellet
        units = ((InfiniteBoard)level.getBoard()).squareAtUnchecked(2, 2).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Ghost); // Tests the position of a Ghost
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);
        player = playerFactory.createPacMan();
        level.registerPlayer(player);
        units = ((InfiniteBoard)level.getBoard()).squareAtUnchecked(1, 2).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Player); // Tests the position of a Player
        units = ((InfiniteBoard)level.getBoard()).squareAtUnchecked(1, 3).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Player); // Tests the position of a Player
        assertFalse(level.getBoard().squareAt(0, 3).isAccessibleTo(player)); // A wall should be inaccessible
    }

    /**
     * Verifies if the level / board is created properly
     */
    @Test
    public void testParseMap2() throws Exception {
        char[][] map = new char[][]{{'#','#','#','#','#'}, {'#', '.', 'P', 'P', '#'}, {'#', '.', 'G', '.' , '#'},
                                    {'#', '#', '#', '#', '#'}};
        Level level = mapParser.parseMap(map, false);
        List<Unit> units = level.getBoard().squareAt(1, 1).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Pellet); // Tests the position of a pellet
        units = level.getBoard().squareAt(2, 2).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Ghost); // Tests the position of a Ghost
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);
        player = playerFactory.createPacMan();
        level.registerPlayer(player);
        units = level.getBoard().squareAt(1, 2).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Player); // Tests the position of a Player
        units = level.getBoard().squareAt(1, 3).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Player); // Tests the position of a Player
        assertFalse(level.getBoard().squareAt(0, 3).isAccessibleTo(player)); // A wall should be inaccessible
    }

    /**
     * Verifies if the infinite level / board is created properly
     */
    @Test
    public void testParseMapToInfinite2() throws Exception {
        char[][] map = new char[][]{{'#','#','#','#','#'}, {'#', '.', 'P', 'P', '#'}, {'#', '.', 'G', '.' , '#'},
                {'#', '#', '#', '#', '#'}};
        InfiniteLevel level = (InfiniteLevel) mapParser.parseMap(map, true);
        List<Unit> units = level.getBoard().squareAt(1, 1).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Pellet); // Tests the position of a pellet
        units = ((InfiniteBoard)level.getBoard()).squareAtUnchecked(2, 2).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Ghost); // Tests the position of a Ghost
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);
        player = playerFactory.createPacMan();
        level.registerPlayer(player);
        units = ((InfiniteBoard)level.getBoard()).squareAtUnchecked(1, 2).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Player); // Tests the position of a Player
        units = ((InfiniteBoard)level.getBoard()).squareAtUnchecked(1, 3).getOccupants();
        assertTrue(units.size() == 1 && units.get(0) instanceof Player); // Tests the position of a Player
        assertFalse(level.getBoard().squareAt(0, 3).isAccessibleTo(player)); // A wall should be inaccessible
    }
}