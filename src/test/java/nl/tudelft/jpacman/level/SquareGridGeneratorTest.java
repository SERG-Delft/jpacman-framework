package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Angeall on 10/03/2016.
 *
 * Test for the square grid generator Class
 */
public class SquareGridGeneratorTest {

    /**
     * Verifies that the square grid generator generates well-sized grid to add to the infinite board
     */
    @Test
    public void testGenerateSquareGrid() throws Exception {
        MapParser mapParser = new MapParser(new LevelFactory(new PacManSprites(), new GhostFactory(new PacManSprites())),
                                            new BoardFactory(new PacManSprites()), true);
        InfiniteLevel level = (InfiniteLevel) mapParser.parseMap(MapParser.class.getResourceAsStream("/board.txt"));
        SquareGridGenerator squareGridGenerator = new SquareGridGenerator();
        level.getInfiniteBoard().addGridBottom(squareGridGenerator
                .generateSquareGrid(SquareGridGenerator.BOTTOM,
                                    level.getInfiniteBoard().getCurrentWidth(),
                                    level.getInfiniteBoard().getCurrentHeight()).getGrid());
        assertEquals(21*2, level.getInfiniteBoard().getCurrentHeight());
        level.getInfiniteBoard().addGridLeft(squareGridGenerator
                .generateSquareGrid(SquareGridGenerator.LEFT,
                                    level.getInfiniteBoard().getCurrentWidth(),
                                    level.getInfiniteBoard().getCurrentHeight()).getGrid());
        assertEquals(23*2, level.getInfiniteBoard().getCurrentWidth());
        level.getInfiniteBoard().addGridBottom(squareGridGenerator
                .generateSquareGrid(SquareGridGenerator.BOTTOM,
                                    level.getInfiniteBoard().getCurrentWidth(),
                                    level.getInfiniteBoard().getCurrentHeight()).getGrid());
        assertEquals(21*3, level.getInfiniteBoard().getCurrentHeight());
        level.getInfiniteBoard().addGridRight(squareGridGenerator
                .generateSquareGrid(SquareGridGenerator.RIGHT,
                                    level.getInfiniteBoard().getCurrentWidth(),
                                    level.getInfiniteBoard().getCurrentHeight()).getGrid());
        assertEquals(23*3, level.getInfiniteBoard().getCurrentWidth());
        level.getInfiniteBoard().addGridTop(squareGridGenerator
                .generateSquareGrid(SquareGridGenerator.TOP,
                                    level.getInfiniteBoard().getCurrentWidth(),
                                    level.getInfiniteBoard().getCurrentHeight()).getGrid());
        assertEquals(21*4, level.getInfiniteBoard().getCurrentHeight());
        level.getInfiniteBoard().addGridRight(squareGridGenerator
                .generateSquareGrid(SquareGridGenerator.RIGHT,
                                    level.getInfiniteBoard().getCurrentWidth(),
                                    level.getInfiniteBoard().getCurrentHeight()).getGrid());
        assertEquals(23*4, level.getInfiniteBoard().getCurrentWidth());
        level.getInfiniteBoard().addGridTop(squareGridGenerator
                .generateSquareGrid(SquareGridGenerator.TOP,
                        level.getInfiniteBoard().getCurrentWidth(),
                        level.getInfiniteBoard().getCurrentHeight()).getGrid());
        assertEquals(21*5, level.getInfiniteBoard().getCurrentHeight());
        level.getInfiniteBoard().addGridLeft(squareGridGenerator
                .generateSquareGrid(SquareGridGenerator.LEFT,
                        level.getInfiniteBoard().getCurrentWidth(),
                        level.getInfiniteBoard().getCurrentHeight()).getGrid());
        assertEquals(23*5, level.getInfiniteBoard().getCurrentWidth());
    }
}