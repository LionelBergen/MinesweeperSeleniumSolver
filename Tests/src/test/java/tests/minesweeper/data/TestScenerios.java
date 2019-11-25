package tests.minesweeper.data;

import static tests.minesweeper.data.TestDataHelper.getValidGameBoard;
import static tests.minesweeper.data.TestDataHelper.getGameSquare;
import static tests.minesweeper.data.TestDataHelper.visualizeGameBoard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import component.model.GameSquare;
import component.model.RegularGameBoard;
import component.model.SquareValue;
import solver.component.Section;
import tests.minesweeper.data.component.TestScenerio;

public class TestScenerios {
	public static final TestScenerio SCENARIO_01 = getGameBoardScenario1();
	public static final TestScenerio SCENARIO_02 = getGameBoardScenario2();
	public static final TestScenerio SCENARIO_03 = getGameBoardScenario3();
	public static final TestScenerio SCENARIO_04 = getGameBoardScenario4();
	public static final TestScenerio SCENARIO_05 = getGameBoardScenario5();
	public static final TestScenerio SCENARIO_06 = getGameBoardScenario6();
	public static final TestScenerio SCENARIO_07 = getGameBoardScenario7();
	public static final TestScenerio SCENARIO_08 = getGameBoardScenario8();
	public static final TestScenerio SCENARIO_09 = getGameBoardScenario9();
	
