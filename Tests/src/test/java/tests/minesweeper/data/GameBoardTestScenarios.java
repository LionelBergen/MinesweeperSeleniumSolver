package tests.minesweeper.data;

import static tests.minesweeper.data.TestDataHelper.getValidGameBoard;
import static tests.minesweeper.data.TestDataHelper.getGameSquare;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import component.model.RegularGameBoard;
import component.model.Section;
import component.model.gamesquare.GameSquare;
import component.model.gamesquare.SquareValue;
import tests.minesweeper.data.component.GameBoardTestScenario;
import tests.minesweeper.solver.data.GameBoardTestData;

public class GameBoardTestScenarios {
	public static final GameBoardTestScenario SCENARIO_01 = getGameBoardScenario1();
	public static final GameBoardTestScenario SCENARIO_02 = getGameBoardScenario2();
	public static final GameBoardTestScenario SCENARIO_03 = getGameBoardScenario3();
	public static final GameBoardTestScenario SCENARIO_04 = getGameBoardScenario4();
	public static final GameBoardTestScenario SCENARIO_05 = getGameBoardScenario5();
	public static final GameBoardTestScenario SCENARIO_06 = getGameBoardScenario6();
	public static final GameBoardTestScenario SCENARIO_07 = getGameBoardScenario7();
	public static final GameBoardTestScenario SCENARIO_08 = getGameBoardScenario8();
	public static final GameBoardTestScenario SCENARIO_09 = getGameBoardScenario9();
	
	public static final GameBoardTestScenario SCENARIO_SPECIAL_01 = getGameBoardScenarioSpecial01();
	
	public static void main(String[] args) {
		TestDataHelper.visualizeGameBoard(GameBoardTestData.SPECIAL_SCENARIO_02);
	}
	
	/**
	 * @return A Game board containing a 1 in the center
	 */
	private static GameBoardTestScenario getGameBoardScenario1() {
		// setup a game board
		RegularGameBoard gameBoard = getValidGameBoard(5, 5, 1);
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
	private static GameBoardTestScenario getGameBoardScenario2() {
		// setup the game board
		RegularGameBoard gameBoard = getValidGameBoard(7, 7, 2);
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
	private static GameBoardTestScenario getGameBoardScenario3() {
		// create game board
		RegularGameBoard gameBoard = getValidGameBoard(7, 7, 2);
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
	private static GameBoardTestScenario getGameBoardScenario4() {
		RegularGameBoard gameBoard = getValidGameBoard(5, 5, 2);
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
	private static GameBoardTestScenario getGameBoardScenario5() {
		RegularGameBoard gameBoard = getValidGameBoard(4, 4, 2);
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
	private static GameBoardTestScenario getGameBoardScenario6() {
		RegularGameBoard gameBoard = getValidGameBoard(4, 4, 2);
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
	private static GameBoardTestScenario getGameBoardScenario7() {
		RegularGameBoard gameBoard = getValidGameBoard(4, 4, 2);
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
	private static GameBoardTestScenario getGameBoardScenario8() {
		RegularGameBoard gameBoard = getValidGameBoard(4, 4, 2);
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
	private static GameBoardTestScenario getGameBoardScenario9() {
		RegularGameBoard gameBoard = getValidGameBoard(4, 4, 2);
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
	private static GameBoardTestScenario getGameBoardScenarioSpecial01() {
		RegularGameBoard gameBoard = getValidGameBoard(7, 6, 5);
		List<GameSquare> squares = gameBoard.getGameBoard();
		getGameSquare(squares, 2, 2).setValue(SquareValue.THREE).setName("3");
		getGameSquare(squares, 4, 2).setValue(SquareValue.ONE).setName("1");
		getGameSquare(squares, 3, 3).setValue(SquareValue.ONE).setName("1");
		
		final List<GameSquare> expectedResults = 
				Arrays.asList(new GameSquare("A", SquareValue.BLANK_UNTOUCHED, 1, 1), new GameSquare("B", SquareValue.BLANK_UNTOUCHED, 2, 1),
						new GameSquare("C", SquareValue.BLANK_UNTOUCHED, 3, 1), new GameSquare("D", SquareValue.BLANK_UNTOUCHED, 4, 1),
						new GameSquare("E", SquareValue.BLANK_UNTOUCHED, 5, 1),
						new GameSquare("F", SquareValue.BLANK_UNTOUCHED, 1, 2), new GameSquare("3", SquareValue.THREE, 2, 2),
						new GameSquare("G", SquareValue.BLANK_UNTOUCHED, 3, 2), new GameSquare("1", SquareValue.ONE, 4, 2),
						new GameSquare("H", SquareValue.BLANK_UNTOUCHED, 5, 2), 
						new GameSquare("I", SquareValue.BLANK_UNTOUCHED, 1, 3), new GameSquare("J", SquareValue.BLANK_UNTOUCHED, 2, 3), 
						new GameSquare("1", SquareValue.ONE, 3, 3), new GameSquare("K", SquareValue.BLANK_UNTOUCHED, 4, 3), 
						new GameSquare("L", SquareValue.BLANK_UNTOUCHED, 5, 3),
						new GameSquare("N", SquareValue.BLANK_UNTOUCHED, 2, 4), new GameSquare("O", SquareValue.BLANK_UNTOUCHED, 3, 4),
						new GameSquare("P", SquareValue.BLANK_UNTOUCHED, 4, 4)
						);

		return createTestScenario(gameBoard, Arrays.asList(expectedResults));
	}
	
	private static GameBoardTestScenario createTestScenario(RegularGameBoard gameBoard, List<List<GameSquare>> gameSquaresList) {
		List<Section> sections = new ArrayList<Section>();
		
		for (List<GameSquare> squares : gameSquaresList) {
			sections.add(new Section(new HashSet<GameSquare>(squares)));
		}
		
		return new GameBoardTestScenario(gameBoard, sections);
	}
}
