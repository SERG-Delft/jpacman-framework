package nl.tudelft.jpacman.level;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.InfiniteBoard;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;

/**
 * Creates new {@link Level}s from text representations.
 * 
 * @author Jeroen Roosen 
 */
public class MapParser {

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
	 * Creates a new map parser.
	 * 
	 * @param levelFactory
	 *            The factory providing the NPC objects and the level.
	 * @param boardFactory
	 *            The factory providing the Square objects and the board.
	 */
	public MapParser(LevelFactory levelFactory, BoardFactory boardFactory) {
		this.levelCreator = levelFactory;
		this.boardCreator = boardFactory;
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
	 * @param infinite
	 * 			  true if the level to create has to be an {@link InfiniteLevel}
	 * @return The level as represented by this text.
	 */
	public Level parseMap(char[][] map, boolean infinite) {
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
	 * 	using the {@link SquareLineGenerator}
	 * @param grid
	 * 				The grid where the Square will be added
	 * @param ghosts
	 * 				The ghosts list where a ghost could be added
     */
	public void makeProceduralGrid(Square[][] grid, List<NPC> ghosts) {
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
	 * The probability of having a Pellet = pP = 9/10 - pG
	 * The probability of having nothing = pN = 1 - pG - pP
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
		int randInt = randomizer.nextInt(1000);
		if(randInt < 5) {
			NPC ghost = levelCreator.createGhost();
			ghosts.add(ghost);
			ghost.occupy(square);
			grid[x][y] = square;
		}
		else if (randInt < 900){
			grid[x][y] = square;
			levelCreator.createPellet().occupy(square);
		}
	}

	private Square makeGhostSquare(List<NPC> ghosts) {
		Square ghostSquare = boardCreator.createGround();
		NPC ghost = levelCreator.createGhost();
		ghosts.add(ghost);
		ghost.occupy(ghostSquare);
		return ghostSquare;
	}

	/**
	 * Parses the list of strings into a 2-dimensional character array and
	 * passes it on to {@link #parseMap(char[][], boolean)}.
	 * 
	 * @param text
	 *            The plain text, with every entry in the list being a equally
	 *            sized row of squares on the board and the first element being
	 *            the top row.
	 * @return The level as represented by the text.
	 * @throws PacmanConfigurationException If text lines are not properly formatted.
	 */
	public Level parseMap(List<String> text, boolean infinite) {
		return parseMap(linesToChar(text), infinite);
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
	 * result to {@link #parseMap(List, boolean)}.
	 * 
	 * @param source
	 *            The input stream that will be read.
	 * @return The parsed level as represented by the text on the input stream.
	 * @throws IOException
	 *             when the source could not be read.
	 */
	public Level parseMap(InputStream source, boolean infinite) throws IOException {
		try {
            return parseMap(streamToLines(source), infinite);
		} catch (IOException exc){
            exc.printStackTrace();
        }
        return null;
	}

	private char[][] readRandomMapForInfiniteBoard() {
        int mapNbr = randomizer.nextInt(1) + 1;
		InputStream boardStream = MapParser.class.getResourceAsStream("/board_infinite" + mapNbr + ".txt");
        ArrayList<String> text;
        try {
            text = (ArrayList<String>) streamToLines(boardStream);
            return linesToChar(text);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> streamToLines(InputStream boardStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                boardStream, "UTF-8"));
        List<String> text = new ArrayList<>();
        while(reader.ready()) {
            text.add(reader.readLine());
        }
        return text;
    }

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
