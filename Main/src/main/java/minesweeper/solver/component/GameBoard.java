package minesweeper.solver.component;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;

import minesweeper.solver.transformers.WebElementTransformer;
import minesweeper.solver.utility.Utility;

public class GameBoard {
	private final List<SeleniumGameSquare> gameBoard;
	
	public GameBoard(List<WebElement> playableSquares) {
		// Transform, getting the X and Y of each
		this.gameBoard = playableSquares.stream().map(e -> transformGameSquare(e)).collect(Collectors.toList());
	}
	
	public GameSquare getRandomOpenElement() {
		List<SeleniumGameSquare> blankSquares = getAllBlankSquares();
		
		return blankSquares.get(Utility.getRandomNumber(0, blankSquares.size() - 1));
	}
	
	public int getSize() {
		return this.gameBoard.size();
	}
	
	/**
	 * Get a square that is surrounded by 8 blank squares
	 * 
	 * @return A square that is surrounded by 8 blank squares
	 */
	public SeleniumGameSquare getRandomLonelySquare() {
		return getAllBlankSquares().stream().filter(e -> getSurroundingBlankSquares(e).size() == 8).findFirst().orElse(null);
	}
	
	public List<SeleniumGameSquare> getAllNumberedSquares() {
		return getAllSquaresOfType(SquareValue.NUMBERED_VALUES);
	}
	
	public List<SeleniumGameSquare> getAllBlankSquares() {
		return getAllSquaresOfType(SquareValue.BLANK_UNTOUCHED);
	}
	
	public List<SeleniumGameSquare> getallFlaggedSquares() {
		return getAllSquaresOfType(SquareValue.FLAGGED);
	}
	
	public List<GameSquare> getSurroundingFlaggedSquares(GameSquare square) {
		return getAllSquaresOfType(SquareValue.FLAGGED).stream().filter(e-> isTouching(square, e)).collect(Collectors.toList());
	}
	
	public List<SeleniumGameSquare> getSurroundingBlankSquares(GameSquare square) {
		return getAllBlankSquares().stream().filter(e -> isTouching(square, e)).collect(Collectors.toList());
	}
	
	public List<SeleniumGameSquare> getSurroundingNumberedSquares(GameSquare square) {
		return getAllNumberedSquares().stream().filter(e -> isTouching(square, e)).collect(Collectors.toList());
	}
	
	/**
	 *  Returns true if the 2 squares are 'touching' according to MineSweeper rules (corners, left, right, up, down)
	 *  Will return false if the two squares are in the same location (AKA the same square)
	 */
	public static boolean isTouching(GameSquare square1, GameSquare square2) {
		// right
		boolean isTouching = square2.getX() + 1 == square1.getX() && square2.getY() == square1.getY();
		// right top corner
		isTouching |= square2.getX() + 1 == square1.getX() && square2.getY() - 1 == square1.getY();
		// right bottom corner
		isTouching |= square2.getX() + 1 == square1.getX() && square2.getY() + 1 == square1.getY();
		
		// left
		isTouching |= square2.getX() - 1 == square1.getX() && square2.getY() == square1.getY();
		// left top corner
		isTouching |= square2.getX() - 1 == square1.getX() && square2.getY() - 1 == square1.getY();
		// left bottom corner
		isTouching |= square2.getX() - 1 == square1.getX() && square2.getY() + 1 == square1.getY();
		
		// top
		isTouching |= square2.getX() == square1.getX() && square2.getY() + 1 == square1.getY();
		
		// bottom
		isTouching |= square2.getX() == square1.getX() && square2.getY() - 1 == square1.getY();
		
		return isTouching;
	}
	
	public boolean isGameWon() {
		return this.getAllBlankSquares().size() == 0;
	}
	
	private List<SeleniumGameSquare> getAllSquaresOfType(List<SquareValue> squareValuesToFilterBy) {
		return this.gameBoard.stream().filter(e -> squareValuesToFilterBy.contains(e.getValue())).collect(Collectors.toList());
	}
	
	private List<SeleniumGameSquare> getAllSquaresOfType(SquareValue squareValueToFilterBy) {
		return this.gameBoard.stream().filter(e -> squareValueToFilterBy == e.getValue()).collect(Collectors.toList());
	}
    
    private SeleniumGameSquare transformGameSquare(WebElement playableSquareElement) {
    	return WebElementTransformer.transform(playableSquareElement);
    }
}
