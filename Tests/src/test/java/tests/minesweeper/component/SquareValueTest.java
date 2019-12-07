package tests.minesweeper.component;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import component.model.gamesquare.SquareValue;

public class SquareValueTest {
	@Test
	public void testIsNumbered() {
		assertTrue(SquareValue.ONE.isNumbered());
		assertTrue(SquareValue.TWO.isNumbered());
		assertTrue(SquareValue.THREE.isNumbered());
		assertTrue(SquareValue.FOUR.isNumbered());
		assertTrue(SquareValue.FIVE.isNumbered());
		assertTrue(SquareValue.SIX.isNumbered());
		assertTrue(SquareValue.SEVEN.isNumbered());
		assertTrue(SquareValue.EIGHT.isNumbered());
		
		assertFalse(SquareValue.BOMB.isNumbered());
		assertFalse(SquareValue.BLANK_UNTOUCHED.isNumbered());
		assertFalse(SquareValue.FLAGGED.isNumbered());
	}
}
