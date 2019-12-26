package tests.minesweeper.solver.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import component.model.Section;
import component.model.gamesquare.GameSquare;
import component.model.gamesquare.SquareValue;

public class SectionTest {
	@Test
	public void testEqualsTrue() {
		Section set1 = new Section();
		Section set2 = new Section();
		
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
		Section set1 = new Section();
		
		assertFalse(set1.equals(null));
		assertFalse(set1.equals(""));
	}
	
	@Test
	public void testEqualsFalse() {
		Section set1 = new Section();
		Section set2 = new Section();
		set2.add(new GameSquare(SquareValue.ONE, 0, 0));
		
		// different sizes
		assertFalse(set1.equals(set2));
		assertFalse(set2.equals(set1));
		
		set1.add(new GameSquare(SquareValue.TWO, 0, 0));
		
		// Same size, different squares
		assertFalse(set1.equals(set2));
		assertFalse(set2.equals(set1));
	}
	
	@Test
	public void testHashCodeEquals() {
		Section set1 = new Section();
		Section set2 = new Section();
		
		set1.add(new GameSquare(SquareValue.TWO, 0, 0));
		set2.add(new GameSquare(SquareValue.TWO, 0, 0));
		
		// Two different objects, but they are the "Same" so this should be equal
		assertEquals(set1.hashCode(), set2.hashCode());
	}
	
	@Test
	public void testHashCodeNotEquals01() {
		Section set1 = new Section();
		Section set2 = new Section();
		
		set1.add(new GameSquare(SquareValue.TWO, 0, 2));
		set2.add(new GameSquare(SquareValue.TWO, 0, 0));
		
		assertNotEquals(set1.hashCode(), set2.hashCode());
	}
	
	@Test
	public void testHashCodeNotEquals02() {
		Section set1 = new Section();
		Section set2 = new Section();
		
		set1.add(new GameSquare(SquareValue.TWO, 1, 2));
		set2.add(new GameSquare(SquareValue.TWO, 0, 2));
		
		assertNotEquals(set1.hashCode(), set2.hashCode());
	}
	
	@Test
	public void testHashCodeNotEquals03() {
		Section set1 = new Section();
		Section set2 = new Section();
		
		set1.add(new GameSquare(SquareValue.TWO, 0, 2));
		set1.add(new GameSquare(SquareValue.TWO, 0, 0));
		set2.add(new GameSquare(SquareValue.TWO, 0, 2));
		
		assertNotEquals(set1.hashCode(), set2.hashCode());
	}
	
	// TODO: Not sure why eclipse is using wrong JUnit version.
	private void assertNotEquals(Object obj1, Object obj2) {
		assertFalse(obj1.equals(obj2));
	}
}
