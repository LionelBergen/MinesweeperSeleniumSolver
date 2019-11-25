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
	
	@Override
	public boolean equals(Object other) {
	    // self check
	    if (this == other) {
	        return true;
	    }
	    
	    // null check
	    if (other == null) {
	        return false;
	    }
	    
	    // type check and cast
	    if (getClass() != other.getClass()) {
	        return false;
	    }
	    
	    GameSquare otherSquare = (GameSquare) other;
	    
	    // field comparison
	    return otherSquare.getX() == this.getX() && this.getY() == otherSquare.getY() && this.getValue() == otherSquare.getValue();
	}
	
	@Override
	public String toString() {
		return x + " " + y + " " + value;
	}
	
	// TODO: Two squares with same X,Y different value will contain same hashcode. 
	// Won't ever happen in our program, but should be fixed (GameSquare should make sense on its own)
	@Override
	public int hashCode() {
		final int primeNumber = 31;
		
	    return primeNumber * x + (primeNumber * y);
	}
}