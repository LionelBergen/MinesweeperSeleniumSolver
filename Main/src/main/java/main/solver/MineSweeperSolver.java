package main.solver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import component.model.GenericSection;
import component.model.Section;
import component.model.gamesquare.GameSquare;
import component.model.gamesquare.SquareValue;
import main.solver.component.SeleniumGameBoard;
import main.solver.component.SeleniumGameSquare;
import main.solver.component.SeleniumSection;
import main.solver.logging.Logger;
import solver.board.analyzing.BoardAnalyzer;
import solver.board.analyzing.SectionAnalyzer;
import solver.calculation.board.MineOddsCalculator;
import solver.component.Rule;
import utility.util.MathUtil;

// TODO: Create interfaces, ensure Element's are kept separate
// class for auto-completing http://minesweeperonline.com/.
public class MineSweeperSolver {
	private static final String MINESWEEPER_ONLINE_URL = "http://minesweeperonline.com/";
	
	// Minimum odds for an early exit
	private static final float MIN_ODDS = 0.125f;
	
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
		
    	SeleniumGameBoard gameBoard = new SeleniumGameBoard(allPlayableSquares);
    	Logger.logTimeTook("Setting up references to elements");
		
    	// More setup/logging
    	int totalBlankSquares = gameBoard.getSize();
    	final int startingMines = getCurrentMinesFromGame(gameElement);
    	Logger.logMessage("Total mines: " + startingMines + " total playable squares: " + totalBlankSquares);
    	
    	// The in-game timer used for highscores begins here, on first click
    	Logger.setCurrentTime();
    	
    	// select a random square & update game board
    	List<SeleniumGameSquare> surroundingSquaresToUpdate = selectARandomSquare(webDriver, gameBoard, startingMines);
		surroundingSquaresToUpdate = updateAllNumberedSquares(webDriver, gameBoard, surroundingSquaresToUpdate);
    	
    	while (!gameBoard.isGameWon()) {
    		// Select a best square based on probability
	    	surroundingSquaresToUpdate = getRandomSquareWithBestProbability(webDriver, gameBoard, startingMines);

	    	do
	    	{
	    		// Update all the squares that need to be updated
	    		surroundingSquaresToUpdate = updateAllNumberedSquares(webDriver, gameBoard, surroundingSquaresToUpdate);
	    		
	        	Logger.logTimeTook("updating batch. Next round contains: " + surroundingSquaresToUpdate.size());
	    	} while (!surroundingSquaresToUpdate.isEmpty());
    	}
    	
