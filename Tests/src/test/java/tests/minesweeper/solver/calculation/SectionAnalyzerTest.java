package tests.minesweeper.solver.calculation;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import component.model.GameSquare;
import component.model.SquareValue;
import solver.board.analyzing.SectionAnalyzer;
import solver.component.Section;
import solver.component.SweeperSet;
import tests.minesweeper.data.TestScenarios;
import tests.minesweeper.data.component.TestScenario;

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
	
	@Test
	public void testScenario01() {
		TestScenario testScenario = TestScenarios.SCENARIO_01;
		
		Section section = testScenario.getExpectedSections().get(0);
		
		List<SweeperSet> results = SectionAnalyzer.breakupSection(section);
		assertEquals(1, results.size());
	}
}
