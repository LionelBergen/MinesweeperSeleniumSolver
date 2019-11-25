package solver.component;

import java.util.List;

import component.model.GameSquare;

// TODO: Rename. Don't use 'set', maybe 'ResultSet'
/**
 * 
 * Example usage: Breaking up a {@link Section} into parts based on same odds 
 * 
 * @author Lionel Bergen
 */
public class SweeperSet {
	private List<GameSquare> sqaures;
	private final int resultsEqual;
	
	public SweeperSet(List<GameSquare> squares, int resultsEqual) {
		this.setSqaures(squares); 
		this.resultsEqual = resultsEqual;
	}

	public List<GameSquare> getSqaures() {
		return sqaures;
	}

	public void setSqaures(List<GameSquare> sqaures) {
		this.sqaures = sqaures;
	}

	public int getResultsEqual() {
		return resultsEqual;
	}
}
