package tests.minesweeper.solver.calculation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;
import org.junit.Test;

import component.model.GameSquare;
import component.model.RegularGameBoard;
import solver.board.analyzing.BoardAnalyzer;
import solver.component.Section;
import tests.minesweeper.data.TestDataHelper;
import tests.minesweeper.data.TestScenarios;
import tests.minesweeper.data.component.TestScenario;

public class BoardAnalyzerTest {
	// A board with no squares
	@Test
	public void testBreakupBoardEmpty() {
		RegularGameBoard emptyGameBoard = TestDataHelper.getValidGameBoard(0, 0, 0);
		
		List<Section> results = BoardAnalyzer.breakupBoard(emptyGameBoard);

		assertEquals(0, results.size());
	}
	
	// A board with all empty squares
	@Test
	public void testBreakupBoardNoResults() {
		RegularGameBoard emptyGameBoard = TestDataHelper.getValidGameBoard(0, 10, 10);
		
		List<Section> results = BoardAnalyzer.breakupBoard(emptyGameBoard);

		assertEquals(0, results.size());
	}
	
	@Test
	public void testBreakupBoard01() {
		TestScenario testScenerio = TestScenarios.SCENARIO_01;
		
		final RegularGameBoard gameBoard = testScenerio.getGameBoard();
		final List<Section> expectedResults = testScenerio.getExpectedSections();
		
		List<Section> actualResults = BoardAnalyzer.breakupBoard(gameBoard);

		assertSectionListsEqual(expectedResults, actualResults);
	}
	
	@Test
	public void testBreakupBoard02() {
		TestScenario testScenerio = TestScenarios.SCENARIO_02;
		
		RegularGameBoard gameBoard = testScenerio.getGameBoard();
		List<Section> expectedResults = testScenerio.getExpectedSections();
		
		List<Section> actualResults = BoardAnalyzer.breakupBoard(gameBoard);

		assertSectionListsEqual(expectedResults, actualResults);
	}
	
	@Test
	public void testBreakupBoard03() {
		TestScenario testScenerio = TestScenarios.SCENARIO_03;
		
		RegularGameBoard gameBoard = testScenerio.getGameBoard();
		List<Section> expectedResults = testScenerio.getExpectedSections();
		
		List<Section> actualResults = BoardAnalyzer.breakupBoard(gameBoard);

		assertSectionListsEqual(expectedResults, actualResults);
	}
	
	@Test
	public void testBreakupBoard04() {
		TestScenario testScenerio = TestScenarios.SCENARIO_04;
		
		RegularGameBoard gameBoard = testScenerio.getGameBoard();
		List<Section> expectedResults = testScenerio.getExpectedSections();
		
		List<Section> actualResults = BoardAnalyzer.breakupBoard(gameBoard);

		assertSectionListsEqual(expectedResults, actualResults);
	}
	
	@Test
	public void testBreakupBoard05() {
		TestScenario testScenerio = TestScenarios.SCENARIO_05;
		
		RegularGameBoard gameBoard = testScenerio.getGameBoard();
		List<Section> expectedResults = testScenerio.getExpectedSections();
		
		List<Section> actualResults = BoardAnalyzer.breakupBoard(gameBoard);

		assertSectionListsEqual(expectedResults, actualResults);
	}
	
	@Test
	public void testBreakupBoard06() {
		TestScenario testScenerio = TestScenarios.SCENARIO_06;
		
		RegularGameBoard gameBoard = testScenerio.getGameBoard();
		List<Section> expectedResults = testScenerio.getExpectedSections();
		
		List<Section> actualResults = BoardAnalyzer.breakupBoard(gameBoard);

		assertSectionListsEqual(expectedResults, actualResults);
	}
	
	@Test
	public void testBreakupBoard07() {
		TestScenario testScenerio = TestScenarios.SCENARIO_07;
		
		RegularGameBoard gameBoard = testScenerio.getGameBoard();
		List<Section> expectedResults = testScenerio.getExpectedSections();
		
		List<Section> actualResults = BoardAnalyzer.breakupBoard(gameBoard);

		assertSectionListsEqual(expectedResults, actualResults);
	}
	
	@Test
	public void testBreakupBoard08() {
		TestScenario testScenerio = TestScenarios.SCENARIO_08;
		
		RegularGameBoard gameBoard = testScenerio.getGameBoard();
		List<Section> expectedResults = testScenerio.getExpectedSections();
		
		List<Section> actualResults = BoardAnalyzer.breakupBoard(gameBoard);

		assertSectionListsEqual(expectedResults, actualResults);
	}
	
	@Test
	public void testBreakupBoard09() {
		TestScenario testScenerio = TestScenarios.SCENARIO_09;
		
		RegularGameBoard gameBoard = testScenerio.getGameBoard();
		List<Section> expectedResults = testScenerio.getExpectedSections();
		
		List<Section> actualResults = BoardAnalyzer.breakupBoard(gameBoard);

		assertSectionListsEqual(expectedResults, actualResults);
	}
	
	@Test
	public void testBreakupBoardSpecial01() {
		TestScenario testScenerio = TestScenarios.SCENARIO_SPECIAL_01;
		
		RegularGameBoard gameBoard = testScenerio.getGameBoard();
		List<Section> expectedResults = testScenerio.getExpectedSections();
		
		List<Section> actualResults = BoardAnalyzer.breakupBoard(gameBoard);

		assertSectionListsEqual(expectedResults, actualResults);
	}
	
	@Test
	public void testBreakupBoardSpecial02() {
		TestScenario testScenerio = TestScenarios.SCENARIO_SPECIAL_02;
		RegularGameBoard gameBoard = testScenerio.getGameBoard();
		
		List<Section> actualResults = BoardAnalyzer.breakupBoard(gameBoard);
		assertEquals(1, actualResults.size());
		assertEquals(49, actualResults.get(0).getGameSquares().size());
	}
	
	/**
	 * Asserts that all of the sections are equal. 
	 * Order does not matter
	 */
	private void assertSectionListsEqual(Collection<Section> expectedList, Collection<Section> actualList) {
		assertEquals(expectedList.size(), actualList.size());
		
		for (Section expectedSection : expectedList) {
			GameSquare squareFromList = expectedSection.getGameSquares().iterator().next();
			
			// get matching in actual results
			Section actualSection = actualList.stream().filter(e -> e.getGameSquares().contains(squareFromList)).findAny().get();
			
			assertListsEqual(expectedSection.getGameSquares(), actualSection.getGameSquares());
		}
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
