package solver.component;

import component.model.Section;

/**
 * Simply a section with a value & maxValue
 * 
 * @author Lionel Bergen
 */
public class AssignedValue {
	private int value;
	private int maxValue;
	private Section key;
	
	public AssignedValue(int value, int maxValue, Section key) {
		this.value = value;
		this.maxValue = maxValue;
		this.key = key;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public Section getKey() {
		return key;
	}
	
	public void setKey(Section key) {
		this.key = key;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
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
	    
	    AssignedValue otherKeyValue = (AssignedValue) other;

	    return this.value == otherKeyValue.getValue() && this.key == otherKeyValue.getKey();
	}
	
	@Override
	public String toString() {
		return this.key + " = " + this.value;
	}
}
