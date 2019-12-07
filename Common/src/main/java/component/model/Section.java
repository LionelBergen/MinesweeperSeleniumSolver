package component.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import component.model.gamesquare.GameSquare;

public class Section extends GenericSection<GameSquare> {
	private Set<GameSquare> gameSquares = new HashSet<GameSquare>();
	
	public Section() {
	}
	
	public Section(Set<GameSquare> gameSquares) {
		this.gameSquares = gameSquares;
	}
	
	// TODO: remove duplicate
	public Section(Collection<GameSquare> gameSquares) {
		this.gameSquares = new HashSet<>(gameSquares);
	}

	@Override
	public void add(GameSquare gameSquare) {
		gameSquares.add(gameSquare);
	}

	@Override
	public Set<GameSquare> getGameSquares() {
		return this.gameSquares;
	}

	/*@Override
	public void setGameSquares(Set<GameSquare> gameSquares) {
		this.gameSquares = gameSquares;
	}*/

	@Override
	public void setGameSquares(Set<GameSquare> gameSquares) {
		this.gameSquares = gameSquares;
		
	}
}
