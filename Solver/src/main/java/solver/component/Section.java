package solver.component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import component.model.GameSquare;
import utility.util.Utility;

/**
 * Basically just a Collection<GameSquare>, but with added features
 * 
 * @author Lionel Bergen
 */
public class Section {
	private Set<GameSquare> gameSquares = new HashSet<GameSquare>();
	
	public Section() {
	}
	
	public Section(Set<GameSquare> gameSquares) {
		this.gameSquares = gameSquares;
	}
	
	// TODO: remove
	public Section(Collection<GameSquare> gameSquares) {
		this.gameSquares = new HashSet<>(gameSquares);
	}
	
	public void add(GameSquare gameSquare) {
		gameSquares.add(gameSquare);
	}
	
	public Set<GameSquare> getGameSquares() {
		return this.gameSquares;
	}
	
	public void setGameSquares(Set<GameSquare> gameSquares) {
		this.gameSquares = gameSquares;
	}
	
	@Override
	public int hashCode() {
		final int prime = 92821;
		int hashCode = 1;
		
		for (GameSquare square : Utility.sortList(this.gameSquares)) {
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
	    
	    Section otherResultSet = (Section) other;
	    
	    boolean sizesAreEqual = this.gameSquares.size() == otherResultSet.gameSquares.size();
	    
	    if (!sizesAreEqual) {
	    	return false;
	    }
	    
	    // Don't care about order
	    return this.gameSquares.containsAll(otherResultSet.gameSquares);
	}
}
