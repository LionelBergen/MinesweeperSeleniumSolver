package tests.minesweeper.utility;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import component.model.gamesquare.GameSquare;
import component.model.gamesquare.SquareValue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static utility.util.GameBoardHelper.GameBoardHelper;

public class GameBoardHelperTest {
	@Test
	public void testGetSurroundingSquares() {
		List<GameSquare> gameSquares = new ArrayList<GameSquare>();
		
		// empty
		assertTrue(GameBoardHelper.getSurroundingBlankSquares(gameSquares, new GameSquare(SquareValue.BLANK_UNTOUCHED, 0, 0)).isEmpty());
		
		GameSquare gameSquare = new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 3);
		gameSquares.add(new GameSquare(SquareValue.ONE, 1, 2));
		gameSquares.add(new GameSquare(SquareValue.TWO, 2, 2));
		gameSquares.add(new GameSquare(SquareValue.FLAGGED, 3, 2));
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 6));
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 6));
		gameSquares.add(new GameSquare(SquareValue.THREE, 2, 3));
		gameSquares.add(gameSquare);
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 4));
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 4, 4));
		
		List<GameSquare> results = GameBoardHelper.getSurroundingBlankSquares(gameSquares, gameSquare);
		assertEquals(2, results.size());
		
		for (GameSquare result : results) {
			assertEquals(SquareValue.BLANK_UNTOUCHED, result.getValue());
		}
		
		// Ensure results doesn't contain self
		assertFalse(results.contains(gameSquare));
	}
	
	@Test
	public void testGetAllBlankSquares() {
		List<GameSquare> gameSquares = new ArrayList<GameSquare>();
		
		// empty
		assertTrue(GameBoardHelper.getAllBlankSquares(gameSquares).isEmpty());
		
		gameSquares.add(new GameSquare(SquareValue.ONE, 1, 2));
		gameSquares.add(new GameSquare(SquareValue.TWO, 2, 2));
		gameSquares.add(new GameSquare(SquareValue.FLAGGED, 3, 2));
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 5));
		gameSquares.add(new GameSquare(SquareValue.BLANK_UNTOUCHED, 3, 6));
		
		List<GameSquare> results = GameBoardHelper.getAllBlankSquares(gameSquares);
		assertEquals(2, results.size());
		
		for (GameSquare result : results) {
			assertEquals(SquareValue.BLANK_UNTOUCHED, result.getValue());
		}
	}
}
