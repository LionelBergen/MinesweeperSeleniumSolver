package minesweeper.solver.component;

import org.openqa.selenium.WebElement;

public class GameSquare {
	private WebElement webElement;
	private SquareValue value;
	private int x;
	private int y;
	
	public GameSquare(WebElement webElement, SquareValue value, int x, int y) {
		this.webElement = webElement;
		this.value = value;
		this.x = x;
		this.y = y;
	}

	public WebElement getWebElement() {
		return webElement;
	}

	public void setWebElement(WebElement webElement) {
		this.webElement = webElement;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public SquareValue getValue() {
		return value;
	}

	public void setValue(SquareValue value) {
		this.value = value;
	}
}
