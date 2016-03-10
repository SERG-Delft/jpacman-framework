package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.InfiniteBoard;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Creates new {@link Level}s from text representations.
 * 
 * @author Jeroen Roosen 
 */
public class MapParser {
    /**
     * The sum of all probability for generating squares into a new map
     */
	private static final int PROCEDURAL_TOTAL_PROBABILITY = 1000;
    /**
     * The probability, on PROCEDURAL_TOTAL_PROBABILITY to put a ghost on a generated square
     */
	private static final int PROCEDURAL_GHOST_PROBABILITY = 10;
	/**
	 * The factory that creates the levels.
	 */
	private final LevelFactory levelCreator;

	/**
	 * The factory that creates the squares and board.
	 */
	private final BoardFactory boardCreator;

	/**
	 * Random number generator to generate procedural squares
	 */
	private final Random randomizer = new Random();

    /**
     * Boolean true if the level to create has to be an {@link InfiniteLevel}
     */
    private boolean infinite;

    /**
	 * Creates a new map parser.
	 * 
	 * @param levelFactory
	 *            The factory providing the NPC objects and the level.
	 * @param boardFactory
	 *            The factory providing the Square objects and the board.
	 */
	public MapParser(LevelFactory levelFactory, BoardFactory boardFactory, boolean infinite) {
		this.levelCreator = levelFactory;
		this.boardCreator = boardFactory;
        this.infinite = infinite;
	}

	/**
	 * Parses the text representation of the board into an actual level.
	 * 
	 * <ul>
	 * <li>Supported characters:
	 * <li>' ' (space) an empty square.
	 * <li>'#' (bracket) a wall.
	 * <li>'.' (period) a square with a pellet.
	 * <li>'P' (capital P) a starting square for players.
	 * <li>'G' (capital G) a square with a ghost.
	 * </ul>
	 * 
	 * @param map
	 *            The text representation of the board, with map[x][y]
	 *            representing the square at position x,y.
	 * @return The level as represented by this text.
	 */
	public Level parseMap(char[][] map) {
		int width = map.length;
		int height = map[0].length;

		Square[][] grid = new Square[width][height];

		List<NPC> ghosts = new ArrayList<>();
		List<Square> startPositions = new ArrayList<>();

		makeGrid(map, width, height, grid, ghosts, startPositions);

		if(infinite){
			InfiniteBoard board = boardCreator.createInfiniteBoard(grid);
			return levelCreator.createInfiniteLevel(board, ghosts, startPositions);
		}
		Board board = boardCreator.createBoard(grid);
		return levelCreator.createLevel(board, ghosts, startPositions);
	}

