package tests.minesweeper.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import component.model.Section;
import component.model.gamesquare.GameSquare;
import component.model.gamesquare.SquareValue;
import solver.component.Rule;
import tests.minesweeper.data.component.SectionTestScenario;

import static tests.minesweeper.data.TestDataHelper.getGameSquare;
import static tests.minesweeper.data.TestDataHelper.makeCopy;

public class SectionTestScenarios {
	public static final SectionTestScenario SCENARIO_01 = getScenario01();
	public static final SectionTestScenario SCENARIO_02 = getScenario02();
	public static final SectionTestScenario SCENARIO_03 = getScenario03();
	
	public static final SectionTestScenario SCENARIO_SPECIAL_01 = getScenarioSpecial01();
	
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
		
		Map<List<Section>, List<GameSquare>> expectedContents = new HashMap<>();
		List<Section> ss = new ArrayList<>();
		ss.add(new Section(new HashSet<>(gameSquares)));
		expectedContents.put(ss, gameSquares);
		
		return new SectionTestScenario(Arrays.asList(section), expectedContents, Arrays.asList(new Rule(gameSquares, 1)));
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
		
		Rule touchingThe2 = createSweeperSet(gameSquareResults1, 2);
		Rule touchingThe3 = createSweeperSet(gameSquareResults2, 3);
		
		List<Rule> expectedResults = Arrays.asList(
				touchingThe2,
				touchingThe3
				);
		
