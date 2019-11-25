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
	
	/**
	 * @return A Game board containing touching numbers & 1 untouching number
	 */
	public static RegularGameBoard getGameBoardScenerio3() {
		RegularGameBoard gameBoard = getValidGameBoard(7, 7);
		List<GameSquare> squares = gameBoard.getGameBoard();
		
		getGameSquare(squares, 1, 1).setValue(SquareValue.TWO);
		getGameSquare(squares, 1, 2).setValue(SquareValue.THREE);
		
		getGameSquare(squares, 3, 5).setValue(SquareValue.THREE);
		
		gameBoard.getGameBoard();
		
		return gameBoard;
	}
	
	/**
	 * @return A Game board containing 2 1's, which are seperate and touching a wall or corner
	 */
	public static RegularGameBoard getGameBoardScenerio4() {
		RegularGameBoard gameBoard = getValidGameBoard(5, 5);
		List<GameSquare> squares = gameBoard.getGameBoard();
		
		getGameSquare(squares, 0, 0).setValue(SquareValue.ONE);
		
		getGameSquare(squares, 3, 4).setValue(SquareValue.ONE);
		
		gameBoard.getGameBoard();
		
		return gameBoard;
	}
	
	/**
	 * @return A Game board containing 2 numbers separated by a single blank
	 */
	public static RegularGameBoard getGameBoardScenerio5() {
		RegularGameBoard gameBoard = getValidGameBoard(4, 4);
		List<GameSquare> squares = gameBoard.getGameBoard();
		
		getGameSquare(squares, 0, 0).setValue(SquareValue.ONE);
		
		getGameSquare(squares, 0, 2).setValue(SquareValue.ONE);
		
		gameBoard.getGameBoard();
		
		return gameBoard;
	}
	
	/**
	 * @return A Game board containing 2 numbers separated by a single blank - diagonally this time
	 */
	public static RegularGameBoard getGameBoardScenerio6() {
		RegularGameBoard gameBoard = getValidGameBoard(4, 4);
		List<GameSquare> squares = gameBoard.getGameBoard();
		
		getGameSquare(squares, 0, 0).setValue(SquareValue.ONE);
		
		getGameSquare(squares, 2, 2).setValue(SquareValue.ONE);
		
		gameBoard.getGameBoard();
		
		return gameBoard;
	}
	
	/**
	 * @return A Game board containing 2 numbers separated by two spaces
	 */
	public static RegularGameBoard getGameBoardScenerio7() {
		RegularGameBoard gameBoard = getValidGameBoard(4, 4);
		List<GameSquare> squares = gameBoard.getGameBoard();
		
		getGameSquare(squares, 0, 0).setValue(SquareValue.ONE);
		
		getGameSquare(squares, 0, 3).setValue(SquareValue.ONE);
		
		gameBoard.getGameBoard();
		
		return gameBoard;
	}
	
	/**
	 * @return A Game board found here: https://math.stackexchange.com/questions/3447402/minesweeper-odds-for-this-scenario-2-different-calculations
	 * We've manually calculated the odds and such
	 */
	public static RegularGameBoard getGameBoardScenerioSpecial01() {
		RegularGameBoard gameBoard = getValidGameBoard(7, 6);
		List<GameSquare> squares = gameBoard.getGameBoard();
		
		getGameSquare(squares, 2, 2).setValue(SquareValue.THREE);
		getGameSquare(squares, 4, 2).setValue(SquareValue.ONE);
		getGameSquare(squares, 3, 3).setValue(SquareValue.ONE);
		
		gameBoard.getGameBoard();
		
		return gameBoard;
	}
	
	public static void main(String[] args) {
		visualizeGameBoard(getGameBoardScenerioSpecial01());
	}
}
