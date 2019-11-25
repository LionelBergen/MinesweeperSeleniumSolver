package solver.component;

import java.util.List;
import java.util.stream.Collectors;

import component.model.GameSquare;

// TODO: write .contains() and equals() tests for this & Section. Not sure if we'll be keeping these classes so I didn't write tests..
// TODO: Rename. Don't use 'set', maybe 'ResultSet'
/**
 * 
 * Example usage: Breaking up a {@link Section} into parts based on same odds 
 * 
 * @author Lionel Bergen
 */
public class SweeperSet {
	private List<GameSquare> squares;
	private final int resultsEqual;
	
	public SweeperSet(List<GameSquare> squares, int resultsEqual) {
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
		return "#:" + resultsEqual + " " + squares.stream().sorted((o1, o2) -> o1.compareTo(o2)).map(Object::toString).collect(Collectors.joining(", "));
	}
	
	@Override
	public int hashCode() {
		final int prime = 92821;
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
	    
	    SweeperSet otherResultSet = (SweeperSet) other;
	    
	    boolean sizesAreEqual = this.squares.size() == otherResultSet.squares.size();
	    
	    if (!sizesAreEqual) {
	    	return false;
	    }
	    
	    // Don't care about order
	    return this.squares.containsAll(otherResultSet.squares);
	}
}
