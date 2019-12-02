package tests.minesweeper.solver.calculation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import component.model.GameSquare;
import component.model.SquareValue;
import solver.board.analyzing.SectionAnalyzer;
import solver.component.Section;
import solver.component.SectionAnalyzedResults;
import solver.component.Rule;
import tests.minesweeper.data.SectionTestScenarios;
import tests.minesweeper.data.component.SectionTestScenario;

public class SectionAnalyzerTest {
	@Test
	public void testEmptySection() {
		Section section = new Section();
		
		// test rules
		List<Rule> resultRules = SectionAnalyzer.breakupSectionIntoRules(section);
		assertEquals(0, resultRules.size());
		
		// test sections
		Collection<Section> resultSections = SectionAnalyzer.getSections(resultRules, section.getGameSquares());
		assertEquals(0, resultSections.size());
	}
	
	@Test
	public void testBlankSection() {
		Set<GameSquare> blankSquareSet = new HashSet<GameSquare>();
		blankSquareSet.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 0));
		blankSquareSet.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 4));
		blankSquareSet.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 6));
		blankSquareSet.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 4));
		
		Section section = new Section(blankSquareSet);
		
		// test rules
		List<Rule> resultRules = SectionAnalyzer.breakupSectionIntoRules(section);
		assertEquals(0, resultRules.size());
		
		// test sections
		Collection<Section> resultSections = SectionAnalyzer.getSections(resultRules, section.getGameSquares());
		assertEquals(0, resultSections.size());
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
	
	// https://math.stackexchange.com/questions/3447402/minesweeper-odds-for-this-scenario-2-different-calculations
	@Test
	public void testSpecial101() {
		testScenario(SectionTestScenarios.SCENARIO_SPECIAL_01);
	}
	
	private void testScenario(SectionTestScenario scenario) {
		// TODO: delete the 'breakupSection' method...
		SectionAnalyzedResults actualResult = SectionAnalyzer.breakupSection(scenario.getSection());
		List<Rule> resultRules = SectionAnalyzer.breakupSectionIntoRules(scenario.getSection());
		
		// assert rules found
		assertEquals(scenario.getExpectedOrigResults().size(), resultRules.size());
		for (Rule expected : scenario.getExpectedOrigResults()) {
			assertTrue("Original Results did not contain expected: " + expected, resultRules.contains(expected));
		}
		
		// assert contents
		Collection<Section> resultContents = SectionAnalyzer.getSections(resultRules, scenario.getSection().getGameSquares());
		//List<Set<Section>> actualContents = actualResult.getResultSets();
		assertEquals(scenario.getExpectedContents().size(), resultContents.size());
		
		for (Entry<List<Section>, List<GameSquare>> expected : scenario.getExpectedContents().entrySet()) {
			// List<Section> resultList = new ArrayList<Section>(resultContents);
			//Section y = resultContents.stream().filter(e -> expected.getKey().contains(e)).findAny().get();
			Set<GameSquare> x = actualResult.get(expected.getKey());
			
			assertTrue("Results did not contain expected value from: " + expected, x.containsAll(expected.getValue()));
		}
	}
}