	/**
	 * Construct the grid with the characters map
	 * @param map
	 * 				The map represented with a matrix of characters
	 * @param width
	 * 				The width of the grid
	 * @param height
	 * 				The height of the grid
	 * @param grid
	 * 				The grid where the Square will be added
	 * @param ghosts
	 * 				The ghosts list where a ghost could be added
	 * @param startPositions
	 * 				The list of positions where the player(s) can start the game
     */
	private void makeGrid(char[][] map, int width, int height,
			Square[][] grid, List<NPC> ghosts, List<Square> startPositions) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				char c = map[x][y];
				addSquare(grid, ghosts, startPositions, x, y, c);
			}
		}
	}

	/**
	 * Construct a grid with the characters map. This grid is made to add to an {@link InfiniteBoard}
	 * 	using the {@link SquareGridGenerator}
	 * @param grid
	 * 				The grid where the Square will be added
	 * @param ghosts
	 * 				The ghosts list where a ghost could be added
     */
	public final void makeProceduralGrid(Square[][] grid, List<NPC> ghosts) {
        char[][] map = readRandomMapForInfiniteBoard();
		if(map != null) {
            for (int x = 0; x < map.length; x++) {
                for (int y = 0; y < map[0].length; y++) {
                    char c = map[x][y];
                    addSquareProcedural(grid, ghosts, x, y, c);
                }
            }
        }
    }

	/**
	 * Add a new square to the grid. Its type depends on c.
	 * @param grid
	 * 				The grid where the Square will be added
	 * @param ghosts
	 * 				The ghosts list where a ghost could be added
	 * @param startPositions
	 * 				The list of positions where the player(s) can start the game
	 * @param x
	 * 				The horizontal coordinate in the grid to add the square
	 * @param y
	 * 				The vertical coordinate in the grid to add the square
	 * @param c
	 * 				The character that will define the type of square to add
	 */
	private void addSquare(Square[][] grid, List<NPC> ghosts,
			List<Square> startPositions, int x, int y, char c) {
		switch (c) {
		case ' ':
			grid[x][y] = boardCreator.createGround();
			break;
		case '#':
			grid[x][y] = boardCreator.createWall();
			break;
		case '.':
			Square pelletSquare = boardCreator.createGround();
			grid[x][y] = pelletSquare;
			levelCreator.createPellet().occupy(pelletSquare);
			break;
		case 'G':
			Square ghostSquare = makeGhostSquare(ghosts);
			grid[x][y] = ghostSquare;
			break;
		case 'P':
			Square playerSquare = boardCreator.createGround();
			grid[x][y] = playerSquare;
			startPositions.add(playerSquare);
			break;
		default:
			throw new PacmanConfigurationException("Invalid character at "
					+ x + "," + y + ": " + c);
		}
	}

	/**
	 * Add a new square to the grid. Its type depends on c.
	 * @param grid
	 * 				The grid where the Square will be added
	 * @param ghosts
	 * 				The ghosts list where a ghost could be added
	 * @param x
	 * 				The horizontal coordinate in the grid to add the square
	 * @param y
	 * 				The vertical coordinate in the grid to add the square
	 * @param c
	 * 				The character that will define the type of square to add
	 */
	private void addSquareProcedural(Square[][] grid, List<NPC> ghosts,
									int x, int y, char c) {
		switch (c) {
			case '#':
				grid[x][y] = boardCreator.createWall();
				break;
			case '.':
				generateAccessibleSquare(grid, ghosts, x, y);
				break;
			case ' ':
				generateAccessibleSquare(grid, ghosts, x, y);
				break;
			case 'P':
				generateAccessibleSquare(grid, ghosts, x, y);
				break;
			case 'G':
				generateAccessibleSquare(grid, ghosts, x, y);
				break;
			default:
				throw new PacmanConfigurationException("Invalid character at "
						+ x + "," + y + ": " + c);
		}
	}

	/**
	 * Add a {@link Square} with either a {@link Pellet}, a {@link NPC} or nothing.
	 * The probability of having a Ghost = pG = 1/200
	 * The probability of having a Pellet = pP = 1 - pG
	 * @param grid
	 * 				The grid where the Square will be added
	 * @param ghosts
	 * 				The ghosts list where a ghost could be added
	 * @param x
	 * 				The horizontal coordinate in the grid to add the square
     * @param y
	 * 				The vertical coordinate in the grid to add the square
     */
	private void generateAccessibleSquare(Square[][] grid, List<NPC> ghosts, int x, int y) {
		Square square = boardCreator.createGround();
		int randInt = randomizer.nextInt(PROCEDURAL_TOTAL_PROBABILITY);
		if(randInt < PROCEDURAL_GHOST_PROBABILITY) {
			NPC ghost = levelCreator.createGhostForInfiniteBoard();
			ghosts.add(ghost);
			ghost.occupy(square);
		}
		else {
			levelCreator.createPellet().occupy(square);
		}
        grid[x][y] = square;
	}

    /**
     * Make a square with a ghost inside of it
     * @param ghosts
     *              The list in which the new ghost will be added
     * @return The square where the ghost has been put
     */
	private Square makeGhostSquare(List<NPC> ghosts) {
		Square ghostSquare = boardCreator.createGround();
        NPC ghost;
        if(!infinite) {
            ghost = levelCreator.createGhost();
        }
        else{
            ghost = levelCreator.createGhostForInfiniteBoard();
        }
		ghosts.add(ghost);
		ghost.occupy(ghostSquare);
		return ghostSquare;
	}

	/**
	 * Parses the list of strings into a 2-dimensional character array and
	 * passes it on to {@link #parseMap(char[][])}.
	 * 
	 * @param text
	 *            The plain text, with every entry in the list being a equally
	 *            sized row of squares on the board and the first element being
	 *            the top row.
	 * @return The level as represented by the text.
	 * @throws PacmanConfigurationException If text lines are not properly formatted.
	 */
	public Level parseMap(List<String> text) {
		return parseMap(linesToChar(text));
	}
	
	/**
	 * Check the correctness of the map lines in the text.
	 * @param text Map to be checked
	 * @throws PacmanConfigurationException if map is not OK.
	 */
	private void checkMapFormat(List<String> text) {	
		if (text == null) {
			throw new PacmanConfigurationException(
					"Input text cannot be null.");
		}

		if (text.isEmpty()) {
			throw new PacmanConfigurationException(
					"Input text must consist of at least 1 row.");
		}

		int width = text.get(0).length();

		if (width == 0) {
			throw new PacmanConfigurationException(
				"Input text lines cannot be empty.");
		}

		for (String line : text) {
			if (line.length() != width) {
				throw new PacmanConfigurationException(
					"Input text lines are not of equal width.");
			}
		}		
	}

	/**
	 * Parses the provided input stream as a character stream and passes it
	 * result to {@link #parseMap(List)}.
	 * 
	 * @param source
	 *            The input stream that will be read.
	 * @return The parsed level as represented by the text on the input stream.
	 * @throws IOException
	 *             when the source could not be read.
	 */
	public Level parseMap(InputStream source) throws IOException {
        return parseMap(streamToLines(source));
    }

    /**
     * Read a random model to add to an infinite board
     * @return a matrix of characters representing the map read.
     */
	private char[][] readRandomMapForInfiniteBoard() {
        int mapNbr = randomizer.nextInt(5) + 1;
		InputStream boardStream = Launcher.class.getResourceAsStream("/board_infinite_" + mapNbr + ".txt");
        ArrayList<String> text;
        try {
            text = (ArrayList<String>) streamToLines(boardStream);
            return linesToChar(text);

        } catch (IOException e) {
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(java.util.logging.Level.SEVERE,
					"Infinite map model could not be found");
		}
        return null;
    }

    /**
     * Parse an input stream into a list of Strings
     * @param boardStream
     *                  The input stream to parse into Strings
     * @return A list of strings representing the columns of the map
     * @throws IOException if the map could not be found or read
     */
    private List<String> streamToLines(InputStream boardStream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(boardStream, "UTF-8"));
        List<String> text = new ArrayList<>();
        while(reader.ready()) {
            text.add(reader.readLine());
        }
        return text;
    }

    /**
     * Parse a list of strings into a matrix of characters
     * @param text
     *              The list of strings representing the map
     * @return A matrix of characters representing the map
     */
    private char[][] linesToChar(List<String> text){
        checkMapFormat(text);

        int height = text.size();
        int width = text.get(0).length();

        char[][] map = new char[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = text.get(y).charAt(x);
            }
        }
        return map;
    }
}
