package main.solver.web;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utility.logging.Logger;

// class for interacting with the Minesweeper board over at http://minesweeperonline.com/.
public class MinesweeperWebsite {
	private static final String GAME_ELEMENT_ID = "game";
	private static final String MINESWEEPER_ONLINE_URL = "http://minesweeperonline.com/";
	
	private static final String XPATH_FOR_SQUARES = "//div[@class='square blank']";
	
	private final WebElement gameElement;
	
	public MinesweeperWebsite(WebDriver webDriver) {
		this.gameElement = webDriver.findElement(By.id(GAME_ELEMENT_ID));
		goToMinesweeperPage(webDriver);
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
	
	private void goToMinesweeperPage(WebDriver webDriver) {
		Logger.setCurrentTime();
    	webDriver.get(MINESWEEPER_ONLINE_URL);
    	Logger.logTimeTook("Opening a webdriver");
	}
}
