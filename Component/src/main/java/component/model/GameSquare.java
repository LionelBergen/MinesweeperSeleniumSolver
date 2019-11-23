package component.model;

public class GameSquare {
	private SquareValue value;
	private int x;
	private int y;
	
	public GameSquare(SquareValue value, int x, int y) {
		this.value = value;
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

	public SquareValue getValue() {
		return value;
	}

	public void setValue(SquareValue value) {
		this.value = value;
	}
}
