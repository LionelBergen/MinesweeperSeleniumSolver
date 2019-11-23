package main.solver.component;

import static utility.Utility.isTouching;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;

import component.model.GameSquare;
import component.model.SquareValue;
import main.solver.transformers.WebElementSeleniumGameSquareTransformer;
import utility.MathUtil;

public class SeleniumGameBoard {
	private final List<SeleniumGameSquare> gameBoard;
	
	public SeleniumGameBoard(List<WebElement> playableSquares) {
		// Transform, getting the X and Y of each
		this.gameBoard = playableSquares.stream().map(e -> transformGameSquare(e)).collect(Collectors.toList());
	}
	
	public SeleniumGameSquare getRandomOpenElement() {
		List<SeleniumGameSquare> blankSquares = getAllBlankSquares();
		
		return blankSquares.get(MathUtil.getRandomNumber(0, blankSquares.size() - 1));
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
    	return WebElementSeleniumGameSquareTransformer.transform(playableSquareElement);
    }
}
