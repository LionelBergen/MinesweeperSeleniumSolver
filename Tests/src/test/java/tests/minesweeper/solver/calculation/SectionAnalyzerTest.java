package tests.minesweeper.solver.calculation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import component.model.Section;
import component.model.gamesquare.GameSquare;
import component.model.gamesquare.SquareValue;
import solver.board.analyzing.SectionAnalyzer;
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
	
	private void testScenario(SectionTestScenario scenario) {
		List<GameSquare> allSquares = scenario.getSections().stream().map(e -> e.getGameSquares().stream().collect(Collectors.toList())).flatMap(List::stream).collect(Collectors.toList());
		
		Collection<Section> results = SectionAnalyzer.getSections(scenario.getExpectedRules(), allSquares);
		
		// TODO: assert results
	}
}
