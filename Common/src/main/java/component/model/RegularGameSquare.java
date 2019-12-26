package component.model;

import java.util.Comparator;

import component.model.gamesquare.SquareValue;

public class RegularGameSquare<T extends RegularGameSquare<T>> {
	private SquareValue value;
	private int x;
	private int y;
	
	// Used for debugging / testing
	private String name;
	
	public RegularGameSquare(String name, SquareValue value, int x, int y) {
		this.value = value;
		this.x = x;
		this.y = y;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public RegularGameSquare<T> setValue(SquareValue value) {
		this.value = value;
		return this;
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
	    
	    @SuppressWarnings("unchecked")
	    RegularGameSquare<T> otherSquare = (RegularGameSquare<T>) other;
	    
	    // field comparison
	    return otherSquare.getX() == this.getX() && this.getY() == otherSquare.getY() && this.getValue() == otherSquare.getValue();
	}
	
	@Override
	public String toString() {
		if (name != null && !name.isEmpty()) {
			return name;
		} else {
			return value.toString();
		}
	}
	
	@Override
	public int hashCode() {
		final int primeNumber = 31;
		
	    return primeNumber * x + (primeNumber * y) + (primeNumber * value.hashCode());
	}

	// TODO: write test
	public int compareTo(RegularGameSquare<T> o2) {
		return Comparator.comparingInt(RegularGameSquare<T>::getX).thenComparingInt(RegularGameSquare::getY).thenComparing(RegularGameSquare::getValue).compare(this, o2);
	}
}
