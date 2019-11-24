package tests.minesweeper.solver.calculation;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import component.model.RegularGameBoard;
import solver.calculation.BoardAnalyzer;
import solver.component.ResultSet;
import tests.minesweeper.data.TestScenerio;

public class BoardAnalyzerTest {
	@Test
	public void testBreakupBoard01() {
		RegularGameBoard gameBoard = TestScenerio.getGameBoardScenerio1();
		
		List<ResultSet> results = BoardAnalyzer.breakupBoard(gameBoard);
		
		assertEquals(1, results.size());
		
		ResultSet result = results.get(0);
		assertEquals(9, result.getGameSquares().size());
	}
}
