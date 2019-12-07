package tests.minesweeper.data;

import static tests.minesweeper.data.TestDataHelper.getValidGameBoard;
import static tests.minesweeper.data.TestDataHelper.getGameSquare;
import static tests.minesweeper.data.TestDataHelper.visualizeGameBoard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import component.model.RegularGameBoard;
import component.model.Section;
import component.model.gamesquare.GameSquare;
import component.model.gamesquare.SquareValue;
import tests.minesweeper.data.component.GameBoardTestScenario;

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
	public static final GameBoardTestScenario SCENARIO_SPECIAL_02 = getGameBoardScenarioSpecial02();
	public static final GameBoardTestScenario SCENARIO_SPECIAL_03 = getGameBoardScenarioSpecial03();
	
	/**
	 * @return A Game board containing a 1 in the center
	 */
	private static GameBoardTestScenario getGameBoardScenario1() {
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
	private static GameBoardTestScenario getGameBoardScenario2() {
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
	private static GameBoardTestScenario getGameBoardScenario3() {
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
	private static GameBoardTestScenario getGameBoardScenario4() {
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
	private static GameBoardTestScenario getGameBoardScenario5() {
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
	private static GameBoardTestScenario getGameBoardScenario6() {
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
	private static GameBoardTestScenario getGameBoardScenario7() {
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
	private static GameBoardTestScenario getGameBoardScenario8() {
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
	private static GameBoardTestScenario getGameBoardScenario9() {
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
	private static GameBoardTestScenario getGameBoardScenarioSpecial01() {
		RegularGameBoard gameBoard = getValidGameBoard(7, 6);
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
	
	/**
	 * Described here: https://math.stackexchange.com/questions/3466402/calculating-minesweeper-odds-is-this-calculation-correct
	 */
	private static GameBoardTestScenario getGameBoardScenarioSpecial03() {
		RegularGameBoard gameBoard = getValidGameBoard(16, 8);
		List<GameSquare> squares = gameBoard.getGameBoard();
		getGameSquare(squares, 4, 3).setValue(SquareValue.BLANK_UNTOUCHED).setName("A");
		getGameSquare(squares, 5, 3).setValue(SquareValue.BLANK_UNTOUCHED).setName("B");
		getGameSquare(squares, 6, 3).setValue(SquareValue.BLANK_UNTOUCHED).setName("C");
		getGameSquare(squares, 7, 3).setValue(SquareValue.BLANK_UNTOUCHED).setName("D");
		getGameSquare(squares, 8, 3).setValue(SquareValue.BLANK_UNTOUCHED).setName("E");
		getGameSquare(squares, 4, 4).setValue(SquareValue.BLANK_UNTOUCHED).setName("F");
		getGameSquare(squares, 6, 4).setValue(SquareValue.BLANK_UNTOUCHED).setName("G");
		getGameSquare(squares, 8, 4).setValue(SquareValue.BLANK_UNTOUCHED).setName("H");
		getGameSquare(squares, 4, 5).setValue(SquareValue.BLANK_UNTOUCHED).setName("I");
		getGameSquare(squares, 5, 5).setValue(SquareValue.BLANK_UNTOUCHED).setName("J");
		getGameSquare(squares, 7, 5).setValue(SquareValue.BLANK_UNTOUCHED).setName("K");
		getGameSquare(squares, 8, 5).setValue(SquareValue.BLANK_UNTOUCHED).setName("L");
		getGameSquare(squares, 5, 6).setValue(SquareValue.BLANK_UNTOUCHED).setName("M");
		getGameSquare(squares, 6, 6).setValue(SquareValue.BLANK_UNTOUCHED).setName("N");
		getGameSquare(squares, 7, 6).setValue(SquareValue.BLANK_UNTOUCHED).setName("O");
		
		getGameSquare(squares, 11, 2).setValue(SquareValue.BLANK_UNTOUCHED).setName("P");
		getGameSquare(squares, 12, 2).setValue(SquareValue.BLANK_UNTOUCHED).setName("Q");
		getGameSquare(squares, 13, 2).setValue(SquareValue.BLANK_UNTOUCHED).setName("R");
		getGameSquare(squares, 14, 2).setValue(SquareValue.BLANK_UNTOUCHED).setName("S");
		getGameSquare(squares, 11, 3).setValue(SquareValue.BLANK_UNTOUCHED).setName("T");
		getGameSquare(squares, 14, 3).setValue(SquareValue.BLANK_UNTOUCHED).setName("U");
		getGameSquare(squares, 11, 4).setValue(SquareValue.BLANK_UNTOUCHED).setName("V");
		getGameSquare(squares, 12, 4).setValue(SquareValue.BLANK_UNTOUCHED).setName("W");
		getGameSquare(squares, 13, 4).setValue(SquareValue.BLANK_UNTOUCHED).setName("X");
		getGameSquare(squares, 14, 4).setValue(SquareValue.BLANK_UNTOUCHED).setName("Y");
		
		getGameSquare(squares, 5, 4).setValue(SquareValue.THREE).setName("3");
		getGameSquare(squares, 7, 4).setValue(SquareValue.ONE).setName("1");
		getGameSquare(squares, 6, 5).setValue(SquareValue.ONE).setName("1");
		
		getGameSquare(squares, 12, 3).setValue(SquareValue.TWO).setName("2");
		getGameSquare(squares, 13, 3).setValue(SquareValue.ONE).setName("1");
		
		final List<GameSquare> expectedResults1 = 
				Arrays.asList(new GameSquare("A", SquareValue.BLANK_UNTOUCHED, 4, 3), new GameSquare("B", SquareValue.BLANK_UNTOUCHED, 5, 3),
						new GameSquare("C", SquareValue.BLANK_UNTOUCHED, 6, 3), new GameSquare("D", SquareValue.BLANK_UNTOUCHED, 7, 3),
						new GameSquare("E", SquareValue.BLANK_UNTOUCHED, 8, 3),
						new GameSquare("F", SquareValue.BLANK_UNTOUCHED, 4, 4), new GameSquare("3", SquareValue.THREE, 5, 4),
						new GameSquare("G", SquareValue.BLANK_UNTOUCHED, 6, 4), new GameSquare("1", SquareValue.ONE, 7, 4),
						new GameSquare("H", SquareValue.BLANK_UNTOUCHED, 8, 4), 
						new GameSquare("I", SquareValue.BLANK_UNTOUCHED, 4, 5), new GameSquare("J", SquareValue.BLANK_UNTOUCHED, 5, 5), 
						new GameSquare("1", SquareValue.ONE, 6, 5), new GameSquare("K", SquareValue.BLANK_UNTOUCHED, 7, 5), 
						new GameSquare("L", SquareValue.BLANK_UNTOUCHED, 8, 5),
						new GameSquare("M", SquareValue.BLANK_UNTOUCHED, 5, 6),
						new GameSquare("N", SquareValue.BLANK_UNTOUCHED, 6, 6), new GameSquare("O", SquareValue.BLANK_UNTOUCHED, 7, 6)
						);
		
		final List<GameSquare> expectedResults2 = 
				Arrays.asList(new GameSquare("P", SquareValue.BLANK_UNTOUCHED, 11, 2), new GameSquare("Q", SquareValue.BLANK_UNTOUCHED, 12, 2),
						new GameSquare("R", SquareValue.BLANK_UNTOUCHED, 13, 2), new GameSquare("S", SquareValue.BLANK_UNTOUCHED, 14, 2),
						new GameSquare("T", SquareValue.BLANK_UNTOUCHED, 11, 3),
						new GameSquare("U", SquareValue.BLANK_UNTOUCHED, 14, 3), new GameSquare("V", SquareValue.BLANK_UNTOUCHED, 11, 4),
						new GameSquare("W", SquareValue.BLANK_UNTOUCHED, 12, 4), new GameSquare("X", SquareValue.BLANK_UNTOUCHED, 13, 4),
						new GameSquare("Y", SquareValue.BLANK_UNTOUCHED, 14, 4), 
						new GameSquare("2", SquareValue.TWO, 12, 3), new GameSquare("1", SquareValue.ONE, 13, 3)
						);

		return createTestScenario(gameBoard, Arrays.asList(expectedResults1, expectedResults2));
	}
	
	/**
	 * @return A Game board containing a flag, and a full square of numbers
	 */
	private static GameBoardTestScenario getGameBoardScenarioSpecial02() {
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
		visualizeGameBoard(SCENARIO_SPECIAL_03);
	}
	
	private static GameBoardTestScenario createTestScenario(RegularGameBoard gameBoard, List<List<GameSquare>> gameSquaresList) {
		List<Section> sections = new ArrayList<Section>();
		
		for (List<GameSquare> squares : gameSquaresList) {
			sections.add(new Section(new HashSet<GameSquare>(squares)));
		}
		
		return new GameBoardTestScenario(gameBoard, sections);
	}
}
