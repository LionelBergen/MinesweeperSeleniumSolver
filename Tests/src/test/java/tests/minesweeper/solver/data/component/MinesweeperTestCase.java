package tests.minesweeper.solver.data.component;

import java.util.List;

import minesweeper.solver.component.GameSquare;

public class MinesweeperTestCase {
	private List<GameSquare> gameSquares;
	private List<Float> chances;
	
	public List<GameSquare> getGameSquares() {
		return gameSquares;
	}
	public void setGameSquares(List<GameSquare> gameSquares) {
		this.gameSquares = gameSquares;
	}
	
	public List<Float> getChances() {
		return chances;
	}
	public void setChances(List<Float> chances) {
		this.chances = chances;
	}
}
