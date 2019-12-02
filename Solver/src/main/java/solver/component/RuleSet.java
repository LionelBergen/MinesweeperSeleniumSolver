package solver.component;

import java.util.List;

/**
 * A class containing a set of rules
 * 
 * @author Lionel Bergen
 */
public class RuleSet {
	private List<Rule> rules;
	private final int hashCode;
	
	public RuleSet(List<Rule> resultSets) {
		this.rules = resultSets;
		this.hashCode = getHashCode(resultSets);
	}

	public List<Rule> getRules() {
		return rules;
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
	    
	    boolean sizesAreEqual = this.rules.size() == otherResultSetCollection.rules.size();
	    
	    if (!sizesAreEqual) {
	    	return false;
	    }
	    
	    // Don't care about order
	    return this.rules.containsAll(otherResultSetCollection.rules);
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
