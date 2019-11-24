package tests.minesweeper.component;

import static org.junit.Assert.assertEquals;
import static tests.minesweeper.data.TestData.getValidGameBoard;

import java.util.List;

import org.junit.Test;

import component.model.GameSquare;
import component.model.RegularGameBoard;
import component.model.SquareValue;

public class GameBoardTest {
	@Test
	public void testGetSize() {
		RegularGameBoard testGameBoard = getValidGameBoard(21);
		
		assertEquals(100, testGameBoard.getSize());
	}
	
	@Test
	public void testGetAllBlankSquares() {
		RegularGameBoard testGameBoard = getValidGameBoard(13);
		
		List<GameSquare> results = testGameBoard.getAllBlankSquares();
		assertEquals(87, results.size());
		
		for (GameSquare square : results) {
			assertEquals(SquareValue.BLANK_UNTOUCHED, square.getValue());
		}
	}
}
