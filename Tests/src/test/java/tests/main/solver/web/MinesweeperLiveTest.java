package tests.main.solver.web;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import component.model.Section;
import component.model.gamesquare.GameSquare;
import component.model.gamesquare.SquareValue;
import main.solver.component.SeleniumGameBoard;
import main.solver.component.SeleniumGameSquare;
import main.solver.helper.WebDriverToolkit;
import solver.board.analyzing.GameBoardAnalyzer;
import tests.main.solver.web.component.MinesweeperTestWebsite;
import tests.minesweeper.solver.data.TestFileUtil;
import utility.logging.Logger;
import utility.util.MathUtil;

public class MinesweeperLiveTest {
	private static final Random RANDOM = new Random();
	
	private WebDriver webDriver;
	private MinesweeperTestWebsite websiteHelper;
	
	@Before
	public void setup() {
		this.webDriver = WebDriverToolkit.getWebDriver();
		this.websiteHelper = new MinesweeperTestWebsite();
	}
	
	@Test
	public void test() throws Exception {
		setupTestSite("LargeScenario01.json");
		
		this.websiteHelper.startGame(webDriver);
		
		// setup references to the game elements
    	final int startingMines = websiteHelper.getCurrentMinesFromGame();
		List<WebElement> allPlayableSquares = websiteHelper.getAllPlayableSquares();
    	SeleniumGameBoard gameBoard = new SeleniumGameBoard(allPlayableSquares, startingMines);
		
    	List<GameSquare> allNumberedSquares = gameBoard.getAllNumberedSquares();
    	GameSquare square = allNumberedSquares.stream().filter(e -> e.getX() == 15 && e.getY() == 6).findFirst().get();
		List<SeleniumGameSquare> surroundingSquaresToUpdate = updateNumberedSquare(webDriver, gameBoard, square);
		
		assertFalse(surroundingSquaresToUpdate.isEmpty());
	}
	
