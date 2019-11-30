package solver.component;

import java.util.List;
import java.util.stream.Collectors;

import component.model.GameSquare;
import utility.util.Utility;

// TODO: write .contains() and equals() tests for this & Section. Not sure if we'll be keeping these classes so I didn't write tests..
/**
 * 
 * Example usage: Breaking up a {@link Section} into parts based on same odds 
 * 
 * @author Lionel Bergen
 */
public class ResultSet {
	private List<GameSquare> squares;
	private final int resultsEqual;
	
	public static final int UNKNOWN_VALUE = -1;
	
	public ResultSet(List<GameSquare> squares, int resultsEqual) {
		this.squares = squares; 
		this.resultsEqual = resultsEqual;
	}

	public List<GameSquare> getSquares() {
		return squares;
	}

	public void setSquares(List<GameSquare> squares) {
		this.squares = squares;
	}

	public int getResultsEqual() {
		return resultsEqual;
	}
	
	// Used for debugging
	@Override
	public String toString() {
		boolean allContainName = squares.stream().filter(e -> e.getName() == null).count() == 0;
		
		if (allContainName) {
			return squares.stream().map(e -> e.getName()).collect(Collectors.joining("+"));
		}
		
		return "#:" + resultsEqual + " " + Utility.sortList(squares).stream().map(Object::toString).collect(Collectors.joining(", "));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int hashCode = 1;
		
		for (GameSquare square : this.squares) {
			hashCode = hashCode * prime + square.hashCode();
		}
		
	    return hashCode;
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
	    
	    ResultSet otherResultSet = (ResultSet) other;
	    
	    boolean sizesAreEqual = this.squares.size() == otherResultSet.squares.size();
	    
	    if (!sizesAreEqual) {
	    	return false;
	    }
	    
	    // Don't care about order
	    return this.squares.containsAll(otherResultSet.squares);
	}
}
