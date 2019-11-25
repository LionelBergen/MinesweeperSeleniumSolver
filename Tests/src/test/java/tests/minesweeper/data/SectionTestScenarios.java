package tests.minesweeper.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import component.model.GameSquare;
import component.model.SquareValue;
import solver.component.Section;
import solver.component.SweeperSet;
import tests.minesweeper.data.component.SectionTestScenario;

public class SectionTestScenarios {
	public static final SectionTestScenario SCENARIO_01 = getScenario01();
	public static final SectionTestScenario SCENARIO_02 = getScenario02();
	
	private static SectionTestScenario getScenario01() {
		Section section = GameBoardTestScenarios.SCENARIO_01.getExpectedSections().iterator().next();
		
		List<GameSquare> gameSquares = new ArrayList<GameSquare>();
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 1));
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1));
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 1));
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 2));
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 2));
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 3));
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 3));
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 3));
		
		return new SectionTestScenario(section, Arrays.asList(new SweeperSet(gameSquares, 1)));
	}
	
	private static SectionTestScenario getScenario02() {
		Section section = GameBoardTestScenarios.SCENARIO_03.getExpectedSections().iterator().next();
		
		// # 2
		List<GameSquare> gameSquareResults1 = new ArrayList<GameSquare>();
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 0));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 0));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 0));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 1));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 2));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 2));
		
		// # 3
		List<GameSquare> gameSquareResults2 = new ArrayList<GameSquare>();
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 1));
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1));
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 2));
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 2));
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 3));
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 3));
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 3));
		
		List<SweeperSet> expectedResults = Arrays.asList(
				createSweeperSet(gameSquareResults1, 2),
				createSweeperSet(gameSquareResults2, 3)
				);
		return new SectionTestScenario(section, expectedResults);
	}
	
	private static SweeperSet createSweeperSet(List<GameSquare> gameSquares, int expectedNumberOfMines) {
		return new SweeperSet(gameSquares, expectedNumberOfMines);
	}
}