	public static final TestScenerio SCENARIO_SPECIAL_01 = getGameBoardScenarioSpecial01();
	public static final TestScenerio SCENARIO_SPECIAL_02 = getGameBoardScenarioSpecial02();
	/**
	 * @return A Game board containing a 1 in the center
	 */
	private static TestScenerio getGameBoardScenario1() {
		// setup a game board
		RegularGameBoard gameBoard = getValidGameBoard(5, 5);
		List<GameSquare> squares = gameBoard.getGameBoard();
		getGameSquare(squares, 2, 2).setValue(SquareValue.ONE);
		
		// Based on board, this is what we expect as our results
		final List<GameSquare> expectedSectionResults = 
				Arrays.asList(new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 2),
						new GameSquare(SquareValue.ONE, 2, 2), new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 2), 
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 3), new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 3),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 3)
						);
		
		return createTestScenario(gameBoard, Arrays.asList(expectedSectionResults));
	}
	
	/**
	 * @return A Game board containing 2 seperate 1's
	 */
	private static TestScenerio getGameBoardScenario2() {
		// setup the game board
		RegularGameBoard gameBoard = getValidGameBoard(7, 7);
		List<GameSquare> squares = gameBoard.getGameBoard();
		getGameSquare(squares, 1, 1).setValue(SquareValue.ONE);
		getGameSquare(squares, 5, 2).setValue(SquareValue.ONE);

		// Based on the board, this is what we expect as our results
		final List<GameSquare> expectedResults1 = 
				Arrays.asList(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 0), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 0),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 0), new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 1),
						new GameSquare(SquareValue.ONE, 1, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1), 
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 2), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 2),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 2)
						);
		
		final List<GameSquare> expectedResults2 = 
				Arrays.asList(new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 2),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 3), new GameSquare(SquareValue.BLANK_UNTOUCHED, 5, 1),
						new GameSquare(SquareValue.ONE, 5, 2), new GameSquare(SquareValue.BLANK_UNTOUCHED, 5, 3), 
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 6, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 6, 2),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 6, 3)
						);
		

		return createTestScenario(gameBoard, Arrays.asList(expectedResults1, expectedResults2));
	}
	
	/**
	 * @return A Game board containing touching numbers & 1 untouching number
	 */
	private static TestScenerio getGameBoardScenario3() {
		// create game board
		RegularGameBoard gameBoard = getValidGameBoard(7, 7);
		List<GameSquare> squares = gameBoard.getGameBoard();
		getGameSquare(squares, 1, 1).setValue(SquareValue.TWO);
		getGameSquare(squares, 1, 2).setValue(SquareValue.THREE);
		getGameSquare(squares, 3, 5).setValue(SquareValue.THREE);

		// Based on test scenerio2, this is what we expect as our results
		final List<GameSquare> expectedResults1 = 
				Arrays.asList(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 0), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 0),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 0), new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 1),
						new GameSquare(SquareValue.TWO, 1, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1), 
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 2), new GameSquare(SquareValue.THREE, 1, 2),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 2), new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 3),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 3), new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 3)
						);
		
		final List<GameSquare> expectedResults2 = 
				Arrays.asList(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 4), new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 4),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 4), new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 5),
						new GameSquare(SquareValue.THREE, 3, 5), new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 5), 
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 6), new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 6),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 6)
						);
		
		return createTestScenario(gameBoard, Arrays.asList(expectedResults1, expectedResults2));
	}
	
	/**
	 * @return A Game board containing 2 1's, which are seperate and touching a wall or corner
	 */
	private static TestScenerio getGameBoardScenario4() {
		RegularGameBoard gameBoard = getValidGameBoard(5, 5);
		List<GameSquare> squares = gameBoard.getGameBoard();
		getGameSquare(squares, 0, 0).setValue(SquareValue.ONE);
		getGameSquare(squares, 3, 4).setValue(SquareValue.ONE);
		
		// Based on test scenerio4, this is what we expect as our results
		final List<GameSquare> expectedResults1 = 
				Arrays.asList(new GameSquare(SquareValue.ONE, 0, 0), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 0),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 1)
						);
		
		final List<GameSquare> expectedResults2 = 
				Arrays.asList(new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 3), new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 3),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 3), new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 4),
						new GameSquare(SquareValue.ONE, 3, 4), new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 4)
						);
		
		return createTestScenario(gameBoard, Arrays.asList(expectedResults1, expectedResults2));
	}
	
	/**
	 * @return A Game board containing 2 numbers separated by a single blank
	 */
	private static TestScenerio getGameBoardScenario5() {
		RegularGameBoard gameBoard = getValidGameBoard(4, 4);
		List<GameSquare> squares = gameBoard.getGameBoard();
		getGameSquare(squares, 0, 0).setValue(SquareValue.ONE);
		getGameSquare(squares, 0, 2).setValue(SquareValue.ONE);
		
		final List<GameSquare> expectedResults = 
				Arrays.asList(new GameSquare(SquareValue.ONE, 0, 0), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 0),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 1),
						new GameSquare(SquareValue.ONE, 0, 2), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 2),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 3), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 3)
						);
		
		return createTestScenario(gameBoard, Arrays.asList(expectedResults));
	}
	
	/**
	 * @return A Game board containing 2 numbers separated by a single blank - diagonally this time
	 */
	private static TestScenerio getGameBoardScenario6() {
		RegularGameBoard gameBoard = getValidGameBoard(4, 4);
		List<GameSquare> squares = gameBoard.getGameBoard();
		getGameSquare(squares, 0, 0).setValue(SquareValue.ONE);
		getGameSquare(squares, 2, 2).setValue(SquareValue.ONE);
		
		final List<GameSquare> expectedResults = 
				Arrays.asList(new GameSquare(SquareValue.ONE, 0, 0), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 0),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 1),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 1),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 2), new GameSquare(SquareValue.ONE, 2, 2),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 2), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 3),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 3), new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 3)
						);
		
		return createTestScenario(gameBoard, Arrays.asList(expectedResults));
	}
	
	/**
	 * @return A Game board containing 2 numbers separated by two spaces
	 */
	private static TestScenerio getGameBoardScenario7() {
		RegularGameBoard gameBoard = getValidGameBoard(4, 4);
		List<GameSquare> squares = gameBoard.getGameBoard();
		getGameSquare(squares, 0, 0).setValue(SquareValue.ONE);
		getGameSquare(squares, 0, 3).setValue(SquareValue.ONE);
		
		final List<GameSquare> expectedResults1 = 
				Arrays.asList(new GameSquare(SquareValue.ONE, 0, 0), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 0),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 1)
						);
		
		final List<GameSquare> expectedResults2 = 
				Arrays.asList(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 2), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 2),
						new GameSquare(SquareValue.ONE, 0, 3), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 3)
						);

		return createTestScenario(gameBoard, Arrays.asList(expectedResults1, expectedResults2));
	}
	
	/**
	 * @return A Game board containing 2 numbers separated by two flags
	 */
	private static TestScenerio getGameBoardScenario8() {
		RegularGameBoard gameBoard = getValidGameBoard(4, 4);
		List<GameSquare> squares = gameBoard.getGameBoard();
		getGameSquare(squares, 0, 0).setValue(SquareValue.ONE);
		getGameSquare(squares, 0, 1).setValue(SquareValue.FLAGGED);
		getGameSquare(squares, 0, 2).setValue(SquareValue.FLAGGED);
		getGameSquare(squares, 0, 3).setValue(SquareValue.ONE);
		
		final List<GameSquare> expectedResults1 = 
				Arrays.asList(new GameSquare(SquareValue.ONE, 0, 0), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 0),
						new GameSquare(SquareValue.FLAGGED, 0, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 1)
						);
		
		final List<GameSquare> expectedResults2 = 
				Arrays.asList(new GameSquare(SquareValue.FLAGGED, 0, 2), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 2),
						new GameSquare(SquareValue.ONE, 0, 3), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 3)
						);

		return createTestScenario(gameBoard, Arrays.asList(expectedResults1, expectedResults2));
	}
	
	/**
	 * @return A Game board containing 2 numbers separated by a single flag
	 */
	private static TestScenerio getGameBoardScenario9() {
		RegularGameBoard gameBoard = getValidGameBoard(4, 4);
		List<GameSquare> squares = gameBoard.getGameBoard();
		getGameSquare(squares, 0, 0).setValue(SquareValue.ONE);
		getGameSquare(squares, 0, 1).setValue(SquareValue.FLAGGED);
		getGameSquare(squares, 0, 2).setValue(SquareValue.ONE);
		
		final List<GameSquare> expectedResults = 
				Arrays.asList(new GameSquare(SquareValue.ONE, 0, 0), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 0),
						new GameSquare(SquareValue.FLAGGED, 0, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 1),
						new GameSquare(SquareValue.ONE, 0, 2), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 2),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 3), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 3)
						);
		
		return createTestScenario(gameBoard, Arrays.asList(expectedResults));
	}
	
	/**
	 * @return A Game board found here: https://math.stackexchange.com/questions/3447402/minesweeper-odds-for-this-scenario-2-different-calculations
	 * We've manually calculated the odds and such
	 */
	private static TestScenerio getGameBoardScenarioSpecial01() {
		RegularGameBoard gameBoard = getValidGameBoard(7, 6);
		List<GameSquare> squares = gameBoard.getGameBoard();
		getGameSquare(squares, 2, 2).setValue(SquareValue.THREE);
		getGameSquare(squares, 4, 2).setValue(SquareValue.ONE);
		getGameSquare(squares, 3, 3).setValue(SquareValue.ONE);	
		
		final List<GameSquare> expectedResults = 
				Arrays.asList(new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 1),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 5, 1),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 2), new GameSquare(SquareValue.THREE, 2, 2),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 2), new GameSquare(SquareValue.ONE, 4, 2),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 5, 2), 
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 3), new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 3), 
						new GameSquare(SquareValue.ONE, 3, 3), new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 3), 
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 5, 3),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 4), new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 4),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 4)
						);

		return createTestScenario(gameBoard, Arrays.asList(expectedResults));
	}
	
	/**
	 * @return A Game board containing a flag, and a full square of numbers
	 */
	private static TestScenerio getGameBoardScenarioSpecial02() {
		RegularGameBoard gameBoard = getValidGameBoard(9, 9);
		List<GameSquare> squares = gameBoard.getGameBoard();
		getGameSquare(squares, 2, 2).setValue(SquareValue.TWO);
		getGameSquare(squares, 3, 2).setValue(SquareValue.TWO);
		getGameSquare(squares, 4, 2).setValue(SquareValue.TWO);
		getGameSquare(squares, 5, 2).setValue(SquareValue.ONE);
		getGameSquare(squares, 6, 2).setValue(SquareValue.ONE);
		
		getGameSquare(squares, 2, 3).setValue(SquareValue.ONE);
		getGameSquare(squares, 6, 3).setValue(SquareValue.ONE);
		
		getGameSquare(squares, 2, 4).setValue(SquareValue.TWO);
		getGameSquare(squares, 3, 4).setValue(SquareValue.ONE);
		getGameSquare(squares, 5, 4).setValue(SquareValue.ONE);
		getGameSquare(squares, 6, 4).setValue(SquareValue.THREE);
		
		getGameSquare(squares, 2, 5).setValue(SquareValue.FLAGGED);
		getGameSquare(squares, 3, 5).setValue(SquareValue.ONE);
		getGameSquare(squares, 5, 5).setValue(SquareValue.ONE);
		getGameSquare(squares, 6, 5).setValue(SquareValue.FLAGGED);
		
		getGameSquare(squares, 2, 6).setValue(SquareValue.THREE);
		getGameSquare(squares, 3, 6).setValue(SquareValue.THREE);
		getGameSquare(squares, 4, 6).setValue(SquareValue.TWO);
		getGameSquare(squares, 5, 6).setValue(SquareValue.TWO);
		getGameSquare(squares, 6, 6).setValue(SquareValue.THREE);
		
		// TODO: This is unfinished. Not really needed anyway
		final List<GameSquare> expectedResults1 = 
				Arrays.asList(new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 1),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 5, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 6, 1), 
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 7, 1),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 2), new GameSquare(SquareValue.TWO, 2, 2),
						new GameSquare(SquareValue.TWO, 3, 2), new GameSquare(SquareValue.TWO, 4, 2),
						new GameSquare(SquareValue.ONE, 5, 2), new GameSquare(SquareValue.ONE, 6, 2), 
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 7, 2),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 3), new GameSquare(SquareValue.ONE, 2, 3),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 3), new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 3),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 5, 3), new GameSquare(SquareValue.ONE, 6, 3),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 7, 3),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 4), new GameSquare(SquareValue.TWO, 2, 4),
						new GameSquare(SquareValue.ONE, 3, 4), new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 4),
						new GameSquare(SquareValue.ONE, 5, 1), new GameSquare(SquareValue.THREE, 6, 4),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 7, 4),
						
						/*
						 * 0 1 2 3 4 5 6 7 8 9
						 * 5 # # & 1 # 1 & # #
						   6 # # 3 3 2 2 3 # #
						   7 # # # # # # # # #
						 */
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 2)
						);

		return createTestScenario(gameBoard, Arrays.asList(expectedResults1));
	}
	
	public static void main(String[] args) {
		visualizeGameBoard(getGameBoardScenarioSpecial02());
	}
	
	private static TestScenerio createTestScenario(RegularGameBoard gameBoard, List<List<GameSquare>> gameSquaresList) {
		List<Section> sections = new ArrayList<Section>();
		
		for (List<GameSquare> squares : gameSquaresList) {
			sections.add(new Section(new HashSet<GameSquare>(squares)));
		}
		
		return new TestScenerio(gameBoard, sections);
	}
}
