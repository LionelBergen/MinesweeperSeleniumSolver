package solver.component;

import java.util.ArrayList;
import java.util.Collection;

import component.model.gamesquare.GameSquare;

// TODO: write .contains() and equals() tests for this & Section. Not sure if we'll be keeping these classes so I didn't write tests..
/**
 * A class containing Squares, and the value the sum of the squares must equal
 * 
 * @author Lionel Bergen
 */
public class Rule {
	private final Collection<? extends GameSquare> squares;
	private final int resultsEqual;
	
	public static final int UNKNOWN_VALUE = -1;
	
	public Rule(Collection<GameSquare> squares, int resultsEqual) {
		this.squares = squares; 
		this.resultsEqual = resultsEqual;
	}

	public Collection<GameSquare> getSquares() {
		return new ArrayList<GameSquare>(squares);
	}

	public int getResultsEqual() {
		return resultsEqual;
	}
	
	// TODO: remove
	// Used for debugging
	/*@Override
	public String toString() {
		boolean allContainName = squares.stream().filter(e -> e.getName() == null).count() == 0;
		
		if (allContainName) {
			return squares.stream().map(e -> e.getName()).collect(Collectors.joining("+"));
		}
		
		return "#:" + resultsEqual + " " + Utility.sortList(squares).stream().map(Object::toString).collect(Collectors.joining(", "));
	}*/
	
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
	    
	    Rule otherResultSet = (Rule) other;
	    
	    boolean sizesAreEqual = this.squares.size() == otherResultSet.squares.size();
	    
	    if (!sizesAreEqual) {
	    	return false;
	    }
	    
	    // Don't care about order
	    return this.squares.containsAll(otherResultSet.squares);
	}
}
