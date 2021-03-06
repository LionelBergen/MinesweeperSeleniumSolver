package tests.minesweeper.solver.calculation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Test;

import component.model.Section;
import component.model.gamesquare.GameSquare;
import solver.board.analyzing.BoardAnalyzer;
import solver.board.analyzing.SectionAnalyzer;
import solver.calculation.OddsCalculator;
import solver.calculation.RulesCombinationCalculator;
import solver.component.AssignedValue;
import solver.component.Rule;
import tests.minesweeper.data.component.GameBoardTestScenario;
import tests.minesweeper.solver.data.GameBoardTestData;

public class OddsCalculatorTest {
	@Test
	public void testCalculateOdds() {
		GameBoardTestScenario test = GameBoardTestData.SPECIAL_SCENARIO_03;
		List<List<AssignedValue>> resultsComplete = getResultsComplete(test);

		Map<Section, BigDecimal> results = OddsCalculator.calculateOdds(resultsComplete, 25, 123);
		Map<String, BigDecimal> expectedResults = new HashMap<>();
		
		expectedResults.put("C", BigDecimal.valueOf(0.34941864));
		expectedResults.put("OMN", BigDecimal.valueOf(0.10046036));
		expectedResults.put("J", BigDecimal.valueOf(0.41115781));
		expectedResults.put("G", BigDecimal.valueOf(0.24630165));
		expectedResults.put("K", BigDecimal.valueOf(0.04115945));
		expectedResults.put("AFBI", BigDecimal.valueOf(0.49828047));
		expectedResults.put("XRWQ", BigDecimal.valueOf(0.21158360));
		expectedResults.put("SUY", BigDecimal.valueOf(0.05122186));
		expectedResults.put("VTP", BigDecimal.valueOf(0.38455520));
		expectedResults.put("LEHD", BigDecimal.valueOf(0.09078006));
		
		for (Entry<Section, BigDecimal> x : results.entrySet()) {
			BigDecimal expectedResult = getExpectedResult(x.getKey(), expectedResults);
			BigDecimal actualResult = x.getValue();
			assertNotNull("Unexpected entry: " + x.getKey().toString(), expectedResult);
			
			// set scale for better comparison (If we're <= 0.00000009 off, it doesn't matter).
			actualResult = actualResult.setScale(8, RoundingMode.HALF_DOWN);
			expectedResult = expectedResult.setScale(8, RoundingMode.HALF_DOWN);
			
			assertTrue("Expected: " + expectedResult + " but was: " + actualResult, expectedResult.compareTo(actualResult) == 0);
		}
		
		assertEquals(expectedResults.size(), results.size());
	}
	
	private BigDecimal getExpectedResult(Object object, Map<String, BigDecimal> expectedResults) {
		for (String s : expectedResults.keySet()) {
			if (s.contains(object.toString().substring(0, 1))) {
				return expectedResults.get(s);
			}
		}
		
		return null;
	}
	
	private static List<List<AssignedValue>> getResultsComplete(GameBoardTestScenario test) {
		List<GameSquare> allGameSquares = test.getGameBoard().getGameBoard();
		List<Section> sections = (List<Section>) BoardAnalyzer.breakupBoard(test.getGameBoard());
		List<Rule> allRules = SectionAnalyzer.breakupSectionIntoRules(sections);
		Collection<Section> allSections = SectionAnalyzer.getSections(allRules, allGameSquares);
		List<List<AssignedValue>> resultsComplete = RulesCombinationCalculator.getAllVariations(allSections, allRules);
		
		return resultsComplete;
	}
}