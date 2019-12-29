package main.solver.helper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverToolkit {
	private WebDriverToolkit() { }
	
	public static WebDriver getWebDriver() {
    	WebDriverManager.chromedriver().setup();
    	
    	return new ChromeDriver();
    }
}
