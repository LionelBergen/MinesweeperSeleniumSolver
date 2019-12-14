package tests.minesweeper.solver.calculation.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static solver.component.transformer.SectionTransformer.transformSectionsToKeyValues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

import component.model.Section;
import component.model.gamesquare.GameSquare;
import component.model.gamesquare.SquareValue;
import solver.calculation.RulesCombinationCalculator;
import solver.component.KeyValue;
import solver.component.Rule;
import tests.minesweeper.data.SectionTestScenarios;

public class RulesCombinationCalculatorTest {
	// Sort used for debugging
	private static final Map<String, Integer> sortingMap = new HashMap<>();
	static {
		sortingMap.put("G", 999);
		sortingMap.put("J", 998);
		sortingMap.put("O", 997);
		sortingMap.put("V", 996);
		sortingMap.put("I", 995);
		sortingMap.put("K", 994);
		sortingMap.put("Y", 993);
		sortingMap.put("L", 992);
		sortingMap.put("C", 991);
		sortingMap.put("R", 990);
	}
	
	private int compare(Object s1, Object s2) {
		return findValue(s2).compareTo(findValue(s1));
	}
	
	private Integer findValue(Object section) {
		String sectionAsString = section.toString();
		
		for (int i=0; i<sectionAsString.length(); i++) {
			Integer valueFound = sortingMap.get(String.valueOf(sectionAsString.charAt(i)));
			if (valueFound != null) {
				return valueFound;
			}
		}
		
		throw new RuntimeException("Cannot find value for: " + section);
	}
	
	@Test
	public void testGetAllVariationsOfRules() {
		List<Rule> testRules = SectionTestScenarios.SCENARIO_SPECIAL_02.getExpectedOrigResults();
		List<Section> testSections = SectionTestScenarios.SCENARIO_SPECIAL_02.getExpectedContents()
				.values()
				.stream()
				.map(e -> new Section(e))
				.sorted((e1, e2) -> compare(e1, e2))
				.collect(Collectors.toList());
		
		// Note: Order matters, see SectionTestScenarios.SCENARIO_SPECIAL_02
		// G, J, ONM, VTP, IBFA, K, YUS, LHED, C, XWRQ
		List<KeyValue> excpectedResultA11B11 = getExpectedResult(new int[]{0, 1, 0, 1, 1, 0, 0, 0, 1, 1}, testSections);
		List<KeyValue> excpectedResultA11B21 = getExpectedResult(new int[]{0, 1, 0, 2, 1, 0, 1, 0, 1, 0}, testSections);
		
		List<KeyValue> excpectedResultA12B11 = getExpectedResult(new int[]{0, 0, 1, 1, 2, 0, 0, 0, 1, 1}, testSections);
		List<KeyValue> excpectedResultA12B21 = getExpectedResult(new int[]{0, 0, 1, 2, 2, 0, 1, 0, 1, 0}, testSections);
		
		List<KeyValue> excpectedResultA21B11 = getExpectedResult(new int[]{1, 0, 0, 1, 2, 0, 0, 0, 0, 1}, testSections);
		List<KeyValue> excpectedResultA21B21 = getExpectedResult(new int[]{1, 0, 0, 2, 2, 0, 1, 0, 0, 0}, testSections);
		
		List<KeyValue> excpectedResultA22B11 = getExpectedResult(new int[]{0, 1, 0, 1, 2, 0, 0, 1, 0, 1}, testSections);
		List<KeyValue> excpectedResultA22B21 = getExpectedResult(new int[]{0, 1, 0, 2, 2, 0, 1, 1, 0, 0}, testSections);
		
		List<KeyValue> excpectedResultA23B11 = getExpectedResult(new int[]{0, 0, 0, 1, 3, 1, 0, 0, 0, 1}, testSections);
		List<KeyValue> excpectedResultA23B21 = getExpectedResult(new int[]{0, 0, 0, 2, 3, 1, 1, 0, 0, 0}, testSections);
		
		List<KeyValue> excpectedResultA24B11 = getExpectedResult(new int[]{0, 0, 1, 1, 3, 0, 0, 1, 0, 1}, testSections);
		List<KeyValue> excpectedResultA24B21 = getExpectedResult(new int[]{0, 0, 1, 2, 3, 0, 1, 1, 0, 0}, testSections);
		
		List<List<KeyValue>> results = RulesCombinationCalculator.getAllVariations(testSections, testRules);
		
		for (List<KeyValue> g : results) {
			for (KeyValue x : g) {
				// Set all maxValues to 0 so we can assert properly (expected all contain a maxValue of 0)
				x.setMaxValue(0);
			}
			
			g.sort((e1, e2) -> compare(e1, e2));
		}
		
		assertTrue(results.contains(excpectedResultA11B11));
		assertTrue(results.contains(excpectedResultA11B21));
		assertTrue(results.contains(excpectedResultA12B11));
		assertTrue(results.contains(excpectedResultA12B21));
		assertTrue(results.contains(excpectedResultA21B11));
		assertTrue(results.contains(excpectedResultA21B21));
		assertTrue(results.contains(excpectedResultA22B11));
		assertTrue(results.contains(excpectedResultA22B21));
		assertTrue(results.contains(excpectedResultA23B11));
		assertTrue(results.contains(excpectedResultA23B21));
		assertTrue(results.contains(excpectedResultA24B11));
		assertTrue(results.contains(excpectedResultA24B21));
		
		assertEquals(12, results.size());
	}
	
