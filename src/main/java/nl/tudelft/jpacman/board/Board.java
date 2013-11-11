package nl.tudelft.jpacman.board;

/**
 * A top-down view of a matrix of {@link Square}s.
 * 
 * @author Jeroen Roosen <j.roosen@student.tudelft.nl>
 */
public interface Board {

	/**
	 * Returns the width of this board, i.e. the amount of columns.
	 * @return The width of this board.
	 */
	int getWidth();

	/**
	 * Returns the height of this board, i.e. the amount of rows.
	 * @return The height of this board.
	 */
	int getHeight();

	/**
	 * Returns the square at the given <code>x,y</code> position.
	 * @param x The <code>x</code> position (column) of the requested square.
	 * @param y The <code>y</code> position (row) of the requested square.
	 * @return The square at the given <code>x,y</code> position.
	 */
	Square squareAt(int x, int y);

}
