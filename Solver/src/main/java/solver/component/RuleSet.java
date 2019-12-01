package solver.component;

import java.util.List;

/**
 * A class containing a set of rules
 * 
 * @author Lionel Bergen
 */
public class RuleSet {
	private List<Rule> resultSets;
	private final int hashCode;
	
	public RuleSet(List<Rule> resultSets) {
		this.resultSets = resultSets;
		this.hashCode = getHashCode(resultSets);
	}

	public List<Rule> getResultSets() {
		return resultSets;
	}

	public void setResultSets(List<Rule> resultSets) {
		this.resultSets = resultSets;
	}
	
	@Override
	public int hashCode() {
		return this.hashCode;
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
	    
	    RuleSet otherResultSetCollection = (RuleSet) other;
	    
	    boolean sizesAreEqual = this.resultSets.size() == otherResultSetCollection.resultSets.size();
	    
	    if (!sizesAreEqual) {
	    	return false;
	    }
	    
	    // Don't care about order
	    return this.resultSets.containsAll(otherResultSetCollection.resultSets);
	}
	
	private int getHashCode(List<Rule> list) {
		final int prime = 31;
		int hashCode = 1;
		
		for (Rule ss : list) {
			hashCode = hashCode * prime + ss.hashCode();
		}
		
		return hashCode;
	}
}
