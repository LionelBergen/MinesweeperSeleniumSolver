package tests.minesweeper.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import component.model.gamesquare.GameSquare;
import component.model.gamesquare.SquareValue;

public class GameSquareTest {
	@Test
	public void testEqualsTrue() {
		GameSquare sqaure1 = new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 0);
		GameSquare sqaure2 = new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 0);

		assertTrue(sqaure1.equals(sqaure1));
		
		assertTrue(sqaure1.equals(sqaure2));
		assertTrue(sqaure2.equals(sqaure1));
	}
	
	// Suppress warning for unlikely argument
	@Test
	@SuppressWarnings("all")
	public void testEqualsInvalid() {
		GameSquare square1 = new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 0);
		
		assertFalse(square1.equals(null));
		assertFalse(square1.equals(""));
	}
	
	@Test
	public void testEqualsFalse() {
		GameSquare sqaure1 = new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 0);
		GameSquare sqaure2 = new GameSquare(SquareValue.FIVE, 1, 2);
		
		assertFalse(sqaure1.equals(sqaure2));
		assertFalse(sqaure2.equals(sqaure1));
		
		sqaure2.setValue(SquareValue.BLANK_UNTOUCHED);
		assertFalse(sqaure1.equals(sqaure2));
		assertFalse(sqaure2.equals(sqaure1));
		
		sqaure2.setX(0);
		assertFalse(sqaure1.equals(sqaure2));
		assertFalse(sqaure2.equals(sqaure1));
		
		sqaure2.setY(0);
		sqaure2.setX(1);
		assertFalse(sqaure1.equals(sqaure2));
		assertFalse(sqaure2.equals(sqaure1));
		
		sqaure2.setX(0);
		sqaure2.setValue(SquareValue.FLAGGED);
		assertFalse(sqaure1.equals(sqaure2));
		assertFalse(sqaure2.equals(sqaure1));
	}
	
	@Test
	public void testCompareEqual() {
		GameSquare square1 = new GameSquare(SquareValue.BLANK_UNTOUCHED, 5, 3);
		GameSquare square2 = new GameSquare(SquareValue.BLANK_UNTOUCHED, 5, 3);
		
		assertEquals(0, square1.compareTo(square1));
		assertEquals(0, square2.compareTo(square1));
		assertEquals(0, square1.compareTo(square2));
	}
	
	@Test
	public void testCompareX() {
		GameSquare greaterSquare = new GameSquare(SquareValue.BLANK_UNTOUCHED, 5, 0);
		GameSquare lesserSquare = new GameSquare(SquareValue.BOMB, 3, 99);
		
		assertEquals(1, greaterSquare.compareTo(lesserSquare));
		assertEquals(-1, lesserSquare.compareTo(greaterSquare));
	}
	
	@Test
	public void testCompareY() {
		GameSquare greaterSquare = new GameSquare(SquareValue.BLANK_UNTOUCHED, 5, 99);
		GameSquare lesserSquare = new GameSquare(SquareValue.BOMB, 5, 0);
		
		assertEquals(1, greaterSquare.compareTo(lesserSquare));
		assertEquals(-1, lesserSquare.compareTo(greaterSquare));
	}
}
