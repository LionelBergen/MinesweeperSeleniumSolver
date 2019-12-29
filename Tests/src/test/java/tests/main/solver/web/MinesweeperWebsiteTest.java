package tests.main.solver.web;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.anyString;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import main.solver.component.SeleniumGameBoard;
import main.solver.web.MinesweeperWebsite;

public class MinesweeperWebsiteTest {
	private final WebDriver mockWebDriver = createMock(WebDriver.class);
	
	@Test
	public void testConstructor() {
		replayAll();
		MinesweeperWebsite testObject = new MinesweeperWebsite();
		verifyAll();
		
		assertNotNull(testObject);
	}

	@Ignore
	@Test
	public void testSelectARandomSquare() {
		MinesweeperWebsite testObject = createTestSubject();
		
		// webElement.getAttribute("className");
		List<WebElement> mockWebElements = new ArrayList<>();
		mockWebElements.add(new MockWebElement());
		
		SeleniumGameBoard gameBoard = new SeleniumGameBoard(mockWebElements, -1);
		
		testObject.selectARandomSquare(mockWebDriver, gameBoard, 50);
	}
	
	private MinesweeperWebsite createTestSubject() {
		expect(mockWebDriver.findElement(By.id("game"))).andReturn(null);
		mockWebDriver.get(anyString());
		
		replayAll();
		MinesweeperWebsite testObject = new MinesweeperWebsite();
		verifyAll();
		
		return testObject;
	}
	
	private void replayAll() {
		EasyMock.replay(mockWebDriver);
	}
	
	private void verifyAll() {
		EasyMock.verify(mockWebDriver);
	}
}
