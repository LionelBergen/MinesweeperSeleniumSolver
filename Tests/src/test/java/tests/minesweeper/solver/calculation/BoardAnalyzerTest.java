package tests.minesweeper.solver.calculation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import component.model.GenericSection;
import component.model.RegularGameBoard;
import component.model.Section;
import component.model.gamesquare.GameSquare;
import solver.board.analyzing.BoardAnalyzer;
import tests.minesweeper.data.TestDataHelper;
import tests.minesweeper.data.GameBoardTestScenarios;
import tests.minesweeper.data.component.GameBoardTestScenario;
import tests.minesweeper.solver.data.GameBoardTestData;

public class BoardAnalyzerTest {
	// A board with no squares
	@Test
	public void testBreakupBoardEmpty() {
		RegularGameBoard emptyGameBoard = TestDataHelper.getValidGameBoard(0, 0, 0, 0);
		
		List<? extends GenericSection<? extends GameSquare>> results = BoardAnalyzer.breakupBoard(emptyGameBoard);

		assertEquals(0, results.size());
	}
	
	// A board with all empty squares
	@Test
	public void testBreakupBoardNoResults() {
		RegularGameBoard emptyGameBoard = TestDataHelper.getValidGameBoard(0, 10, 10, 0);
		
		List<? extends GenericSection<? extends GameSquare>> results = BoardAnalyzer.breakupBoard(emptyGameBoard);

		assertEquals(0, results.size());
	}
	
	@Test
	public void testBreakupBoard01() {
		GameBoardTestScenario testScenerio = GameBoardTestScenarios.SCENARIO_01;
		
		final RegularGameBoard gameBoard = testScenerio.getGameBoard();
		final List<Section> expectedResults = testScenerio.getExpectedSections();
		
		List<? extends GenericSection<? extends GameSquare>> actualResults = BoardAnalyzer.breakupBoard(gameBoard);

		assertSectionListsEqual(expectedResults, actualResults);
	}
	
	@Test
	public void testBreakupBoard02() {
		GameBoardTestScenario testScenerio = GameBoardTestScenarios.SCENARIO_02;
		
		RegularGameBoard gameBoard = testScenerio.getGameBoard();
		List<Section> expectedResults = testScenerio.getExpectedSections();
		
		List<? extends GenericSection<? extends GameSquare>> actualResults = BoardAnalyzer.breakupBoard(gameBoard);

		assertSectionListsEqual(expectedResults, actualResults);
	}
	
	@Test
	public void testBreakupBoard03() {
		GameBoardTestScenario testScenerio = GameBoardTestScenarios.SCENARIO_03;
		
		RegularGameBoard gameBoard = testScenerio.getGameBoard();
		List<Section> expectedResults = testScenerio.getExpectedSections();
		
		List<? extends GenericSection<? extends GameSquare>> actualResults = BoardAnalyzer.breakupBoard(gameBoard);

		assertSectionListsEqual(expectedResults, actualResults);
	}
	
	@Test
	public void testBreakupBoard04() {
		GameBoardTestScenario testScenerio = GameBoardTestScenarios.SCENARIO_04;
		
		RegularGameBoard gameBoard = testScenerio.getGameBoard();
		List<Section> expectedResults = testScenerio.getExpectedSections();
		
		List<? extends GenericSection<? extends GameSquare>> actualResults = BoardAnalyzer.breakupBoard(gameBoard);

		assertSectionListsEqual(expectedResults, actualResults);
	}
	
	@Test
	public void testBreakupBoard05() {
		GameBoardTestScenario testScenerio = GameBoardTestScenarios.SCENARIO_05;
		
		RegularGameBoard gameBoard = testScenerio.getGameBoard();
		List<Section> expectedResults = testScenerio.getExpectedSections();
		
		List<? extends GenericSection<? extends GameSquare>> actualResults = BoardAnalyzer.breakupBoard(gameBoard);

		assertSectionListsEqual(expectedResults, actualResults);
	}
	
	@Test
	public void testBreakupBoard06() {
		GameBoardTestScenario testScenerio = GameBoardTestScenarios.SCENARIO_06;
		
		RegularGameBoard gameBoard = testScenerio.getGameBoard();
		List<Section> expectedResults = testScenerio.getExpectedSections();
		
		List<? extends GenericSection<? extends GameSquare>> actualResults = BoardAnalyzer.breakupBoard(gameBoard);

		assertSectionListsEqual(expectedResults, actualResults);
	}
	
