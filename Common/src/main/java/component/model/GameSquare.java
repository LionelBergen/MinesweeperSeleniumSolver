package component.model;

import java.util.Comparator;

public class GameSquare {
	private SquareValue value;
	private int x;
	private int y;
	
	// Used for debugging / testing
	private String name;
	
	public GameSquare(String name, SquareValue value, int x, int y) {
		this.name = name;
		this.value = value;
		this.x = x;
		this.y = y;
	}
	
	public GameSquare(SquareValue value, int x, int y) {
		this(null, value, x, y);
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

	public GameSquare setValue(SquareValue value) {
		this.value = value;
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return name == null ? (x + " " + y + " " + value) : name;
	}
	
	@Override
	public int hashCode() {
		final int primeNumber = 31;
		
	    return primeNumber * x + (primeNumber * y) + (primeNumber * value.hashCode());
	}

	// TODO: write test
	public int compareTo(GameSquare o2) {
		return Comparator.comparingInt(GameSquare::getX).thenComparingInt(GameSquare::getY).thenComparing(GameSquare::getValue).compare(this, o2);
	}
}
