package tests.minesweeper.solver.calculation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.Test;

import component.model.GameSquare;
import component.model.RegularGameBoard;
import component.model.SquareValue;
import solver.calculation.BoardAnalyzer;
import solver.component.Section;
import tests.minesweeper.data.TestScenerio;

public class BoardAnalyzerTest {
	@Test
	public void testBreakupBoard01() {
		RegularGameBoard gameBoard = TestScenerio.getGameBoardScenerio1();
		
		// Based on test scenerio1, this is what we expect as our results
		final List<GameSquare> expectedResults1 = 
				Arrays.asList(new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 2),
						new GameSquare(SquareValue.ONE, 2, 2), new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 2), 
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 3), new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 3),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 3)
						);
		
		List<Section> results = BoardAnalyzer.breakupBoard(gameBoard);

		assertEquals(1, results.size());
		
		Section result = results.get(0);
		assertListsEqual(expectedResults1, result.getGameSquares());
	}
	
	@Test
	public void testBreakupBoard02() {
		RegularGameBoard gameBoard = TestScenerio.getGameBoardScenerio2();
		
		// Based on test scenerio2, this is what we expect as our results
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
		
		List<Section> results = BoardAnalyzer.breakupBoard(gameBoard);

		assertEquals(2, results.size());
		
		Section result1 = results.stream().filter(e -> e.getGameSquares().contains(expectedResults1.get(0))).findAny().get();
		assertListsEqual(expectedResults1, result1.getGameSquares());
		
		Section result2 = results.stream().filter(e -> e.getGameSquares().contains(expectedResults2.get(1))).findAny().get();
		assertListsEqual(expectedResults2, result2.getGameSquares());
	}
	
	@Test
	public void testBreakupBoard03() {
		RegularGameBoard gameBoard = TestScenerio.getGameBoardScenerio3();
		
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
		
		List<Section> results = BoardAnalyzer.breakupBoard(gameBoard);

		assertEquals(2, results.size());
		
		Section result1 = results.stream().filter(e -> e.getGameSquares().contains(expectedResults1.get(0))).findAny().get();
		assertListsEqual(expectedResults1, result1.getGameSquares());
		
		Section result2 = results.stream().filter(e -> e.getGameSquares().contains(expectedResults2.get(1))).findAny().get();
		assertListsEqual(expectedResults2, result2.getGameSquares());
	}
	
	@Test
	public void testBreakupBoard04() {
		RegularGameBoard gameBoard = TestScenerio.getGameBoardScenerio4();
		
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
		
		List<Section> results = BoardAnalyzer.breakupBoard(gameBoard);

		assertEquals(2, results.size());
		
		Section result1 = results.stream().filter(e -> e.getGameSquares().contains(expectedResults1.get(0))).findAny().get();
		assertListsEqual(expectedResults1, result1.getGameSquares());
		
		Section result2 = results.stream().filter(e -> e.getGameSquares().contains(expectedResults2.get(1))).findAny().get();
		assertListsEqual(expectedResults2, result2.getGameSquares());
	}
	
	@Test
	public void testBreakupBoard05() {
		RegularGameBoard gameBoard = TestScenerio.getGameBoardScenerio5();
		
		// Based on test scenerio5, this is what we expect as our results
		final List<GameSquare> expectedResults1 = 
				Arrays.asList(new GameSquare(SquareValue.ONE, 0, 0), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 0),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 1),
						new GameSquare(SquareValue.ONE, 0, 2), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 2),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 3), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 3)
						);
		
		List<Section> results = BoardAnalyzer.breakupBoard(gameBoard);

		assertEquals(1, results.size());
		
		Section result1 = results.stream().filter(e -> e.getGameSquares().contains(expectedResults1.get(0))).findAny().get();
		assertListsEqual(expectedResults1, result1.getGameSquares());
	}
	
	@Test
	public void testBreakupBoard06() {
		RegularGameBoard gameBoard = TestScenerio.getGameBoardScenerio6();
		
		// Based on test scenerio6, this is what we expect as our results
		final List<GameSquare> expectedResults1 = 
				Arrays.asList(new GameSquare(SquareValue.ONE, 0, 0), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 0),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 1),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 1),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 2), new GameSquare(SquareValue.ONE, 2, 2),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 2), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 3),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 2, 3), new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 3)
						);
		
		List<Section> results = BoardAnalyzer.breakupBoard(gameBoard);

		assertEquals(1, results.size());
		
		Section result1 = results.stream().filter(e -> e.getGameSquares().contains(expectedResults1.get(0))).findAny().get();
		assertListsEqual(expectedResults1, result1.getGameSquares());
	}
	
	@Test
	public void testBreakupBoard07() {
		RegularGameBoard gameBoard = TestScenerio.getGameBoardScenerio7();
		
		// Based on test scenerio7, this is what we expect as our results
		final List<GameSquare> expectedResults1 = 
				Arrays.asList(new GameSquare(SquareValue.ONE, 0, 0), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 0),
						new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 1), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 1)
						);
		
		final List<GameSquare> expectedResults2 = 
				Arrays.asList(new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 2), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 2),
						new GameSquare(SquareValue.ONE, 0, 3), new GameSquare(SquareValue.BLANK_UNTOUCHED, 1, 3)
						);
		
		List<Section> results = BoardAnalyzer.breakupBoard(gameBoard);

		assertEquals(2, results.size());
		
		Section result1 = results.stream().filter(e -> e.getGameSquares().contains(expectedResults1.get(0))).findAny().get();
		assertListsEqual(expectedResults1, result1.getGameSquares());
		
		Section result2 = results.stream().filter(e -> e.getGameSquares().contains(expectedResults2.get(0))).findAny().get();
		assertListsEqual(expectedResults2, result2.getGameSquares());
	}
	
	@Test
	public void testBreakupBoardSpecial01() {
		RegularGameBoard gameBoard = TestScenerio.getGameBoardScenerioSpecial01();
		
		final List<GameSquare> expectedResults1 = 
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
		
		List<Section> results = BoardAnalyzer.breakupBoard(gameBoard);

		assertEquals(1, results.size());
		
		Section result1 = results.stream().filter(e -> e.getGameSquares().contains(expectedResults1.get(0))).findAny().get();
		assertListsEqual(expectedResults1, result1.getGameSquares());
	}
	
	/**
	 * Asserts list 1 contains all values in list2. 
	 * Order does not matter
	 */
	private void assertListsEqual(Collection<GameSquare> expectedList, Collection<GameSquare> actualList) {
		assertEquals(expectedList.size(), actualList.size());
		
		for (GameSquare expectedSquare : expectedList) {
			assertTrue("Results did not contain expected: " + expectedSquare, actualList.contains(expectedSquare));
		}
	}
}
