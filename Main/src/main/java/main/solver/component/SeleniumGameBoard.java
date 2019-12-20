package main.solver.component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;

import component.model.GameBoard;
import component.model.gamesquare.GameSquare;
import main.solver.transformers.WebElementSeleniumGameSquareTransformer;

/**
 * An Implementation of A GameBoard, with SeleniumWebElement's functionality
 * 
 * @author Lionel Bergen
 */
public class SeleniumGameBoard extends GameBoard {
	private final List<SeleniumGameSquare> gameBoard;
	
	public SeleniumGameBoard(List<WebElement> playableSquares, int numberOfMines) {
		// Transform, getting the X and Y of each
		this.gameBoard = playableSquares.stream().map(e -> transformGameSquare(e)).collect(Collectors.toList());
		this.setTotalUnidentifiedMines(numberOfMines);
	}
	
	@Override
	public List<GameSquare> getGameBoard() {
		return new ArrayList<GameSquare>(this.gameBoard);
	}

	@Override
	protected void setGameBoard(List<GameSquare> gameSquares) {
		throw new RuntimeException("Invalid argument");
	}
    
    private SeleniumGameSquare transformGameSquare(WebElement playableSquareElement) {
    	return WebElementSeleniumGameSquareTransformer.transform(playableSquareElement);
    }
}
