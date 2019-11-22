package minesweeper.solver.component;

import org.openqa.selenium.WebElement;

public class SeleniumGameSquare extends GameSquare {
	private WebElement webElement;

	public SeleniumGameSquare(WebElement webElement, SquareValue value, int x, int y) {
		super(value, x, y);
		
		this.webElement = webElement;
	}

	public WebElement getWebElement() {
		return webElement;
	}

	public void setWebElement(WebElement webElement) {
		this.webElement = webElement;
	}

}
