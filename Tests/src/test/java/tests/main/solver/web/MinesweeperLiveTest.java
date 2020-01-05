package tests.main.solver.web;

import java.io.File;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import main.solver.helper.WebDriverToolkit;
import main.solver.web.MinesweeperWebsite;
import tests.main.solver.web.component.MinesweeperTestWebsite;
import tests.minesweeper.solver.data.TestFileUtil;

public class MinesweeperLiveTest {
	@Test
	public void test() throws Exception {
		setupTestSite("LargeScenario01.json");
		MinesweeperWebsite websiteHelper = new MinesweeperTestWebsite();
	}
	
	private void setupTestSite(String testJsonFile) throws Exception {
		WebDriver webDriver = WebDriverToolkit.getWebDriver();
		
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
