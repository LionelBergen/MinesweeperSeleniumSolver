package tests.minesweeper.solver.component;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import component.model.GameSquare;
import component.model.SquareValue;
import solver.component.ResultSet;

public class ResultSetTest {
	@Test
	public void testEqualsTrue() {
		ResultSet set1 = new ResultSet();
		ResultSet set2 = new ResultSet();
		
		set1.add(new GameSquare(SquareValue.TWO, 0, 0));
		set2.add(new GameSquare(SquareValue.TWO, 0, 0));
		
		assertTrue(set1.equals(set1));
		
		assertTrue(set1.equals(set2));
		assertTrue(set2.equals(set1));
	}
	
	// Suppress warning for unlikely argument
	@Test
	@SuppressWarnings("all")
	public void testEqualsInvalid() {
		ResultSet set1 = new ResultSet();
		
		assertFalse(set1.equals(null));
		assertFalse(set1.equals(""));
	}
	
	@Test
	public void testEqualsFalse() {
		ResultSet set1 = new ResultSet();
		ResultSet set2 = new ResultSet();
		set2.add(new GameSquare(SquareValue.ONE, 0, 0));
		
		// different sizes
		assertFalse(set1.equals(set2));
		assertFalse(set2.equals(set1));
		
		set1.add(new GameSquare(SquareValue.TWO, 0, 0));
		
		// Same size, different squares
		assertFalse(set1.equals(set2));
		assertFalse(set2.equals(set1));
	}
}
