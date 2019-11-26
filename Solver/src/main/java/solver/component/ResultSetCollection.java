package solver.component;

import java.util.List;

public class ResultSetCollection {
	private List<ResultSet> resultSets;
	private final int hashCode;
	
	public ResultSetCollection(List<ResultSet> resultSets) {
		this.resultSets = resultSets;
		this.hashCode = getHashCode(resultSets);
	}

	public List<ResultSet> getResultSets() {
		return resultSets;
	}

	public void setResultSets(List<ResultSet> resultSets) {
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
	    
	    ResultSetCollection otherResultSetCollection = (ResultSetCollection) other;
	    
	    boolean sizesAreEqual = this.resultSets.size() == otherResultSetCollection.resultSets.size();
	    
	    if (!sizesAreEqual) {
	    	return false;
	    }
	    
	    // Don't care about order
	    return this.resultSets.containsAll(otherResultSetCollection.resultSets);
	}
	
	private int getHashCode(List<ResultSet> list) {
		final int prime = 31;
		int hashCode = 1;
		
		for (ResultSet ss : list) {
			hashCode = hashCode * prime + ss.hashCode();
		}
		
		return hashCode;
	}
}
