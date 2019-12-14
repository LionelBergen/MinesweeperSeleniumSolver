package tests.minesweeper.solver.calculation.board;

import static org.junit.Assert.assertEquals;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Ignore;
import org.junit.Test;

import component.model.Section;
import component.model.gamesquare.GameSquare;
import component.model.gamesquare.SquareValue;
import solver.board.analyzing.SectionAnalyzer;
import solver.calculation.board.MineOddsCalculator;
import solver.component.Rule;
import tests.minesweeper.data.SectionTestScenarios;

@Ignore
public class MineOddsCalculatorTest {
	private static final double DELTA = 1e-4;
	
	@Test
	public void calculateOddsTest() {
		List<Section> sections = SectionTestScenarios.SCENARIO_SPECIAL_01.getSections();
		
		for (Section section : sections) {
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
		
		assertEquals(1, sections.size());
	}

	@Test
	public void calculateOddsTest02() {
		List<Section> sections = SectionTestScenarios.SCENARIO_SPECIAL_02.getSections();
		List<GameSquare> allGameSquares = sections.stream().flatMap(e -> e.getGameSquares().stream()).collect(Collectors.toList());
		
		//for (Section section : sections) 
		{
			List<Rule> rules = SectionAnalyzer.breakupSectionIntoRules(sections);
			Collection<Section> allSubSectionsFromRules = SectionAnalyzer.getSections(rules, allGameSquares);
			
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
		
		assertEquals(1, sections.size());
	}
	
	private void assertGameSquareOdds(Double expectedResultUpTo4DecimalPlaces, GameSquare gameSquare, Map<Section, Double> results) {
		Section resultSection = getSectionFromResults(gameSquare, results);
		Double result = results.get(resultSection);
		assertEquals(expectedResultUpTo4DecimalPlaces, result, DELTA);
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
