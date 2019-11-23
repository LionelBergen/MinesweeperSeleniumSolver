package main.solver.component;

import org.openqa.selenium.WebElement;

import component.model.GameSquare;
import component.model.SquareValue;

/**
 * Game square, but with an additional WebElement property
 * @author Lionel Bergen
 */
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
