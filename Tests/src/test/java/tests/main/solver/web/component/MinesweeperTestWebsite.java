package tests.main.solver.web.component;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import main.solver.web.MinesweeperWebsite;

// Used for tests. Implements pages created by MockBuilder
public class MinesweeperTestWebsite extends MinesweeperWebsite {
	private static final String XPATH_FOR_SQUARES = "//div[@class='square blank']";
	private static final String GAME_ELEMENT_ID = "gameBoard";
	
	public void startGame(WebDriver webDriver) {
		this.gameElement = webDriver.findElement(By.id(GAME_ELEMENT_ID));
	}
	
	@Override
	public int getCurrentMinesFromGame() {
		return 0;
	}
	
	public List<WebElement> getAllPlayableSquares() {
		return this.getAllPlayableSquares(XPATH_FOR_SQUARES);
	}
}
