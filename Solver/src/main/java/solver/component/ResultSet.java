package solver.component;

import java.util.HashSet;
import java.util.Set;

import component.model.GameSquare;

public class ResultSet {
	// Use a Set since it doesn't allow duplicates
	private Set<GameSquare> gameSquares = new HashSet<GameSquare>();
	
	public void add(GameSquare gameSquare) {
		gameSquares.add(gameSquare);
	}
	
	public Set<GameSquare> getGameSquares() {
		return this.gameSquares;
	}
}
