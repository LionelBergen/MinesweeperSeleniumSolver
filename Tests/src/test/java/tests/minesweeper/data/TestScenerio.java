package tests.minesweeper.data;

import static tests.minesweeper.data.TestDataHelper.getValidGameBoard;
import static tests.minesweeper.data.TestDataHelper.getGameSquare;
import static tests.minesweeper.data.TestDataHelper.visualizeGameBoard;

import java.util.List;

import component.model.GameSquare;
import component.model.RegularGameBoard;
import component.model.SquareValue;

public class TestScenerio {
	/**
	 * @return A Game board containing a 1 in the center
	 */
	public static RegularGameBoard getGameBoardScenerio1() {
		RegularGameBoard gameBoard = getValidGameBoard(5, 5);
		List<GameSquare> squares = gameBoard.getGameBoard();
		
		getGameSquare(squares, 2, 2).setValue(SquareValue.ONE);
		
		gameBoard.getGameBoard();
		
		return gameBoard;
	}
	
	/**
	 * @return A Game board containing 2 seperate 1's
	 */
	public static RegularGameBoard getGameBoardScenerio2() {
		RegularGameBoard gameBoard = getValidGameBoard(7, 7);
		List<GameSquare> squares = gameBoard.getGameBoard();
		
		getGameSquare(squares, 1, 1).setValue(SquareValue.ONE);
		
		getGameSquare(squares, 5, 2).setValue(SquareValue.ONE);
		
		gameBoard.getGameBoard();
		
		return gameBoard;
	}
	
	public static void main(String[] args) {
		visualizeGameBoard(getGameBoardScenerio2());
	}
}
