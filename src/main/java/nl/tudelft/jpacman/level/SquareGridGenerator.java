package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;

import java.util.ArrayList;

/**
 * Created by Anthony Rouneau on 02/03/2016.
 *
 * Generates a square grid in order to extend an infinite board.
 */
public class SquareGridGenerator {
    /**
     * Constant that represents the right side of the board
     */
    public static final int RIGHT = 0;
    /**
     * Constant that represents the left side of the board
     */
    public static final int LEFT = 2;
    /**
     * Constant that represents the top side of the board
     */
    public static final int TOP = 1;
    /**
     * Constant that represents the bottom side of the board
     */
    public static final int BOTTOM = 3;
    /**
     *
     */
    private static final int NUMBER_OF_SIDES = 4;
    /**
     * Constant that represents the width (horizontal characters) of the boards
     */
    private static final int GRID_WIDTH = 23;
    /**
     * Constant that represents the height (vertical characters) of the boards
     */
    private static final int GRID_HEIGHT = 21;
    /**
     * The board parser used to generate new grids
     */
    private final MapParser mapParser;

    /**
     * Creates a square grid generator
     */
    public SquareGridGenerator(){
        PacManSprites pacManSprites = new PacManSprites();
        BoardFactory boardFactory = new BoardFactory(pacManSprites);
        LevelFactory levelFactory = new LevelFactory(pacManSprites, new GhostFactory(pacManSprites));
        this.mapParser = new MapParser(levelFactory, boardFactory, true);
    }

    public final GridAndGhosts generateSquareGrid(int side, int currentWidth, int currentHeight) {
        assert side < NUMBER_OF_SIDES;
        int numberOfGridToAdd;
        ArrayList<NPC> ghosts = new ArrayList<>();
        Square[][] tempGrid = new Square[GRID_WIDTH][GRID_HEIGHT];
        Square[][] finalGrid;
        if(side % 2 == 0){
            numberOfGridToAdd = currentHeight/GRID_HEIGHT;
            finalGrid = new Square[GRID_WIDTH][numberOfGridToAdd*GRID_HEIGHT];
            for(int i = 1; i <= numberOfGridToAdd; i++){
                mapParser.makeProceduralGrid(tempGrid, ghosts);
                for(int x = 0; x < GRID_WIDTH; x++){
                    System.arraycopy(tempGrid[x], 0, finalGrid[x], (i - 1) * GRID_HEIGHT, i * GRID_HEIGHT - (i - 1) * GRID_HEIGHT);
                }
            }
        }
        else{
            numberOfGridToAdd = currentWidth/GRID_WIDTH;
            finalGrid = new Square[numberOfGridToAdd*GRID_WIDTH][GRID_HEIGHT];
            for(int i = 1; i <= numberOfGridToAdd; i++){
                mapParser.makeProceduralGrid(tempGrid, ghosts);
                for(int x = (i-1)*GRID_WIDTH; x < i*GRID_WIDTH; x++){
                    System.arraycopy(tempGrid[x - ((i - 1) * GRID_WIDTH)], 0, finalGrid[x], 0, GRID_HEIGHT);
                }
            }
        }
        return new GridAndGhosts(finalGrid, ghosts);
    }

    class GridAndGhosts{
        private final Square[][] grid;
        private final ArrayList<NPC> ghosts;

        public GridAndGhosts(Square[][] grid, ArrayList<NPC> ghosts){
            this.grid = grid;
            this.ghosts = ghosts;
        }

        public Square[][] getGrid() {
            return grid;
        }

        public ArrayList<NPC> getGhosts() {
            return ghosts;
        }
    }
}
