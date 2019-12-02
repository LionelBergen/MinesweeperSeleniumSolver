package tests.minesweeper.solver.calculation.board;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import component.model.GameSquare;
import component.model.SquareValue;
import solver.board.analyzing.SectionAnalyzer;
import solver.calculation.board.MineOddsCalculator;
import solver.component.Rule;
import solver.component.Section;
import tests.minesweeper.data.SectionTestScenarios;

public class MineOddsCalculatorTest {
	private static final double DELTA = 1e-4;
	
	@Test
	public void calculateOddsTest() {
		Section section = SectionTestScenarios.SCENARIO_SPECIAL_01.getSection();
		
		List<Rule> rules = SectionAnalyzer.breakupSectionIntoRules(section);
		Collection<Section> allSubSectionsFromRules = SectionAnalyzer.getSections(rules, section.getGameSquares());
		
		Map<Section, Double> results = MineOddsCalculator.calculateOdds(rules, allSubSectionsFromRules);
		
		GameSquare gsC = new GameSquare("C", SquareValue.BLANK_UNTOUCHED, 3, 1);
		GameSquare gsN = new GameSquare("N", SquareValue.BLANK_UNTOUCHED, 2, 4);
		GameSquare gsO = new GameSquare("O", SquareValue.BLANK_UNTOUCHED, 3, 4);
		GameSquare gsP = new GameSquare("P", SquareValue.BLANK_UNTOUCHED, 4, 4);
		
		assertGameSquareOdds(Double.valueOf(0.2115), gsC, results);
		assertGameSquareOdds(Double.valueOf(0.0705), gsN, results);
		assertGameSquareOdds(Double.valueOf(0.0705), gsO, results);
		assertGameSquareOdds(Double.valueOf(0.0705), gsP, results);
	}
	
	private void assertGameSquareOdds(Double expectedResultUpTo4DecimalPlaces, GameSquare gameSquare, Map<Section, Double> results) {
		assertEquals(expectedResultUpTo4DecimalPlaces, results.get(getSectionFromResults(gameSquare, results)), DELTA);
	}
	
	private Section getSectionFromResults(GameSquare needle, Map<Section, Double> hayStack) {
		for (Section section : hayStack.keySet()) {
			if (section.getGameSquares().contains(needle)) {
				return section;
			}
		}
		
		return null;
	}
}