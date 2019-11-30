package tests.minesweeper.solver.calculation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.Test;

import solver.calculation.MathCombinationCalculator;

public class MathCombinationCalculatorTest {
	@Test
	public void testGetAllNumbersThatAddUpTo01() {
		List<Integer> expectedResult = Arrays.asList(1, 0, 0, 0);
		
		Set<List<Integer>> results = MathCombinationCalculator.getAllNumberCombinations(1, 4);
		
		assertTrue(results.contains(expectedResult));
		assertEquals(1, results.size());
	}
	
	@Test
	public void testGetAllNumbersThatAddUpTo02() {
		List<Integer> expectedResult1 = Arrays.asList(1, 1, 0, 0);
		List<Integer> expectedResult2 = Arrays.asList(2, 0, 0, 0);
		
		Set<List<Integer>> results = MathCombinationCalculator.getAllNumberCombinations(2, 4);
		
		assertTrue(results.contains(expectedResult1));
		assertTrue(results.contains(expectedResult2));
		assertEquals(2, results.size());
	}
	
	@Test
	public void testGetAllNumbersThatAddUpTo03() {
		List<Integer> expectedResult1 = Arrays.asList(6, 0, 0, 0);
		List<Integer> expectedResult2 = Arrays.asList(5, 1, 0, 0);
		List<Integer> expectedResult3 = Arrays.asList(4, 2, 0, 0);
		List<Integer> expectedResult4 = Arrays.asList(4, 1, 1, 0);
		List<Integer> expectedResult5 = Arrays.asList(3, 3, 0, 0);
		List<Integer> expectedResult6 = Arrays.asList(3, 2, 1, 0);
		List<Integer> expectedResult7 = Arrays.asList(3, 1, 1, 1);
		List<Integer> expectedResult8 = Arrays.asList(2, 2, 2, 0);
		List<Integer> expectedResult9 = Arrays.asList(2, 2, 1, 1);
		
		Set<List<Integer>> results = MathCombinationCalculator.getAllNumberCombinations(6, 4);
		
		assertTrue(results.contains(expectedResult1));
		assertTrue(results.contains(expectedResult2));
		assertTrue(results.contains(expectedResult3));
		assertTrue(results.contains(expectedResult4));
		assertTrue(results.contains(expectedResult5));
		assertTrue(results.contains(expectedResult6));
		assertTrue(results.contains(expectedResult7));
		assertTrue(results.contains(expectedResult8));
		assertTrue(results.contains(expectedResult9));
		assertEquals(9, results.size());
	}
}
