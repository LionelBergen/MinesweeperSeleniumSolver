package solver.component.section;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import component.model.GameSquare;
import utility.util.Utility;

public abstract class GenericSection<T extends GameSquare> {
	private Set<T> gameSquares = new HashSet<>();
	
	protected GenericSection() {
	}
	
	protected GenericSection(Set<T> gameSquares) {
		this.gameSquares = gameSquares;
	}
	
	// TODO: remove
	public GenericSection(Collection<T> gameSquares) {
		this.gameSquares = new HashSet<>(gameSquares);
	}
	
	public void add(T gameSquare) {
		gameSquares.add(gameSquare);
	}
	
	public Set<T> getGameSquares() {
		return this.gameSquares;
	}
	
	public void setGameSquares(Set<T> gameSquares) {
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
	    
	    @SuppressWarnings("unchecked")
		GenericSection<T> otherResultSet = (GenericSection<T>) other;
	    
	    boolean sizesAreEqual = this.gameSquares.size() == otherResultSet.gameSquares.size();
	    
	    if (!sizesAreEqual) {
	    	return false;
	    }
	    
	    // Don't care about order
	    return this.gameSquares.containsAll(otherResultSet.gameSquares);
	}
}
