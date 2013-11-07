package nl.tudelft.jpacman.board;


public interface Board {

	int getWidth();

	int getHeight();

	Square squareAt(int x, int y);

}
