package tests.minesweeper.utility;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import component.model.gamesquare.GameSquare;
import utility.util.Utility;

public class UtilityTest {
	@Test
	public void testIsTouchingSameSpot() {
		GameSquare square1 = new GameSquare(null, 5, 5);
		GameSquare square2 = new GameSquare(null, 5, 5);
		
		assertFalse(Utility.isTouching(square1, square2));
		assertFalse(Utility.isTouching(square1, square1));
	}
	
	@Test
	public void testIsTouchingLeftRight() {
		GameSquare square1 = new GameSquare(null, 5, 5);
		GameSquare square2 = new GameSquare(null, 4, 5);
		
		assertTrue(Utility.isTouching(square1, square2));
		assertTrue(Utility.isTouching(square2, square1));
	}
	
	@Test
	public void testIsTouchingTopBottom() {
		GameSquare square1 = new GameSquare(null, 5, 6);
		GameSquare square2 = new GameSquare(null, 5, 5);
		
		assertTrue(Utility.isTouching(square1, square2));
		assertTrue(Utility.isTouching(square2, square1));
	}
	
	@Test
	public void testIsTouchingTopLeftCornerBottomRightCorner() {
		GameSquare square1 = new GameSquare(null, 5, 5);
		GameSquare square2 = new GameSquare(null, 4, 4);
		
		assertTrue(Utility.isTouching(square1, square2));
		assertTrue(Utility.isTouching(square2, square1));
	}
}