	private List<SeleniumGameSquare> updateAllNumberedSquares(WebDriver webDriver, SeleniumGameBoard gameBoard, List<SeleniumGameSquare> squaresToUpdate) {
    	List<SeleniumGameSquare> squaresToUpdate2 = new ArrayList<SeleniumGameSquare>();
		for (SeleniumGameSquare square : squaresToUpdate) {
    		squaresToUpdate2.addAll(updateNumberedSquare(webDriver, gameBoard, square));
    	}
		
		return squaresToUpdate2;
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

	// TODO: Move this method. Also try to avoid casting list
	private List<SeleniumGameSquare> hackyConversion(List<GameSquare> list) {
		return list.stream().map(e -> (SeleniumGameSquare) e).collect(Collectors.toList());
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
	
	private void startGame(WebDriver webDriver) {
		Logger.setCurrentTime();
		
		// setup references to the game elements
    	final int startingMines = websiteHelper.getCurrentMinesFromGame();
		List<WebElement> allPlayableSquares = websiteHelper.getAllPlayableSquares();
    	SeleniumGameBoard gameBoard = new SeleniumGameBoard(allPlayableSquares, startingMines);
    	
    	Logger.logTimeTook("Setting up references to elements");
    	Logger.logMessage("Total mines: " + startingMines + " total playable squares: " + gameBoard.getSize());
    	
    	// The in-game timer used for highscores begins here, on first click
    	Logger.setCurrentTime();
    	
    	// select a random square & update game board (Remove duplicates)
    	List<SeleniumGameSquare> surroundingSquaresToUpdate = new ArrayList<>(new HashSet<SeleniumGameSquare>(selectARandomSquare(webDriver, gameBoard, startingMines)));
    	
    	while (!surroundingSquaresToUpdate.isEmpty()) {
    		surroundingSquaresToUpdate = new ArrayList<>(new HashSet<SeleniumGameSquare>(updateAllNumberedSquares(webDriver, gameBoard, surroundingSquaresToUpdate)));
    	}
    	
    	while (!gameBoard.isGameWon()) {
    		// Select a best square based on probability
	    	surroundingSquaresToUpdate = getRandomSquareWithBestProbability(webDriver, gameBoard, startingMines);

	    	do {
	    		// Update all the squares that need to be updated
	    		surroundingSquaresToUpdate = updateAllNumberedSquares(webDriver, gameBoard, surroundingSquaresToUpdate);
	    		
	        	Logger.logTimeTook("updating batch. Next round contains: " + surroundingSquaresToUpdate.size());
	    	} while (!surroundingSquaresToUpdate.isEmpty());
    	}
    	
    	Logger.logMessage("Congrats you won!");
	}
	
	/**
	 * Selects a random square, given the best odds. 
	 * Does simple calculations, does not go into anything complicated such as calculating odds of each surrounding square around 2+ touching numbers
	 */
	private List<SeleniumGameSquare> getRandomSquareWithBestProbability(WebDriver webDriver, SeleniumGameBoard gameBoard, int startingMines) {
		gameBoard.setTotalUnidentifiedMines(websiteHelper.getCurrentMinesFromGame());
		
		/*
		// Step 1: Get Sections from Board
		Logger.setCurrentTime();
		Logger.logMessage("Breaking up board.....");
		Collection<Section> sections = BoardAnalyzer.breakupBoard(gameBoard);
		Logger.logTimeTook("Breaking up board");

		// Step 2: get Rules & SubSections
		Logger.setCurrentTime();
		Logger.logMessage("Breaking up board into rules & sections");
		final List<Rule> rules = SectionAnalyzer.breakupSectionIntoRules(sections);
		final List<Section> allSubSections = new ArrayList<>(SectionAnalyzer.getSections(rules, gameBoard.getGameBoard()));
		Logger.logTimeTook("Breaking up board into rules & sections");
		
		// Step 3: get all possibilities
		Logger.setCurrentTime();
		Logger.logMessage("Caluclating all possible combinations");
		final List<List<AssignedValue>> allVariations = RulesCombinationCalculator.getAllVariations(allSubSections, rules);
		Logger.logTimeTook("Caluclated: " + allVariations.size() + " possible outcomes");
		
		// Step 4: compute probabilities
		Logger.setCurrentTime();
		Logger.logMessage("Caluclating probabilities");
		final Map<Section, BigDecimal> probabilities = OddsCalculator.calculateOdds(allVariations, unFoundMines, totalUnidentifiedSquares);
		Logger.logTimeTook("Caluclated: " + allVariations.size() + " possible outcomes");

		// Get the item with the best probability
		Entry<Section, BigDecimal> lowestValue = probabilities.entrySet().stream().sorted(Map.Entry.comparingByValue()).findFirst().get();
		Section result = lowestValue.getKey();*/
		
		Logger.setCurrentTime();
		final Map<Section, BigDecimal> probabilities = GameBoardAnalyzer.calculateOddsForEverySection(gameBoard);
		Logger.logTimeTook("Caluclated: " + probabilities.size() + " probabilities");
		
		Entry<Section, BigDecimal> lowestValue = probabilities.entrySet().stream().sorted(Map.Entry.comparingByValue()).findFirst().get();
		Section result = lowestValue.getKey();
		
		Set<GameSquare> squaresWithBestProbability = result.getGameSquares();
		SeleniumGameSquare squareToSelect = getRandomSquareFromSet(squaresWithBestProbability);
    	
		Logger.logMessage("Found random square. Odds of hitting a mine: " + lowestValue.getValue() + "%");
    	
    	SquareValue newValue = selectSquareWithWait(webDriver, squareToSelect);
    	
    	// handle the new value change
    	return updateSquare(webDriver, gameBoard, squareToSelect, newValue);
	}
	
	private SeleniumGameSquare getRandomSquareFromSet(Set<GameSquare> squares) {
		int random = RANDOM.nextInt(squares.size());
		return (SeleniumGameSquare) new ArrayList<>(squares).get(random);
	}
	
	private SeleniumGameSquare getARandomSquare(SeleniumGameBoard gameBoard) {
		return (SeleniumGameSquare) gameBoard.getRandomLonelySquare();
	}

	/**
	 * Selects a random square and returns all squares which now need to be updated
	 */
	private List<SeleniumGameSquare> selectARandomSquare(WebDriver webDriver, SeleniumGameBoard gameBoard, int startingMines) {
		int totalBlankSquares = gameBoard.getSize();
		int unFoundMines = websiteHelper.getCurrentMinesFromGame();
		
		float oddsOfRandomSquare = MathUtil.asFloat(unFoundMines, totalBlankSquares);
		SeleniumGameSquare randomSquare = getARandomSquare(gameBoard);

    	Logger.logMessage("Found random square. Odds of hitting a mine: " + oddsOfRandomSquare + "%");
    	
    	SquareValue newValue = selectSquareWithWait(webDriver, randomSquare);
    	
    	// handle the new value change
    	return updateSquare(webDriver, gameBoard, randomSquare, newValue);
	}
	
	private void gameOver() {
		Logger.logMessage("Game over.");
	}
	
	private String waitForClassNameToChange(WebDriver webDriver, final WebElement element) {
    	WebDriverWait wait = new WebDriverWait(webDriver, 5);
    	
    	wait.until(d -> !element.getAttribute("className").equals("square blank"));
    	
    	return element.getAttribute("className");
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
	
	private SquareValue selectSquareWithWait(WebDriver webDriver, SeleniumGameSquare gameSquare) {
		gameSquare.getWebElement().click();
		
		// Wait for the WebElement to update & retrieve the new value
    	return waitForElementToChange(webDriver, gameSquare);
	}
	
	private void setupTestSite(String testJsonFile) throws Exception {
		JSONObject jsonFromFile =  TestFileUtil.getJsonFromFile(testJsonFile);
		
		webDriver.get(new File("C:\\Users\\Lionel\\eclipse-workspace\\MinesweeperSolver\\MockBuilder\\assets\\board-tester/board-tester.html").getAbsolutePath());
		webDriver.findElement(By.id("loadBoard")).click();
		
		synchronized(webDriver) {
			WebDriverWait wait = new WebDriverWait(webDriver, 30);
			synchronized(wait) {
				wait.until(ExpectedConditions.alertIsPresent());
				System.out.println(jsonFromFile.toString());
				Alert alert = webDriver.switchTo().alert();
				alert.sendKeys(jsonFromFile.toString());
				alert.accept();
			}
		}
	}
}
