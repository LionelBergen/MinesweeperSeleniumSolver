package tests.minesweeper.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import component.model.GameSquare;
import component.model.RegularGameBoard;
import component.model.SquareValue;

public class TestData {
	public static RegularGameBoard getValidGameBoard(int numberOfNumeredSquares) {
		List<GameSquare> gameSquares = new ArrayList<GameSquare>();
		
		// Get a 10x10 game board fille with blank
		for (int x=0; x<10; x++) {
			for (int y=0; y<10; y++) {
				gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, x, y));
			}
		}
		
		// randomize the array
		Collections.shuffle(gameSquares);
		
		for (int i=0; i<numberOfNumeredSquares; i++) {
			gameSquares.get(i).setValue(SquareValue.ONE);
		}
		
		RegularGameBoard gameBoard = new RegularGameBoard();
		gameBoard.setGameBoard(gameSquares);
		
		return gameBoard;
	}
}
