package minesweeper.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import minesweeper.solver.component.GameBoard;
import minesweeper.solver.component.GameSquare;
import minesweeper.solver.component.SquareValue;
import minesweeper.solver.logging.Logger;
import minesweeper.solver.utility.Utility;

// TODO: Create interfaces, ensure Element's are kept separate
// class for auto-completing http://minesweeperonline.com/.
public class MineSweeperSolver {
	private static final String MINESWEEPER_ONLINE_URL = "http://minesweeperonline.com/";
	
	private static final float BEST_ODDS = 0.125f;
	
	public MineSweeperSolver(WebDriver webDriver) {
		// get URL for the website and log the time it takes
		getMinesweeperURL(webDriver);
	
		webDriver.findElement(By.id("face")).click();
		startGame(webDriver);
	}
	
	private void startGame(WebDriver webDriver) {
		Logger.setCurrentTime();
		
		// setup references to the game elements
		final WebElement gameElement = webDriver.findElement(By.id("game"));
		List<WebElement> allPlayableSquares = getAllPlayableSquares(gameElement);
    	GameBoard gameBoard = new GameBoard(allPlayableSquares);
    	Logger.logTimeTook("Setting up references to elements");
		
    	// More setup/logging
    	int totalBlankSquares = gameBoard.getSize();
    	final int startingMines = getCurrentMinesFromGame(gameElement);
    	Logger.logMessage("Total mines: " + startingMines + " total playable squares: " + totalBlankSquares);
    	
    	// The in-game timer used for highscores begins here, on first click
    	Logger.setCurrentTime();
    	
    	while (!gameBoard.isGameWon()) {
	    	List<GameSquare> surroundingSquaresToUpdate = selectARandomSquare(webDriver, gameBoard, startingMines);

	    	do
	    	{
	    		surroundingSquaresToUpdate = updateAllNumberedSquares(webDriver, gameBoard, surroundingSquaresToUpdate);
	    		
	        	Logger.logTimeTook("updating batch. Next round contains: " + surroundingSquaresToUpdate.size());
	    	} while (!surroundingSquaresToUpdate.isEmpty());
    	}
    	
    	Logger.logMessage("Congrats you won!");
	}
	
	private List<GameSquare> updateAllNumberedSquares(WebDriver webDriver, GameBoard gameBoard, List<GameSquare> squaresToUpdate) {
    	List<GameSquare> squaresToUpdate2 = new ArrayList<GameSquare>();
		for (GameSquare square : squaresToUpdate) {
    		squaresToUpdate2.addAll(updateNumberedSquare(webDriver, gameBoard, square));
    	}
		
		return squaresToUpdate2;
	}

	private List<GameSquare> selectARandomSquare(WebDriver webDriver, GameBoard gameBoard, int startingMines) {
		// Get a random square and click it
		GameSquare randomSquare = getRandomSquareWithBestProbability(gameBoard, startingMines);
    	SquareValue newValue = selectSquareWithWait(webDriver, randomSquare);
    	
    	// handle the new value change
    	return updateSquare(webDriver, gameBoard, randomSquare, newValue);
	}
	