	@Test
	public void testGetAllVariationsOfARule() {
		GameSquare squareA = new GameSquare("A", SquareValue.BLANK_UNTOUCHED, 1, 1);
		GameSquare squareB = new GameSquare("B", SquareValue.BLANK_UNTOUCHED, 1, 2);
		GameSquare squareF = new GameSquare("F", SquareValue.BLANK_UNTOUCHED, 1, 3);
		GameSquare squareI = new GameSquare("I", SquareValue.BLANK_UNTOUCHED, 1, 4);
		GameSquare squareC = new GameSquare("C", SquareValue.BLANK_UNTOUCHED, 1, 5);
		GameSquare squareG = new GameSquare("G", SquareValue.BLANK_UNTOUCHED, 1, 6);
		GameSquare squareJ = new GameSquare("J", SquareValue.BLANK_UNTOUCHED, 1, 7);
		
		List<GameSquare> allSquares = Arrays.asList(squareA, squareB, squareC, squareF, squareI, squareG, squareJ);
		
		Section section1 = new Section();
		Section section2 = new Section();
		Section section3 = new Section();
		Section section4 = new Section();
		section1.setGameSquares(new HashSet<>(Arrays.asList(squareA, squareB, squareF, squareI)));
		section2.setGameSquares(new HashSet<>(Arrays.asList(squareC)));
		section3.setGameSquares(new HashSet<>(Arrays.asList(squareG)));
		section4.setGameSquares(new HashSet<>(Arrays.asList(squareJ)));
		
		List<Section> allSections = Arrays.asList(section1, section2, section3, section4);
		
		List<KeyValue> expectedResult1 = getExpectedResult(new int[]{1, 1, 1, 0}, allSections);
		List<KeyValue> expectedResult2 = getExpectedResult(new int[]{1, 1, 0, 1}, allSections);
		List<KeyValue> expectedResult3 = getExpectedResult(new int[]{1, 0, 1, 1}, allSections);
		List<KeyValue> expectedResult4 = getExpectedResult(new int[]{0, 1, 1, 1}, allSections);
		List<KeyValue> expectedResult5 = getExpectedResult(new int[]{2, 1, 0, 0}, allSections);
		List<KeyValue> expectedResult6 = getExpectedResult(new int[]{2, 0, 1, 0}, allSections);
		List<KeyValue> expectedResult7 = getExpectedResult(new int[]{2, 0, 0, 1}, allSections);
		List<KeyValue> expectedResult8 = getExpectedResult(new int[]{3, 0, 0, 0}, allSections);
				
		Rule rule = new Rule(allSquares, 3, section3);
		List<List<KeyValue>> results = RulesCombinationCalculator.getAllVariationsOfARule(allSections,  rule);
		
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
	
	// No known values
	@Test
	public void testGetAllVariationsOfARuleWithKnownValues01() {
		GameSquare squareA = new GameSquare("A", SquareValue.BLANK_UNTOUCHED, 1, 1);
		GameSquare squareB = new GameSquare("B", SquareValue.BLANK_UNTOUCHED, 1, 2);
		GameSquare squareF = new GameSquare("F", SquareValue.BLANK_UNTOUCHED, 1, 3);
		GameSquare squareI = new GameSquare("I", SquareValue.BLANK_UNTOUCHED, 1, 4);
		GameSquare squareC = new GameSquare("C", SquareValue.BLANK_UNTOUCHED, 1, 5);
		GameSquare squareG = new GameSquare("G", SquareValue.BLANK_UNTOUCHED, 1, 6);
		GameSquare squareJ = new GameSquare("J", SquareValue.BLANK_UNTOUCHED, 1, 7);
		
		List<GameSquare> allSquares = Arrays.asList(squareA, squareB, squareC, squareF, squareI, squareG, squareJ);
		
		Section section1 = new Section();
		Section section2 = new Section();
		Section section3 = new Section();
		Section section4 = new Section();
		section1.setGameSquares(new HashSet<>(Arrays.asList(squareA, squareB, squareF, squareI)));
		section2.setGameSquares(new HashSet<>(Arrays.asList(squareC)));
		section3.setGameSquares(new HashSet<>(Arrays.asList(squareG)));
		section4.setGameSquares(new HashSet<>(Arrays.asList(squareJ)));
		
		List<Section> allSections = Arrays.asList(section1, section2, section3, section4);
		List<KeyValue> allSectionsWithKnown = transformSectionsToKeyValues(allSections, 0);
		
		List<KeyValue> expectedResult1 = getExpectedResult(new int[]{1, 1, 1, 0}, allSections);
		List<KeyValue> expectedResult2 = getExpectedResult(new int[]{1, 1, 0, 1}, allSections);
		List<KeyValue> expectedResult3 = getExpectedResult(new int[]{1, 0, 1, 1}, allSections);
		List<KeyValue> expectedResult4 = getExpectedResult(new int[]{0, 1, 1, 1}, allSections);
		List<KeyValue> expectedResult5 = getExpectedResult(new int[]{2, 1, 0, 0}, allSections);
		List<KeyValue> expectedResult6 = getExpectedResult(new int[]{2, 0, 1, 0}, allSections);
		List<KeyValue> expectedResult7 = getExpectedResult(new int[]{2, 0, 0, 1}, allSections);
		List<KeyValue> expectedResult8 = getExpectedResult(new int[]{3, 0, 0, 0}, allSections);
				
		Rule rule = new Rule(allSquares, 3, section3);
		List<List<KeyValue>> results = RulesCombinationCalculator.getAllVariationsOfARuleWithKnownValues(allSectionsWithKnown, rule);
		
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
	
	// two known values
	@Test
	public void testGetAllVariationsOfARuleWithKnownValues02() {
		GameSquare squareA = new GameSquare("A", SquareValue.BLANK_UNTOUCHED, 1, 1);
		GameSquare squareB = new GameSquare("B", SquareValue.BLANK_UNTOUCHED, 1, 2);
		GameSquare squareF = new GameSquare("F", SquareValue.BLANK_UNTOUCHED, 1, 3);
		GameSquare squareI = new GameSquare("I", SquareValue.BLANK_UNTOUCHED, 1, 4);
		GameSquare squareC = new GameSquare("C", SquareValue.BLANK_UNTOUCHED, 1, 5);
		GameSquare squareG = new GameSquare("G", SquareValue.BLANK_UNTOUCHED, 1, 6);
		GameSquare squareJ = new GameSquare("J", SquareValue.BLANK_UNTOUCHED, 1, 7);
		
		List<GameSquare> allSquares = Arrays.asList(squareA, squareB, squareC, squareF, squareI, squareG, squareJ);
		
		Section section1 = new Section();
		Section section2 = new Section();
		Section section3 = new Section();
		Section section4 = new Section();
		section1.setGameSquares(new HashSet<>(Arrays.asList(squareA, squareB, squareF, squareI)));
		section2.setGameSquares(new HashSet<>(Arrays.asList(squareC)));
		section3.setGameSquares(new HashSet<>(Arrays.asList(squareG)));
		section4.setGameSquares(new HashSet<>(Arrays.asList(squareJ)));
		
		List<Section> allSections = Arrays.asList(section1, section2, section3, section4);
		List<KeyValue> allSectionsWithKnown = transformSectionsToKeyValues(allSections, 0);
		// set two known values
		allSectionsWithKnown.get(0).setValue(2);
		allSectionsWithKnown.get(1).setValue(1);
		
		List<KeyValue> expectedResult1 = getExpectedResult(new int[]{2, 1, 0, 0}, allSections);
				
		Rule rule = new Rule(allSquares, 3, section3);
		List<List<KeyValue>> results = RulesCombinationCalculator.getAllVariationsOfARuleWithKnownValues(allSectionsWithKnown, rule);
		
		assertTrue(results.contains(expectedResult1));
		assertEquals(1, results.size());
	}
	
	private List<KeyValue> getExpectedResult(int[] values, List<Section> sections) {
		List<KeyValue> list = new ArrayList<>();
		
		for (int i=0; i<values.length; i++) {
			list.add(new KeyValue(values[i], 0, sections.get(i)));
		}
		
		return list;
	}
}
