package utility.util;

import component.model.GameSquare;

public final class Utility {
	private Utility() { }
	
	/**
	 *  Returns true if the 2 squares are 'touching' according to MineSweeper rules (corners, left, right, up, down)
	 *  Will return false if the two squares are in the same location (AKA the same square)
	 */
	public static boolean isTouching(GameSquare square1, GameSquare square2) {
		// right
		boolean isTouching = square2.getX() + 1 == square1.getX() && square2.getY() == square1.getY();
		// right top corner
		isTouching |= square2.getX() + 1 == square1.getX() && square2.getY() - 1 == square1.getY();
		// right bottom corner
		isTouching |= square2.getX() + 1 == square1.getX() && square2.getY() + 1 == square1.getY();
		
		// left
		isTouching |= square2.getX() - 1 == square1.getX() && square2.getY() == square1.getY();
		// left top corner
		isTouching |= square2.getX() - 1 == square1.getX() && square2.getY() - 1 == square1.getY();
		// left bottom corner
		isTouching |= square2.getX() - 1 == square1.getX() && square2.getY() + 1 == square1.getY();
		
		// top
		isTouching |= square2.getX() == square1.getX() && square2.getY() + 1 == square1.getY();
		
		// bottom
		isTouching |= square2.getX() == square1.getX() && square2.getY() - 1 == square1.getY();
		
		return isTouching;
	}
}
