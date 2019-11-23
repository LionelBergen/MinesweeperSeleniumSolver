package main.solver.component;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;

import main.solver.transformers.WebElementSeleniumGameSquareTransformer;

/**
 * An Implementation of A GameBoard, with SeleniumWebElement's functionality
 * 
 * @author Lionel Bergen
 */
public class SeleniumGameBoard extends GameBoard<SeleniumGameSquare> {
	private final List<SeleniumGameSquare> gameBoard;
	
	public SeleniumGameBoard(List<WebElement> playableSquares) {
		// Transform, getting the X and Y of each
		this.gameBoard = playableSquares.stream().map(e -> transformGameSquare(e)).collect(Collectors.toList());
		
		setGameBoard(gameBoard);
	}
    
    private SeleniumGameSquare transformGameSquare(WebElement playableSquareElement) {
    	return WebElementSeleniumGameSquareTransformer.transform(playableSquareElement);
    }
}