    	Logger.logMessage("Congrats you won!");
	}
	
	private List<SeleniumGameSquare> updateAllNumberedSquares(WebDriver webDriver, SeleniumGameBoard gameBoard, List<SeleniumGameSquare> squaresToUpdate) {
    	List<SeleniumGameSquare> squaresToUpdate2 = new ArrayList<SeleniumGameSquare>();
		for (SeleniumGameSquare square : squaresToUpdate) {
    		squaresToUpdate2.addAll(updateNumberedSquare(webDriver, gameBoard, square));
    	}
		
		return squaresToUpdate2;
	}

	/**
	 * Selects a random square and returns all squares which now need to be updated
	 */
	private List<SeleniumGameSquare> selectARandomSquare(WebDriver webDriver, SeleniumGameBoard gameBoard, int startingMines) {
		int totalBlankSquares = gameBoard.getSize();
		int unFoundMines = startingMines - gameBoard.getAllFlaggedSquares().size();
		
		float oddsOfRandomSquare = MathUtil.asFloat(unFoundMines, totalBlankSquares);
		SeleniumGameSquare randomSquare = getARandomSquare(gameBoard);

    	Logger.logMessage("Found random square. Odds of hitting a mine: " + oddsOfRandomSquare + "%");
    	
    	SquareValue newValue = selectSquareWithWait(webDriver, randomSquare);
    	
    	// handle the new value change
    	return updateSquare(webDriver, gameBoard, randomSquare, newValue);
	}
	
	private SeleniumGameSquare getARandomSquare(SeleniumGameBoard gameBoard) {
		return (SeleniumGameSquare) gameBoard.getRandomLonelySquare();
	}
	
	// TODO: Do the complicated calculations. Get every (unique) blank square surrounding known area and calculate the odds. 
	/**
	 * Selects a random square, given the best odds. 
	 * Does simple calculations, does not go into anything complicated such as calculating odds of each surrounding square around 2+ touching numbers
	 */
	private List<SeleniumGameSquare> getRandomSquareWithBestProbability(WebDriver webDriver, SeleniumGameBoard gameBoard, int startingMines) {
		// TODO: This isn't working. Perhaps it's counting cleared squares?
		// TODO: cannot use <? extends GmaeSquare> for everything.
		// Step 1: Get Sections from Board
		Collection<Section> sections = BoardAnalyzer.breakupBoard(gameBoard);
		
		Map<Section, Double> odds = new HashMap<>();
		
		for (Section section : sections) {
			// Step 2:
			List<Rule> rules = SectionAnalyzer.breakupSectionIntoRules(section);
			
			// Step 3: get subSections
			Collection<Section> subSections = SectionAnalyzer.getSections(rules, section.getGameSquares());
			
			odds.putAll(MineOddsCalculator.calculateOdds(rules, subSections));
			
			for (Entry<Section, Double> entry : odds.entrySet()) {
				System.out.println(entry.getKey() + " " + entry.getValue());
			}
		}
		
		int totalBlankSquares = gameBoard.getSize();
		int unFoundMines = startingMines - gameBoard.getAllFlaggedSquares().size();
		
		float oddsOfRandomSquare = MathUtil.asFloat(unFoundMines, totalBlankSquares);
		SeleniumGameSquare randomSquare = getARandomSquare(gameBoard);

    	Logger.logMessage("Found random square. Odds of hitting a mine: " + oddsOfRandomSquare + "%");
    	
    	SquareValue newValue = selectSquareWithWait(webDriver, randomSquare);
    	
    	// handle the new value change
    	return updateSquare(webDriver, gameBoard, randomSquare, newValue);
    	
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
				// TODO: switch to new method 'getNumber'
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
	    		gameOver();
	    		webDriver.findElement(By.id("face")).click();
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
	
	private List<SeleniumGameSquare> updateNumberedSquare(WebDriver webDriver, SeleniumGameBoard gameBoard, GameSquare gameSquare) {
		List<SeleniumGameSquare> surroundingBlankSquares = hackyConversion(gameBoard.getSurroundingBlankSquares(gameSquare));
		final int numberOfSurroundingMines = gameSquare.getValue().getNumberOfSurroundingMines();
		int numberOfSurroundingFlags = gameBoard.getSurroundingFlaggedSquares(gameSquare).size();
		int numberOfSurroundingUntouchedSquares = surroundingBlankSquares.size() + numberOfSurroundingFlags;
		
		List<SeleniumGameSquare> squaresToUpdate = new ArrayList<SeleniumGameSquare>();
		
		if (numberOfSurroundingMines == numberOfSurroundingUntouchedSquares) {
			for (SeleniumGameSquare touchingGameSquare : surroundingBlankSquares) {
				// Value may have changed during previous iteration
				if (touchingGameSquare.getValue() == SquareValue.BLANK_UNTOUCHED) {
					squaresToUpdate.addAll(flagSquare(webDriver, gameBoard, touchingGameSquare));
				}
			}
		}
		else if (numberOfSurroundingMines == numberOfSurroundingFlags && surroundingBlankSquares.size() > 0) {
			for (SeleniumGameSquare squareToClick : surroundingBlankSquares) {
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
	private List<SeleniumGameSquare> flagSquare(WebDriver webDriver, SeleniumGameBoard gameBoard, SeleniumGameSquare squareToFlag) {
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
	
	private SquareValue waitForElementToChange(WebDriver webDriver, SeleniumGameSquare gameSquare) {
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
	
	private SquareValue selectSquareWithWait(WebDriver webDriver, SeleniumGameSquare gameSquare) {
		gameSquare.getWebElement().click();
		
		// Wait for the WebElement to update & retrieve the new value
    	return waitForElementToChange(webDriver, gameSquare);
	}
}
