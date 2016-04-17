package nl.tudelft.jpacman.board;

public class Position {

	private  int x =0;
	private  int y=0;
	public Position() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Position(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "X = " +x + "  Y  = "+ y;
	}
	
}
