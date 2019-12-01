package tests.minesweeper.solver.calculation;

import org.junit.Test;

import solver.component.KeyValue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static solver.board.analyzing.SolutionAnalyzer.getAllPossibilities;

import java.util.Arrays;
import java.util.List;

public class SolutionAnalyzerTest {
	// (G) + (C) + (D+E+H+L) + (K) = 1
	@Test
	public void testGetAllPossibilities01() {
		List<KeyValue> gameSquares = Arrays.asList(
				new KeyValue(1, "G"),
				new KeyValue(1, "C"),
				new KeyValue(1, "K"),
				new KeyValue(4, "LHED"));
		
		List<KeyValue> expectedResult1 = Arrays.asList(
				new KeyValue(1, 0, "G"), 
				new KeyValue(0, 0, "C"), 
				new KeyValue(0, 0, "K"),
				new KeyValue(0, 0, "LHED"));
		
		List<KeyValue> expectedResult2 = Arrays.asList(
				new KeyValue(0, 0, "G"), 
				new KeyValue(1, 0, "C"), 
				new KeyValue(0, 0, "K"),
				new KeyValue(0, 0, "LHED"));
		
		List<KeyValue> expectedResult3 = Arrays.asList(
				new KeyValue(0, 0, "G"), 
				new KeyValue(0, "C"), 
				new KeyValue(1, 0, "K"),
				new KeyValue(0, 0, "LHED"));
		
		List<KeyValue> expectedResult4 = Arrays.asList(
				new KeyValue(0, 0, "G"), 
				new KeyValue(0, 0, "C"), 
				new KeyValue(0, 0, "K"),
				new KeyValue(1, 0, "LHED"));
		
		List<List<KeyValue>> results = getAllPossibilities(1, gameSquares);
		
		// We don't care about the order
		assertTrue(results.contains(expectedResult1));
		assertTrue(results.contains(expectedResult2));
		assertTrue(results.contains(expectedResult3));
		assertTrue(results.contains(expectedResult4));
		
		assertEquals(4, results.size());
	}
	
	// (A+B+F+I) + (C) + (G) + (J) = 3
	@Test
	public void testGetAllPossibilities02() {
		List<KeyValue> gameSquares = Arrays.asList(
				new KeyValue(4, "ABFI"),
				new KeyValue(1, "C"),
				new KeyValue(1, "G"),
				new KeyValue(1, "J"));
		
		List<KeyValue> expectedResult1 = Arrays.asList(
				new KeyValue(3, 0, "ABFI"),
				new KeyValue(0, 0, "C"),
				new KeyValue(0, 0, "G"),
				new KeyValue(0, 0, "J"));
		
		List<KeyValue> expectedResult2 = Arrays.asList(
				new KeyValue(2, 0, "ABFI"),
				new KeyValue(1, 0, "C"),
				new KeyValue(0, 0, "G"),
				new KeyValue(0, 0, "J"));
		
		List<KeyValue> expectedResult3 = Arrays.asList(
				new KeyValue(2, 0, "ABFI"),
				new KeyValue(0, 0, "C"),
				new KeyValue(1, 0, "G"),
				new KeyValue(0, 0, "J"));
		
		List<KeyValue> expectedResult4 = Arrays.asList(
				new KeyValue(2, 0, "ABFI"),
				new KeyValue(0, 0, "C"),
				new KeyValue(0, 0, "G"),
				new KeyValue(1, 0, "J"));
		
		List<KeyValue> expectedResult5 = Arrays.asList(
				new KeyValue(1, 0, "ABFI"),
				new KeyValue(1, 0, "C"),
				new KeyValue(1, 0, "G"),
				new KeyValue(0, 0, "J"));
		
		List<KeyValue> expectedResult6 = Arrays.asList(
				new KeyValue(1, 0, "ABFI"),
				new KeyValue(1, 0, "C"),
				new KeyValue(0, 0, "G"),
				new KeyValue(1, 0, "J"));
		
		List<KeyValue> expectedResult7 = Arrays.asList(
				new KeyValue(1, 0, "ABFI"),
				new KeyValue(0, 0, "C"),
				new KeyValue(1, 0, "G"),
				new KeyValue(1, 0, "J"));
		
		List<KeyValue> expectedResult8 = Arrays.asList(
				new KeyValue(0, 0, "ABFI"),
				new KeyValue(1, 0, "C"),
				new KeyValue(1, 0, "G"),
				new KeyValue(1, 0, "J"));
		
		List<List<KeyValue>> results = getAllPossibilities(3, gameSquares);
		
		assertTrue(results.contains(expectedResult1));
		assertTrue(results.contains(expectedResult2));
		assertTrue(results.contains(expectedResult3));
		assertTrue(results.contains(expectedResult4));
		assertTrue(results.contains(expectedResult5));
		assertTrue(results.contains(expectedResult6));
		assertTrue(results.contains(expectedResult7));
		assertTrue(results.contains(expectedResult8));
		
		assertEquals(8, results.size());
	}
}
