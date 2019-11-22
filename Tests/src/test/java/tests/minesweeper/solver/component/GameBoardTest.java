package tests.minesweeper.solver.component;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import minesweeper.solver.component.GameBoard;
import minesweeper.solver.component.GameSquare;

public class GameBoardTest {
	@Test
	public void testIsTouchingSameSpot() {
		GameSquare square1 = new GameSquare(null, 5, 5);
		GameSquare square2 = new GameSquare(null, 5, 5);
		
		assertFalse(GameBoard.isTouching(square1, square2));
		assertFalse(GameBoard.isTouching(square1, square1));
	}
	
	@Test
	public void testIsTouchingLeftRight() {
		GameSquare square1 = new GameSquare(null, 5, 5);
		GameSquare square2 = new GameSquare(null, 4, 5);
		
		assertTrue(GameBoard.isTouching(square1, square2));
		assertTrue(GameBoard.isTouching(square2, square1));
	}
	
	@Test
	public void testIsTouchingTopBottom() {
		GameSquare square1 = new GameSquare(null, 5, 6);
		GameSquare square2 = new GameSquare(null, 5, 5);
		
		assertTrue(GameBoard.isTouching(square1, square2));
		assertTrue(GameBoard.isTouching(square2, square1));
	}
	
	@Test
	public void testIsTouchingTopLeftCornerBottomRightCorner() {
		GameSquare square1 = new GameSquare(null, 5, 5);
		GameSquare square2 = new GameSquare(null, 4, 4);
		
		assertTrue(GameBoard.isTouching(square1, square2));
		assertTrue(GameBoard.isTouching(square2, square1));
	}
}
