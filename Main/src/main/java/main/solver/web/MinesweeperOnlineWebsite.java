package main.solver.web;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

//class for interacting with the Minesweeper board over at http://minesweeperonline.com/.
public class MinesweeperOnlineWebsite extends MinesweeperWebsite {
	private static final String MINESWEEPER_ONLINE_URL = "http://minesweeperonline.com/";
	private static final String XPATH_FOR_SQUARES = "//div[@class='square blank']";
	
	private static final String GAME_ELEMENT_ID = "game";
	private static final String RESTART_BUTTON_ID = "face";
	
	public void startGame(WebDriver webDriver) {
		this.startGame(webDriver, MINESWEEPER_ONLINE_URL, GAME_ELEMENT_ID, RESTART_BUTTON_ID);
	}
	
	public List<WebElement> getAllPlayableSquares() {
		return this.getAllPlayableSquares(XPATH_FOR_SQUARES);
	}
	
	@Override
	public int getCurrentMinesFromGame() {
    	String minesValue = gameElement.findElement(By.id("mines_hundreds")).getAttribute("class")
    			+ gameElement.findElement(By.id("mines_tens")).getAttribute("class")
    			+ gameElement.findElement(By.id("mines_ones")).getAttribute("class");

    	return Integer.parseInt(minesValue.replace("time", ""));
    }
}
