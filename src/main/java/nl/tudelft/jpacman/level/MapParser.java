package nl.tudelft.jpacman.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.NPC;

/**
 * Creates new {@link Level}s from text representations.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
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
	 * <lh>Supported characters:</lh>
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
	 * @throws IllegalArgumentException
	 *             when the amount of rows or columns is less than 1, the grid
	 *             isn't rectangular or consists unsupported characters.
	 */
	public Level parseMap(char[][] map) {
		int width = map.length;
		int height = map[0].length;

		Square[][] grid = new Square[width][height];

		// parse map
		List<NPC> ghosts = new ArrayList<>();
		List<Square> startPositions = new ArrayList<>();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				char c = map[x][y];
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
					Square ghostSquare = boardCreator.createGround();
					grid[x][y] = ghostSquare;
					NPC ghost = levelCreator.createGhost();
					ghosts.add(ghost);
					ghost.occupy(ghostSquare);
					break;
				case 'P':
					Square playerSquare = boardCreator.createGround();
					grid[x][y] = playerSquare;
					startPositions.add(playerSquare);
					break;
				default:
					throw new IllegalArgumentException("Invalid character at "
							+ x + "," + y + ": " + c);
				}
			}
		}
		Board board = boardCreator.createBoard(grid);
		return levelCreator.createLevel(board, ghosts, startPositions);
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
	 * @throws IllegalArgumentException
	 *             when the amount of lines is less than 1 or the lines are not
	 *             of equal size, or when the text contains unsupported
	 *             characters.
	 */
	public Level parseMap(List<String> text) {
		assert text != null;

		if (text.isEmpty()) {
			throw new IllegalArgumentException(
					"Input text must consist of at least 1 row.");
		}

		int height = text.size();
		int width = text.get(0).length();

		for (String line : text) {
			if (line.length() != width) {
				throw new IllegalArgumentException(
						"Input text lines are not of equal width.");
			}
		}

		char[][] map = new char[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				map[x][y] = text.get(y).charAt(x);
			}
		}
		return parseMap(map);
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
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				source))) {
			List<String> lines = new ArrayList<>();
			while (reader.ready()) {
				lines.add(reader.readLine());
			}
			return parseMap(lines);
		}
	}
}
