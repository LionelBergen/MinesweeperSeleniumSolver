package solver.component;

public class KeyValue {
	private int value;
	private int maxValue;
	private Object key;
	
	public KeyValue(int maxValue, Object key) {
		this(0, maxValue, key);
	}
	
	public KeyValue(int value, int maxValue, Object key) {
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
	
	public Object getKey() {
		return key;
	}
	
	public void setKey(Object key) {
		this.key = key;
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
	    
	    KeyValue otherKeyValue = (KeyValue) other;

	    return this.value == otherKeyValue.getValue() && this.key == otherKeyValue.getKey();
	}
	
	@Override
	public String toString() {
		return this.key + " = " + this.value;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
}
