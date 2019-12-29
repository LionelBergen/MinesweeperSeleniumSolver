package tests.main.solver.web;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import main.solver.helper.WebDriverToolkit;
import main.solver.web.MinesweeperWebsite;

public class MinesweeperLiveTest {
	
	@Ignore
	@Test
	public void test() {
		WebDriver webDriver = WebDriverToolkit.getWebDriver();
		MinesweeperWebsite websiteHelper = new MinesweeperWebsite();
		
		
	}
	
	private void setupTestSite(String testJsonFile) {
		
	}
}
