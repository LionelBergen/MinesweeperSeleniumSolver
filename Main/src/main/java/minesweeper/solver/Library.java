/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package minesweeper.solver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Library {
	private static final String CHROMEDRIVER_LOCATION = "C:\\Users\\Lionel\\Desktop\\webdrivers\\chromedriver.exe";
	
    public static void main(String[] args) {
    	WebDriver webDriver = getWebDriver();
    	
    	new MineSweeperSolver(webDriver);
    }
    
    private static WebDriver getWebDriver() {
    	System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_LOCATION);
    	
    	return new ChromeDriver();
    }
}