	@Test
	public void testBreakupBoard07() {
		GameBoardTestScenario testScenerio = GameBoardTestScenarios.SCENARIO_07;
		
		RegularGameBoard gameBoard = testScenerio.getGameBoard();
		List<Section> expectedResults = testScenerio.getExpectedSections();
		
		List<? extends GenericSection<? extends GameSquare>> actualResults = BoardAnalyzer.breakupBoard(gameBoard);

		assertSectionListsEqual(expectedResults, actualResults);
	}
	
	@Test
	public void testBreakupBoard08() {
		GameBoardTestScenario testScenerio = GameBoardTestScenarios.SCENARIO_08;
		
		RegularGameBoard gameBoard = testScenerio.getGameBoard();
		List<Section> expectedResults = testScenerio.getExpectedSections();
		
		List<? extends GenericSection<? extends GameSquare>> actualResults = BoardAnalyzer.breakupBoard(gameBoard);

		assertSectionListsEqual(expectedResults, actualResults);
	}
	
	@Test
	public void testBreakupBoard09() {
		GameBoardTestScenario testScenerio = GameBoardTestScenarios.SCENARIO_09;
		
		RegularGameBoard gameBoard = testScenerio.getGameBoard();
		List<Section> expectedResults = testScenerio.getExpectedSections();
		
		List<? extends GenericSection<? extends GameSquare>> actualResults = BoardAnalyzer.breakupBoard(gameBoard);

		assertSectionListsEqual(expectedResults, actualResults);
	}
	
	@Test
	public void testBreakupBoardSpecial01() {
		GameBoardTestScenario testScenerio = GameBoardTestScenarios.SCENARIO_SPECIAL_01;
		
		RegularGameBoard gameBoard = testScenerio.getGameBoard();
		List<Section> expectedResults = testScenerio.getExpectedSections();
		
		List<? extends GenericSection<? extends GameSquare>> actualResults = BoardAnalyzer.breakupBoard(gameBoard);

		assertSectionListsEqual(expectedResults, actualResults);
	}
	
	@Test
	public void testBreakupBoardSpecial03() {
		GameBoardTestScenario testScenerio = GameBoardTestData.SPECIAL_SCENARIO_03;
		
		RegularGameBoard gameBoard = testScenerio.getGameBoard();
		List<Section> results = BoardAnalyzer.breakupBoard(gameBoard);

		assertEquals(2, results.size());
	}
	
	@Test
	public void testBreakupBoardSpecial02() {
		GameBoardTestScenario testScenerio = GameBoardTestData.SPECIAL_SCENARIO_02;
		RegularGameBoard gameBoard = testScenerio.getGameBoard();
		
		List<? extends GenericSection<? extends GameSquare>> actualResults = BoardAnalyzer.breakupBoard(gameBoard);
		assertEquals(1, actualResults.size());
		assertEquals(49, actualResults.get(0).getGameSquares().size());
	}
	
	/**
	 * Asserts that all of the sections are equal. 
	 * Order does not matter
	 */
	private void assertSectionListsEqual(Collection<Section> expectedList, List<? extends GenericSection<? extends GameSquare>> actualResults) {
		assertEquals(expectedList.size(), actualResults.size());
		
		for (Section expectedSection : expectedList) {
			GameSquare squareFromList = expectedSection.getGameSquares().iterator().next();
			
			// get matching in actual results
			GenericSection<? extends GameSquare> actualSection = actualResults.stream().filter(e -> e.getGameSquares().contains(squareFromList)).findAny().orElse(null);
			
			if (actualSection == null) {
				fail("Did not find expected square: " + squareFromList);
			}
			
			assertListsEqual(expectedSection.getGameSquares(), actualSection.getGameSquares());
		}
	}
	
	/**
	 * Asserts list 1 contains all values in list2. 
	 * Order does not matter
	 */
	private void assertListsEqual(Collection<GameSquare> expectedList, Set<? extends GameSquare> set) {
		assertEquals(expectedList.size(), set.size());
		
		for (GameSquare expectedSquare : expectedList) {
			assertTrue("Results did not contain expected: " + expectedSquare, set.contains(expectedSquare));
		}
	}
}
