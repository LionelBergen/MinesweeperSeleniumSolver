package component.model;

import java.util.Arrays;
import java.util.List;

public enum SquareValue {
	BOMB("square bombdeath", -1),
	BLANK_UNTOUCHED("square blank", -1),
	FLAGGED("square bombflagged", -1),
	ZERO("square open0", 0),
	ONE("square open1", 1),
	TWO("square open2", 2),
	THREE("square open3", 3),
	FOUR("square open4", 4),
	FIVE("square open5", 5),
	SIX("square open6", 6),
	SEVEN("square open7", 7),
	EIGHT("square open8", 8);
	
	public static final List<SquareValue> NUMBERED_VALUES = Arrays.asList(
			ONE, TWO, THREE, FOUR, 
			FIVE, SIX, SEVEN, EIGHT);
	
	private String value;
	private int numberOfSurroundingMines;
	
	SquareValue(String value, int numberOfSurroundingMines) {
		this.value = value;
		this.numberOfSurroundingMines = numberOfSurroundingMines;
	}

	public int getNumberOfSurroundingMines() {
		return numberOfSurroundingMines;
	}

	public void setNumberOfSurroundingMines(int numberOfSurroundingMines) {
		this.numberOfSurroundingMines = numberOfSurroundingMines;
	}
	
	public boolean isNumbered() {
		return NUMBERED_VALUES.contains(this);
	}
	
	public static SquareValue fromValue(String fromValue) {
		for (SquareValue s : values()) {
			if (s.value.equals(fromValue)) {
				return s;
			}
		}
		
		return null;
	}
}
