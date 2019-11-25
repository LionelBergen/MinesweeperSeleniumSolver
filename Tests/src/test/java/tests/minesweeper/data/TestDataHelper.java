package tests.minesweeper.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import component.model.GameSquare;
import component.model.RegularGameBoard;
import component.model.SquareValue;
import tests.minesweeper.data.component.TestScenerio;

public class TestDataHelper {
	public static RegularGameBoard getValidGameBoard(int numberOfNumberedSquares) {
		return getValidGameBoard(numberOfNumberedSquares, 10, 10);
	}
	
	public static RegularGameBoard getValidGameBoard(int width, int height) {
		return getValidGameBoard(0, width, height);
	}
	
	public static RegularGameBoard getValidGameBoard(int numberOfNumberedSquares, int width, int height) {
		List<GameSquare> gameSquares = new ArrayList<GameSquare>();
		
		// Get a 10x10 game board fille with blank
		for (int x=0; x<width; x++) {
			for (int y=0; y<height; y++) {
				gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, x, y));
			}
		}
		
		// randomize the array
		Collections.shuffle(gameSquares);
		
		for (int i=0; i<numberOfNumberedSquares; i++) {
			gameSquares.get(i).setValue(SquareValue.ONE);
		}
		
		RegularGameBoard gameBoard = new RegularGameBoard();
		gameBoard.setGameBoard(gameSquares);
		
		return gameBoard;
	}
	
	public static GameSquare getGameSquare(List<GameSquare> gameSquares, int x, int y) {
		return gameSquares.stream().filter(e -> e.getX() == x && e.getY() == y).findFirst().get();
	}
	
	public static void visualizeGameBoard(TestScenerio scenerio) {
		visualizeGameBoard(scenerio.getGameBoard());
	}
	
	public static void visualizeGameBoard(RegularGameBoard gameBoard) {
		List<GameSquare> gameSquares = gameBoard.getGameBoard();
		
		System.out.println(gameSquares.size());
		
		int width = gameSquares.stream().max(Comparator.comparing(GameSquare::getX)).get().getX() + 1;
		int height = gameSquares.stream().max(Comparator.comparing(GameSquare::getY)).get().getY() + 1;
		
		GameSquare[][] board = new GameSquare[width][height];
		
		for (GameSquare square : gameSquares) {
			board[square.getX()][square.getY()] = square;
		}
		
		int i = 0;
		
		// print the X grid at the top
		System.out.print("  ");
		for (int x=0; x<width; x++) {
			System.out.print(x + " ");
		}
		System.out.println();
		
		for (int y=0; y<height; y++) {
			// print the Y grid at the left
			System.out.print(i++ + " ");
			
			for (int x=0; x<width; x++) {
				System.out.print(visualizeSquareValue(board[x][y]));
				
				if (x != width -1) {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
	
	private static String visualizeSquareValue(GameSquare gameSquare) {
		switch (gameSquare.getValue()) {
		case BLANK_UNTOUCHED:
			return "#";
		case BOMB:
			return "@";
		case FLAGGED:
			return "&";
		case EIGHT:
			return "8";
		case FIVE:
			return "5";
		case FOUR:
			return "4";
		case ONE:
			return "1";
		case SEVEN:
			return "7";
		case SIX:
			return "6";
		case THREE:
			return "3";
		case TWO:
			return "2";
		case ZERO:
			return "0";
		default:
			throw new RuntimeException("Problem with test data");
		}
	}
}
