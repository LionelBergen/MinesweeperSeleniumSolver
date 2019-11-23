package main.solver.transformers;

import org.openqa.selenium.WebElement;

import component.model.SquareValue;
import main.solver.component.SeleniumGameSquare;

// Transforms a WebElement to a 'SeleniumGameSquare'
public class WebElementSeleniumGameSquareTransformer {
	public static SeleniumGameSquare transform(WebElement webElement) {
    	String idOfElement = webElement.getAttribute("id");
    	SquareValue value = getValueFromElement(webElement);
    	
    	int x = Integer.valueOf(idOfElement.split("_")[0]);
    	int y = Integer.valueOf(idOfElement.split("_")[1]);
    	
    	return new SeleniumGameSquare(webElement, value, x, y);
	}
	
	private static SquareValue getValueFromElement(WebElement webElement) {
		String className = webElement.getAttribute("className");
		SquareValue value = SquareValue.fromValue(className);
		
		if (null == value) {
			// If the value doesn't exist something is wrong
			throw new RuntimeException("Cannot get square value from className: " + className);
		}
		
		return value;
	}
}
