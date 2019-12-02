package solver.component.section;

import java.util.Set;

import component.model.GameSquare;
import utility.util.Utility;

/**
 * Basically just a Collection<GameSquare>, but with added features
 * 
 * @author Lionel Bergen
 */
public abstract class GenericSection<T extends GameSquare> {
	public abstract void add(T gameSquare);
	
	public abstract Set<T> getGameSquares();
	
	public abstract void setGameSquares(Set<T> gameSquares);
	
	@Override
	public int hashCode() {
		final int prime = 92821;
		int hashCode = 1;
		
		for (GameSquare square : Utility.sortList(this.getGameSquares())) {
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
	    
	    boolean sizesAreEqual = this.getGameSquares().size() == otherResultSet.getGameSquares().size();
	    
	    if (!sizesAreEqual) {
	    	return false;
	    }
	    
	    // Don't care about order
	    return this.getGameSquares().containsAll(otherResultSet.getGameSquares());
	}
}
