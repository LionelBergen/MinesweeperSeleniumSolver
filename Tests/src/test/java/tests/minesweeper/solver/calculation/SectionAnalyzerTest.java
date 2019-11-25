package tests.minesweeper.solver.calculation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import component.model.GameSquare;
import component.model.SquareValue;
import solver.board.analyzing.SectionAnalyzer;
import solver.component.Section;
import solver.component.SweeperSet;
import tests.minesweeper.data.SectionTestScenarios;
import tests.minesweeper.data.component.SectionTestScenario;

public class SectionAnalyzerTest {
	@Test
	public void testEmptySection() {
		Section section = new Section();
		
		List<SweeperSet> results = SectionAnalyzer.breakupSection(section);
		
		assertEquals(0, results.size());
	}
	
	@Test
	public void testBlankSection() {
		Set<GameSquare> blankSquareSet = new HashSet<GameSquare>();
		blankSquareSet.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 0));
		blankSquareSet.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 4));
		blankSquareSet.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 6));
		blankSquareSet.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 4));
		
		Section section = new Section(blankSquareSet);
		
		List<SweeperSet> results = SectionAnalyzer.breakupSection(section);
		assertEquals(0, results.size());
	}
	
	// Tests a simple 1 block surrounded by 8 blanks
	@Test
	public void testScenario01() {
		testScenario(SectionTestScenarios.SCENARIO_01);
	}
	
	// Tests a 2 touching a 3, surrounded by blanks
	@Test
	public void testScenario02() {
		testScenario(SectionTestScenarios.SCENARIO_02);
	}
	
	// Tests a 2 touching a 4, surrounded by blanks & 2 flags touching the 4
	@Test
	public void testScenario03() {
		testScenario(SectionTestScenarios.SCENARIO_03);
	}
	
	private void testScenario(SectionTestScenario scenario) {
		List<SweeperSet> actualResults = SectionAnalyzer.breakupSection(scenario.getSection());
		assertEquals(scenario.getExpectedResults().size(), actualResults.size());
		
		for (SweeperSet expected : scenario.getExpectedResults()) {
			assertTrue("Results did not contain expected: " + expected, actualResults.contains(expected));
		}
	}
}
