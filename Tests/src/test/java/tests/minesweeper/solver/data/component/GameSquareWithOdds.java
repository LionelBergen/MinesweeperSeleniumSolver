package tests.minesweeper.solver.data.component;

import component.model.GameSquare;

public class GameSquareWithOdds {
	private GameSquare gameSquare;
	private float expectedChanceOfAMine;
	
	public GameSquareWithOdds(GameSquare gameSquare, float expectedChanceOfAMine) {
		this.gameSquare = gameSquare;
		this.expectedChanceOfAMine = expectedChanceOfAMine;
	}
	
	public GameSquare getGameSquare() {
		return gameSquare;
	}
	
	public void setGameSquare(GameSquare gameSquare) {
		this.gameSquare = gameSquare;
	}
	
	public float getExpectedChanceOfAMine() {
		return expectedChanceOfAMine;
	}
	
	public void setExpectedChanceOfAMine(float expectedChanceOfAMine) {
		this.expectedChanceOfAMine = expectedChanceOfAMine;
	}
}