		List<GameSquare> resultSet1 = Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 0),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 0),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 0));
		List<Section> parentSet1 = Arrays.asList(new Section(new HashSet<>(touchingThe2.getSquares())));
		
		List<GameSquare> resultSet2 = Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 1),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 2),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 2));
		List<Section> parentSet2 = Arrays.asList(new Section(new HashSet<>(touchingThe2.getSquares())),
				new Section(new HashSet<>(touchingThe3.getSquares())));
		
		List<GameSquare> resultSet3 = Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 3),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 3),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 3));
		List<Section> parentSet3 = Arrays.asList(new Section(new HashSet<>(touchingThe3.getSquares())));
		
		Map<List<Section>, List<GameSquare>> expectedContents = new HashMap<>();
		
		expectedContents.put(parentSet1, resultSet1);
		expectedContents.put(parentSet2, resultSet2);
		expectedContents.put(parentSet3, resultSet3);
		
		return new SectionTestScenario(Arrays.asList(section), expectedContents, expectedResults);
	}
	
	// Similar as above but with 2 flags & a 4 instead of a 3
	private static SectionTestScenario getScenario03() {
		// setup test data
		// make a copy so we don't mess up the original data
		Section section = makeCopy(GameBoardTestScenarios.SCENARIO_03.getExpectedSections().iterator().next());
		List<GameSquare> gameSquareList = new ArrayList<GameSquare>(section.getGameSquares());
		
		getGameSquare(gameSquareList, 1, 2).setValue(SquareValue.FOUR);
		getGameSquare(gameSquareList, 1, 3).setValue(SquareValue.FLAGGED);
		getGameSquare(gameSquareList, 2, 3).setValue(SquareValue.FLAGGED);
		
		// # 2
		List<GameSquare> gameSquareResults1 = new ArrayList<GameSquare>();
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 0));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 0));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 0));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 1));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 2));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 2));
		
		// # 4
		List<GameSquare> gameSquareResults2 = new ArrayList<GameSquare>();
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 1));
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1));
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 2));
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 2));
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 3));
		
		Rule touchingThe2 = createSweeperSet(gameSquareResults1, 2);
		Rule touchingThe4 = createSweeperSet(gameSquareResults2, 2);
		
		List<Rule> expectedResults = Arrays.asList(
				touchingThe2,
				touchingThe4
				);
		
		List<GameSquare> resultSet1 = Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 0),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 0),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 0));
		List<Section> parentSet1 = Arrays.asList(new Section(touchingThe2.getSquares()));
		
		List<GameSquare> resultSet2 = Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 1),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 2),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 2));
		List<Section> parentSet2 = Arrays.asList(new Section(touchingThe2.getSquares()),
				new Section(touchingThe4.getSquares()));
		
		List<GameSquare> resultSet3 = Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 3));
		List<Section> parentSet3 = Arrays.asList(new Section(touchingThe4.getSquares()));
		
		Map<List<Section>, List<GameSquare>> expectedContents = new HashMap<>();
		
		expectedContents.put(parentSet1, resultSet1);
		expectedContents.put(parentSet2, resultSet2);
		expectedContents.put(parentSet3, resultSet3);
		
		return new SectionTestScenario(Arrays.asList(section), expectedContents, expectedResults);
	}
	
	// Here: https://math.stackexchange.com/questions/3447402/minesweeper-odds-for-this-scenario-2-different-calculations
	private static SectionTestScenario getScenarioSpecial01() {
		Section section = GameBoardTestScenarios.SCENARIO_SPECIAL_01.getExpectedSections().iterator().next();
		
		// # 3 (2. 2)
		List<GameSquare> gameSquareResults1 = new ArrayList<GameSquare>();
		gameSquareResults1.add(new GameSquare("A", SquareValue.BLANK_UNTOUCHED, 1, 1));
		gameSquareResults1.add(new GameSquare("B", SquareValue.BLANK_UNTOUCHED, 2, 1));
		gameSquareResults1.add(new GameSquare("C", SquareValue.BLANK_UNTOUCHED, 3, 1));
		gameSquareResults1.add(new GameSquare("F", SquareValue.BLANK_UNTOUCHED, 1, 2));
		gameSquareResults1.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 2));
		gameSquareResults1.add(new GameSquare("I", SquareValue.BLANK_UNTOUCHED, 1, 3));
		gameSquareResults1.add(new GameSquare("J", SquareValue.BLANK_UNTOUCHED, 2, 3));
		
		// # 1 (4, 2)
		List<GameSquare> gameSquareResults2 = new ArrayList<GameSquare>();
		gameSquareResults2.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 1));
		gameSquareResults2.add(new GameSquare("D", SquareValue.BLANK_UNTOUCHED, 4, 1));
		gameSquareResults2.add(new GameSquare("E", SquareValue.BLANK_UNTOUCHED, 5, 1));
		gameSquareResults2.add(new GameSquare("G", SquareValue.BLANK_UNTOUCHED, 3, 2));
		gameSquareResults2.add(new GameSquare("H", SquareValue.BLANK_UNTOUCHED, 5, 2));
		gameSquareResults2.add(new GameSquare("K", SquareValue.BLANK_UNTOUCHED, 4, 3));
		gameSquareResults2.add(new GameSquare("L", SquareValue.BLANK_UNTOUCHED, 5, 3));
		
		// # 1 (3, 3)
		List<GameSquare> gameSquareResults3 = new ArrayList<GameSquare>();
		gameSquareResults3.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 2));
		gameSquareResults3.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 3));
		gameSquareResults3.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 3));
		gameSquareResults3.add(new GameSquare("N", SquareValue.BLANK_UNTOUCHED, 2, 4));
		gameSquareResults3.add(new GameSquare("O", SquareValue.BLANK_UNTOUCHED, 3, 4));
		gameSquareResults3.add(new GameSquare("P", SquareValue.BLANK_UNTOUCHED, 4, 4));

		Rule touchingThe3 = createSweeperSet(gameSquareResults1, 3);
		Rule touchingThe11 = createSweeperSet(gameSquareResults2, 1);
		Rule touchingThe12 = createSweeperSet(gameSquareResults3, 1);
		
		List<Rule> expectedResults = Arrays.asList(
				touchingThe3,
				touchingThe11,
				touchingThe12
				);
		
		// Green
		List<GameSquare> resultSet1 = Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 1),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 2),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 3));
		List<Section> parentSet1 = Arrays.asList(new Section(touchingThe3.getSquares()));
		
		// Yellow
		List<GameSquare> resultSet2 = Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 3));
		List<Section> parentSet2 = Arrays.asList(new Section(touchingThe3.getSquares()),
				new Section(touchingThe12.getSquares()));
		
		// Light-blue
		List<GameSquare> resultSet3 = Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 4),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 4),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 4));
		List<Section> parentSet3 = Arrays.asList(new Section(touchingThe12.getSquares()));
		
		// Pink
		List<GameSquare> resultSet4 = Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 1));
		List<Section> parentSet4 = Arrays.asList(new Section(touchingThe11.getSquares()),
				new Section(touchingThe3.getSquares()));
		
		// Brown
		List<GameSquare> resultSet5 = Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 2));
		List<Section> parentSet5 = Arrays.asList(new Section(touchingThe11.getSquares()),
				new Section(touchingThe3.getSquares()),
				new Section(touchingThe12.getSquares()));
		
		// Purple
		List<GameSquare> resultSet6 = Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 3));
		List<Section> parentSet6 = Arrays.asList(new Section(touchingThe11.getSquares()),
				new Section(touchingThe12.getSquares()));
		
		// Orange
		List<GameSquare> resultSet7 = Arrays.asList(
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 1),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 5, 1),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 5, 2),
				new GameSquare(SquareValue.BLANK_UNTOUCHED, 5, 3));
		List<Section> parentSet7 = Arrays.asList(new Section(touchingThe11.getSquares()));
		
		Map<List<Section>, List<GameSquare>> expectedContents = new HashMap<>();
		
		expectedContents.put(parentSet1, resultSet1);
		expectedContents.put(parentSet2, resultSet2);
		expectedContents.put(parentSet3, resultSet3);
		expectedContents.put(parentSet4, resultSet4);
		expectedContents.put(parentSet5, resultSet5);
		expectedContents.put(parentSet6, resultSet6);
		expectedContents.put(parentSet7, resultSet7);
		
		return new SectionTestScenario(Arrays.asList(section), expectedContents, expectedResults);
	}
	
	private static Rule createSweeperSet(List<GameSquare> gameSquares, int expectedNumberOfMines) {
		return new Rule(gameSquares, expectedNumberOfMines);
	}
}
