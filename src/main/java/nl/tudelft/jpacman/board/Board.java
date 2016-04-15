package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.level.Level;

/**
 * A top-down view of a matrix of {@link Square}s.
 * 
 * @author Jeroen Roosen 
 */
public class Board {

	/**
	 * The grid of squares with board[x][y] being the square at column x, row y.
	 */
	private Square[][] board;

	/**
	 * Creates a new board.
	 * 
	 * @param grid
	 *            The grid of squares with grid[x][y] being the square at column
	 *            x, row y.
	 */
	Board(Square[][] grid) {
		assert grid != null;
		this.board = grid;
		assert invariant() : "Initial grid cannot contain null squares";
	}
	
	/**
	 * Whatever happens, the squares on the board can't be null.
	 * @return false if any square on the board is null.
	 */
	protected final boolean invariant() {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				if (board[x][y] == null) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Returns the number of columns.
	 * 
	 * @return The width of this board.
	 */
	public int getWidth() {
		return board.length;
	}

	/**
	 * Returns the number of rows.
	 * 
	 * @return The height of this board.
	 */
	public int getHeight() {
		return board[0].length;
	}

	/**
	 * Returns the square at the given <code>x,y</code> position.
	 * 
	 * @param x
	 *            The <code>x</code> position (column) of the requested square.
	 * @param y
	 *            The <code>y</code> position (row) of the requested square.
	 * @return The square at the given <code>x,y</code> position (never null).
	 */
	public Square squareAt(int x, int y) {
		assert withinBorders(x, y);
		Square result = board[x][y];
		assert result != null : "Follows from invariant.";
		return result;
	}

	/**
	 * Determines whether the given <code>x,y</code> position is on this board.
	 * 
	 * @param x
	 *            The <code>x</code> position (row) to test.
	 * @param y
	 *            The <code>y</code> position (column) to test.
	 * @return <code>true</code> iff the position is on this board.
	 */
	public boolean withinBorders(int x, int y) {
		return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
	}

	public void extend(Direction direction)
	{
		Square[][] newGrid = null;
		int extendScale = 5;
		if(direction == Direction.EAST || direction == Direction.WEST)
		{
			extendScale = this.getWidth();
		}
		else
		{
			extendScale = this.getHeight();
		}
		newGrid = new Square[this.getWidth() + Math.abs(direction.getDeltaX()*extendScale)][this.getHeight() + Math.abs(direction.getDeltaY()*extendScale)];
		if(direction == Direction.EAST)
		{
			this.boardCopy(this.board, newGrid, 0, 0);
			this.createSquare(newGrid, this.getWidth(), 0, this.getWidth()+extendScale, this.getHeight());
		}
		else if(direction == Direction.NORTH)
		{
			this.boardCopy(this.board, newGrid, 0, extendScale);
			this.createSquare(newGrid, 0, 0, this.getWidth(), extendScale);
		}
		else if(direction == Direction.SOUTH)
		{
			this.boardCopy(this.board, newGrid, 0, 0);
			this.createSquare(newGrid, 0, this.getHeight(), this.getWidth(), this.getHeight()+extendScale);
		}
		else
		{
			this.boardCopy(this.board, newGrid, extendScale, 0);
			this.createSquare(newGrid, 0, 0, extendScale, this.getHeight());
		}
		this.setPositions(newGrid);
		this.board = newGrid;
	}

	private void boardCopy(Square[][] originalBoard, Square[][] newBoard, int width, int height)
	{
		for(int i = 0; i < originalBoard.length; i++)
		{
			for(int j = 0; j < originalBoard[0].length; j++)
			{
				newBoard[i + width][j + height] = originalBoard[i][j];
			}
		}
	}

	private void setPositions(Square[][] grid)
	{
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[0].length; j++)
			{
				grid[i][j].setCoord(i, j);
			}
		}
	}

	private void createSquare(Square[][] grid, int startX, int startY, int endX, int endY)
	{
		for(int i = 0; i < (endX - startX)/this.getWidth(); i++)
		{
			for(int j = 0; j < (endY - startY)/this.getHeight(); j++)
			{
				Level lev = Launcher.getLauncher().makeLevel();
				Square[][] board = lev.getBoard().board;
				for(int k = 0; k < board.length; k++)
				{
					for(int l = 0; l < board[0].length; l++)
					{
						grid[startX+(i*this.getWidth())+k][startY+(j*this.getHeight())+l] = board[i][j];
					}
				}
			}
		}
	}
}
