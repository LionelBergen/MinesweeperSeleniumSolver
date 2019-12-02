package main.solver.component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import solver.component.section.GenericSection;

public class SeleniumSection extends GenericSection<SeleniumGameSquare> {
	private Set<SeleniumGameSquare> gameSquares = new HashSet<SeleniumGameSquare>();
	
	public SeleniumSection() {
	}
	
	public SeleniumSection(Set<SeleniumGameSquare> gameSquares) {
		this.gameSquares = gameSquares;
	}
	
	// TODO: remove duplicate
	public SeleniumSection(Collection<SeleniumGameSquare> gameSquares) {
		this.gameSquares = new HashSet<>(gameSquares);
	}

	@Override
	public void add(SeleniumGameSquare gameSquare) {
		this.gameSquares.add(gameSquare);
	}

	@Override
	public void setGameSquares(Set<SeleniumGameSquare> gameSquares) {
		this.gameSquares = gameSquares;
	}

	@Override
	public Set<SeleniumGameSquare> getGameSquares() {
		return this.gameSquares;
	}
}
