package main.solver.component;

import java.util.List;

import component.model.GameSquare;

public class GameBoard {
	private final List<GameSquare> gameBoard;
	
	public GameBoard(List<GameSquare> gameSquares) {
		this.gameBoard = gameSquares;
	}
}
