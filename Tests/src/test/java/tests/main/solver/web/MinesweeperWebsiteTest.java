package tests.main.solver.web;

import static org.junit.Assert.assertNotNull;

import org.easymock.EasyMock;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.anyString;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import main.solver.web.MinesweeperWebsite;

public class MinesweeperWebsiteTest {
	private final WebDriver mockWebDriver = createMock(WebDriver.class);
	
	@Test
	public void testConstructor() {
		WebElement mockElement = createMock(WebElement.class);
		
		expect(mockWebDriver.findElement(By.id("game"))).andReturn(null);
		expect(mockWebDriver.findElement(By.id("face"))).andReturn(mockElement);
		mockWebDriver.get(anyString());
		
		replayAll();
		MinesweeperWebsite testObject = new MinesweeperWebsite(mockWebDriver);
		verifyAll();
		
		assertNotNull(testObject);
	}
	
	private void replayAll() {
		EasyMock.replay(mockWebDriver);
	}
	
	private void verifyAll() {
		EasyMock.verify(mockWebDriver);
	}
}