	// TODO: Do the complicated calculations. Get every (unique) blank square surrounding known area and calculate the odds. 
	/**
	 * Selects a random square, given the best odds. 
	 * Does simple calculations, does not go into anything complicated such as calculating odds of each surrounding square around 2+ touching numbers
	 */
	private GameSquare getRandomSquareWithBestProbability(GameBoard gameBoard, int startingMines) {
		int totalBlankSquares = gameBoard.getSize();
		int unFoundMines = startingMines - gameBoard.getallFlaggedSquares().size();
		
		float oddsOfRandomSquare = Utility.asFloat(unFoundMines, totalBlankSquares);
		GameSquare randomSquare = gameBoard.getRandomLonelySquare();

    	Logger.logMessage("Found random square. Odds of hitting a mine: " + oddsOfRandomSquare + "%");
		return randomSquare;
		/*int totalBlankSquares = gameBoard.getSize();
		int unFoundMines = startingMines - gameBoard.getallFlaggedSquares().size();
		
		float oddsOfRandomSquare = Utility.asFloat(unFoundMines, totalBlankSquares);
		GameSquare randomSquare = gameBoard.getRandomLonelySquare();
		
		TreeMap<Float, GameSquare> oddsForEachKnownSquare = new TreeMap<Float, GameSquare>();

		if (randomSquare != null)
		{
			if (oddsOfRandomSquare <= BEST_ODDS) {
		    	Logger.logMessage("Found random square. Odds of hitting a mine: " + oddsOfRandomSquare + "%");
				return randomSquare;
			}
			
			oddsForEachKnownSquare.put(Float.valueOf(oddsOfRandomSquare), randomSquare);
		}
		
		for (GameSquare gameSquare : gameBoard.getAllNumberedSquares()) {
			List<GameSquare> blankSquaresSurroundingSquare = gameBoard.getSurroundingBlankSquares(gameSquare);
			
			if (blankSquaresSurroundingSquare.size() > 0) {
				int minesSurroundingSquare = gameSquare.getValue().getNumberOfSurroundingMines();
				
				float chancesOfHittingAMine = Utility.asFloat(minesSurroundingSquare, blankSquaresSurroundingSquare.size());
				randomSquare = blankSquaresSurroundingSquare.get(Utility.getRandomNumber(0, blankSquaresSurroundingSquare.size() - 1));
				
				oddsForEachKnownSquare.put(Float.valueOf(chancesOfHittingAMine), randomSquare);
			}
		}
		
		Entry<Float, GameSquare> entryWithBestOdds = oddsForEachKnownSquare.firstEntry();
    	Logger.logMessage("Found random square. Odds of hitting a mine: " + entryWithBestOdds.getKey() + "%");
    	
		return entryWithBestOdds.getValue();*/
	}
	
