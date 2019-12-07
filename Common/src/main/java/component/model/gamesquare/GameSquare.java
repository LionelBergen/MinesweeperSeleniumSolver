package component.model.gamesquare;

import java.util.Comparator;

import component.model.RegularGameSquare;

public class GameSquare extends RegularGameSquare<GameSquare> {
	public GameSquare(String name, SquareValue value, int x, int y) {
		super(name, value, x, y);
	}
	
	public GameSquare(SquareValue value, int x, int y) {
		this(null, value, x, y);
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

	// TODO: write test
	public int compareTo(GameSquare o2) {
		return Comparator.comparingInt(GameSquare::getX).thenComparingInt(GameSquare::getY).thenComparing(GameSquare::getValue).compare(this, o2);
	}
}
