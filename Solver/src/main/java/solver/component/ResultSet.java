package solver.component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import component.model.GameSquare;

public class ResultSet {
	// Use a Set since it doesn't allow duplicates
	private Set<GameSquare> gameSquares = new HashSet<GameSquare>();
	
	public void add(GameSquare gameSquare) {
		gameSquares.add(gameSquare);
	}
	
	public Set<GameSquare> getGameSquares() {
		return this.gameSquares;
	}
	
	public boolean isTouching(ResultSet resultSet) {
		return this.gameSquares.stream().anyMatch(e -> resultSet.gameSquares.contains(e));
	}
	
	@Override
	public int hashCode() {
		final int prime = 92821;
		int hashCode = 1;
		
		for (GameSquare square : this.gameSquares) {
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
	    
	    boolean sizesAreEqual = this.gameSquares.size() == otherResultSet.gameSquares.size();
	    
	    if (!sizesAreEqual) {
	    	return false;
	    }
	    
	    return this.gameSquares.equals(otherResultSet.gameSquares);
	}
}
