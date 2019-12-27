package main.solver.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import component.model.gamesquare.GameSquare;
import component.model.gamesquare.SquareValue;
import main.solver.component.SeleniumGameBoard;
import main.solver.component.SeleniumGameSquare;
import utility.logging.Logger;
import utility.util.MathUtil;

// class for interacting with the Minesweeper board over at http://minesweeperonline.com/.
public class MinesweeperWebsite {
	private static final String GAME_ELEMENT_ID = "game";
	private static final String MINESWEEPER_ONLINE_URL = "http://minesweeperonline.com/";
	
	private static final String XPATH_FOR_SQUARES = "//div[@class='square blank']";
	
	private final WebElement gameElement;
	
	public MinesweeperWebsite(WebDriver webDriver) {
		goToMinesweeperPage(webDriver);
		this.gameElement = webDriver.findElement(By.id(GAME_ELEMENT_ID));
	}
	
	public void startGame(WebDriver webDriver) {
		webDriver.findElement(By.id("face")).click();
	}
	
	public List<WebElement> getAllPlayableSquares() {
    	// get all squares
    	List<WebElement> allPlayableSquares = gameElement.findElements(By.xpath(XPATH_FOR_SQUARES));
    	
    	// Filter invisible squares. Not sure why the game has extra invisible squares
    	allPlayableSquares = allPlayableSquares.stream().filter(e -> e.isDisplayed()).collect(Collectors.toList());
    	
    	return allPlayableSquares;
    }
    
    public int getCurrentMinesFromGame() {
    	String minesValue = gameElement.findElement(By.id("mines_hundreds")).getAttribute("class")
    			+ gameElement.findElement(By.id("mines_tens")).getAttribute("class")
    			+ gameElement.findElement(By.id("mines_ones")).getAttribute("class");

    	return Integer.parseInt(minesValue.replace("time", ""));
    }

	/**
	 * Selects a random square and returns all squares which now need to be updated
	 */
	public List<SeleniumGameSquare> selectARandomSquare(WebDriver webDriver, SeleniumGameBoard gameBoard, int startingMines) {
		int totalBlankSquares = gameBoard.getSize();
		int unFoundMines = startingMines - gameBoard.getAllFlaggedSquares().size();
		
		float oddsOfRandomSquare = MathUtil.asFloat(unFoundMines, totalBlankSquares);
		SeleniumGameSquare randomSquare = getARandomSquare(gameBoard);

    	Logger.logMessage("Found random square. Odds of hitting a mine: " + oddsOfRandomSquare + "%");
    	
    	SquareValue newValue = selectSquareWithWait(webDriver, randomSquare);
    	
    	// handle the new value change
    	return updateSquare(webDriver, gameBoard, randomSquare, newValue);
	}
	
	private SquareValue selectSquareWithWait(WebDriver webDriver, SeleniumGameSquare gameSquare) {
		gameSquare.getWebElement().click();
		
		// Wait for the WebElement to update & retrieve the new value
    	return waitForElementToChange(webDriver, gameSquare);
	}
	
	private SquareValue waitForElementToChange(WebDriver webDriver, SeleniumGameSquare gameSquare) {
		// Wait for the WebElement to update & retrieve the new value
    	String newClassName = waitForClassNameToChange(webDriver, gameSquare.getWebElement());
    	SquareValue newValue = SquareValue.fromValue(newClassName);
    	Logger.logMessage("Changed square to: " + newValue + " at: " + gameSquare);
    	if (newValue == null) {
    		throw new RuntimeException("Cannot get value from class: " + newClassName);
    	}
    	
    	return newValue;
	}
	
	private String waitForClassNameToChange(WebDriver webDriver, final WebElement element) {
    	WebDriverWait wait = new WebDriverWait(webDriver, 5);
    	
    	wait.until(d -> !element.getAttribute("className").equals("square blank"));
    	
    	return element.getAttribute("className");
	}
	
	/**
	 * Updates the value on the square. If the new value is a bomb, ends the game.
	 * Otherwise, returns the squares which need to be updated
	 *
	 * @param webDriver
	 * @param gameBoard
	 * @param gameSquare
	 * @param newSquareValue
	 * @return All squares which now need to be updated
	 */
	private List<SeleniumGameSquare> updateSquare(WebDriver webDriver, SeleniumGameBoard gameBoard, GameSquare gameSquare, SquareValue newSquareValue) {
		List<SeleniumGameSquare> squaresWhichNeedToBeUpdated = new ArrayList<SeleniumGameSquare>();
		
		// Already updated
		if (gameSquare.getValue() == newSquareValue) {
			return squaresWhichNeedToBeUpdated;
		}
		
		// Sanity check
		if (gameSquare.getValue() == SquareValue.FLAGGED || gameSquare.getValue() == SquareValue.ZERO 
				|| gameSquare.getValue().isNumbered()) {
			throw new RuntimeException("Something went wrong: Square of type: " + gameSquare.getValue() + " cannot be changed.");
		}
		
		// update the value of the gameSquare
		gameSquare.setValue(newSquareValue);
		
		switch(gameSquare.getValue()) {
	    	case BOMB:
	    		// TODO: instead of starting the game, return an indication that we should end the game (To prevent recursive overflow)
	    		Logger.logMessage("GAME OVER!");
	    		startGame(webDriver);
	    		break;
			case ZERO:
				// If 0, it means all surrounding blank squares were also updated.
				for (GameSquare surroundingGameSquare : gameBoard.getSurroundingBlankSquares(gameSquare)) {
					SquareValue newValue = selectSquareWithWait(webDriver, (SeleniumGameSquare) surroundingGameSquare);
					squaresWhichNeedToBeUpdated.addAll(updateSquare(webDriver, gameBoard, surroundingGameSquare, newValue));
				}
				// No break, we also need to update numbered squares (in case it gives us enough info to open another square)
			case ONE:
			case TWO:
			case THREE:
			case FOUR:
			case FIVE:
			case SIX:
			case SEVEN:
			case EIGHT:
			case FLAGGED:
				List<SeleniumGameSquare> squaresToAdd = hackyConversion(gameBoard.getSurroundingNumberedSquares(gameSquare));
				squaresWhichNeedToBeUpdated.addAll(squaresToAdd);
				break;
			default:
				throw new RuntimeException("Unknown value: " + gameSquare.getValue());
		}
		
		return squaresWhichNeedToBeUpdated;
	}

	// TODO: Move this method. Also try to avoid casting list
	private List<SeleniumGameSquare> hackyConversion(List<GameSquare> list) {
		return list.stream().map(e -> (SeleniumGameSquare) e).collect(Collectors.toList());
	}
	
	private SeleniumGameSquare getARandomSquare(SeleniumGameBoard gameBoard) {
		return (SeleniumGameSquare) gameBoard.getRandomLonelySquare();
	}
	
	private void goToMinesweeperPage(WebDriver webDriver) {
		Logger.setCurrentTime();
    	webDriver.get(MINESWEEPER_ONLINE_URL);
    	Logger.logTimeTook("Opening a webdriver");
	}
}
