package solver.component;

import java.util.Set;

import solver.component.section.Section;

// TODO: make immutable
/**
 * Basically just a Collection<Section>
 * 
 * @author Lionel Bergen
 */
public class SectionSet {
	private final Set<Section> sections;
	private final int hashCode;
	
	public SectionSet(Set<Section> sections) {
		this.sections = sections;
		this.hashCode = getHashCode(sections);
	}
	
	public Set<Section> getSections() {
		return this.sections;
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
	    
	    SectionSet otherResultSet = (SectionSet) other;
	    
	    boolean sizesAreEqual = this.sections.size() == otherResultSet.sections.size();
	    
	    if (!sizesAreEqual) {
	    	return false;
	    }
	    
	    // Don't care about order
	    return this.sections.containsAll(otherResultSet.sections);
	}
	
	private int getHashCode(Set<Section> list) {
		final int prime = 31;
		int hashCode = 1;
		
		for (Section ss : list) {
			hashCode = hashCode * prime + ss.hashCode();
		}
		
		return hashCode;
	}
}