	/**
	 * Updates the value on the square. If the new value is a bomb, ends the game.
	 * Otherwise, returns the squares which need to be updated
	 */
	private List<GameSquare> updateSquare(WebDriver webDriver, GameBoard gameBoard, GameSquare gameSquare, SquareValue newSquareValue) {
		List<GameSquare> squaresWhichNeedToBeUpdated = new ArrayList<GameSquare>();
		
		// Already updated
		if (gameSquare.getValue() == newSquareValue) {
			return squaresWhichNeedToBeUpdated;
		}
		
		// Sanity check
		if (gameSquare.getValue() == SquareValue.FLAGGED || gameSquare.getValue() == SquareValue.ZERO 
				|| SquareValue.NUMBERED_VALUES.contains(gameSquare.getValue())) {
			throw new RuntimeException("Something went wrong: Square of type: " + gameSquare.getValue() + " cannot be changed.");
		}
		
		// update the value of the gameSquare
		gameSquare.setValue(newSquareValue);
		
		switch(gameSquare.getValue()) {
	    	case BOMB:
	    		gameOver();
	    		webDriver.findElement(By.id("face")).click();
	    		startGame(webDriver);
	    		break;
			case ZERO:
				// If 0, it means all surrounding blank squares were also updated.
				for (GameSquare surroundingGameSquare : gameBoard.getSurroundingBlankSquares(gameSquare)) {
					SquareValue newValue = selectSquareWithWait(webDriver, surroundingGameSquare);
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
				squaresWhichNeedToBeUpdated.addAll(gameBoard.getSurroundingNumberedSquares(gameSquare));
				break;
			default:
				throw new RuntimeException("Unknown value: " + gameSquare.getValue());
		}
		
		return squaresWhichNeedToBeUpdated;
	}
	
	private List<GameSquare> updateNumberedSquare(WebDriver webDriver, GameBoard gameBoard, GameSquare gameSquare) {
		List<GameSquare> surroundingBlankSquares = gameBoard.getSurroundingBlankSquares(gameSquare);
		final int numberOfSurroundingMines = gameSquare.getValue().getNumberOfSurroundingMines();
		int numberOfSurroundingFlags = gameBoard.getSurroundingFlaggedSquares(gameSquare).size();
		int numberOfSurroundingUntouchedSquares = surroundingBlankSquares.size() + numberOfSurroundingFlags;
		
		List<GameSquare> squaresToUpdate = new ArrayList<GameSquare>();
		
		if (numberOfSurroundingMines == numberOfSurroundingUntouchedSquares) {
			for (GameSquare touchingGameSquare : surroundingBlankSquares) {
				// Value may have changed during previous iteration
				if (touchingGameSquare.getValue() == SquareValue.BLANK_UNTOUCHED) {
					squaresToUpdate.addAll(flagSquare(webDriver, gameBoard, touchingGameSquare));
				}
			}
		}
		else if (numberOfSurroundingMines == numberOfSurroundingFlags && surroundingBlankSquares.size() > 0) {
			for (GameSquare squareToClick : surroundingBlankSquares) {
				SquareValue newValue = selectSquareWithWait(webDriver, squareToClick);
		    	
				squaresToUpdate.addAll(updateSquare(webDriver, gameBoard, squareToClick, newValue));
			}
		}
		
		return squaresToUpdate;
	}
	
	/**
	 * Right clicks a square to flag it & updates the value of the GameSquare to 'FLAGGED'.
	 * @return 
	 */
	private List<GameSquare> flagSquare(WebDriver webDriver, GameBoard gameBoard, GameSquare squareToFlag) {
		if (squareToFlag.getValue() != SquareValue.BLANK_UNTOUCHED) {
			throw new RuntimeException("Cannot flag a square that has a value of: " + squareToFlag.getValue());
		}
		
		// We don't actually need this, but it's easier to debug if we know what the program is thinking
		Actions action = new Actions(webDriver);
		action.contextClick(squareToFlag.getWebElement()).build().perform();
		
		SquareValue newValue = waitForElementToChange(webDriver, squareToFlag);
		
		if (newValue != SquareValue.FLAGGED) {
			throw new RuntimeException("Square wrongly flagged: " + squareToFlag);
		}
		
		return updateSquare(webDriver, gameBoard, squareToFlag, newValue);
	}
	
	private SquareValue waitForElementToChange(WebDriver webDriver, GameSquare gameSquare) {
		// Wait for the WebElement to update & retrieve the new value
    	String newClassName = waitForClassNameToChange(webDriver, gameSquare.getWebElement());
    	SquareValue newValue = SquareValue.fromValue(newClassName);
    	
    	if (newValue == null) {
    		throw new RuntimeException("Cannot get value from class: " + newClassName);
    	}
    	
    	return newValue;
	}
	
	private void gameOver() {
		Logger.logMessage("Game over.");
	}
	
	private String waitForClassNameToChange(WebDriver webDriver, final WebElement element) {
    	WebDriverWait wait = new WebDriverWait(webDriver, 5);
    	
    	wait.until(d -> !element.getAttribute("className").equals("square blank"));
    	
    	return element.getAttribute("className");
	}
	
	private void getMinesweeperURL(WebDriver webDriver) {
		Logger.setCurrentTime();
    	webDriver.get(MINESWEEPER_ONLINE_URL);
    	Logger.logTimeTook("Opening a webdriver");
	}
    
    private int getCurrentMinesFromGame(WebElement gameElement) {
    	String minesValue = gameElement.findElement(By.id("mines_hundreds")).getAttribute("class")
    			+ gameElement.findElement(By.id("mines_tens")).getAttribute("class")
    			+ gameElement.findElement(By.id("mines_ones")).getAttribute("class");

    	return Integer.parseInt(minesValue.replace("time", ""));
    }
    
    private List<WebElement> getAllPlayableSquares(WebElement gameElement) {
    	// get all squares
    	List<WebElement> allPlayableSquares = gameElement.findElements(By.xpath("//div[@class='square blank']"));
    	
    	// Filter invisible squares. Not sure why the game has extra invisible squares
    	allPlayableSquares = allPlayableSquares.stream().filter(e -> e.isDisplayed()).collect(Collectors.toList());
    	
    	return allPlayableSquares;
    }
	
	private SquareValue selectSquareWithWait(WebDriver webDriver, GameSquare gameSquare) {
		gameSquare.getWebElement().click();
		
		// Wait for the WebElement to update & retrieve the new value
    	return waitForElementToChange(webDriver, gameSquare);
	}
}
