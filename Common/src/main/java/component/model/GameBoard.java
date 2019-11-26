package component.model;

import static utility.util.MathUtil.getRandomNumber;
import java.util.List;
import utility.util.GameBoardHelper;

/**
 * Contains {@link GameSquare}'s representing a Minesweeper board.
 *  
 * @author Lionel Bergen
 */
public abstract class GameBoard<T extends GameSquare> {
	private final GameBoardHelper<T> gameBoardHelper = new GameBoardHelper<T>();
	private List<T> gameBoard = null;
	
	public void setGameBoard(List<T> gameSquares) {
		this.gameBoard = gameSquares;
	}
	
	public List<T> getGameBoard() {
		return this.gameBoard;
	}
	
	public int getSize() {
		return this.gameBoard.size();
	}
	
	public boolean isGameWon() {
		return this.getAllBlankSquares().size() == 0;
	}
	
	/**
	 * Get a square that is surrounded by 8 blank squares
	 * 
	 * @return A square that is surrounded by 8 blank squares
	 */
	public T getRandomLonelySquare() {
		return getAllBlankSquares().stream().filter(e -> getSurroundingBlankSquares(e).size() == 8).findFirst().orElse(null);
	}

	public T getRandomOpenElement() {
		List<T> blankSquares = getAllBlankSquares();
		
		return blankSquares.get(getRandomNumber(0, blankSquares.size() - 1));
	}
	
	/**
	 * Returns all GameSquare's matching {@link SquareValue#BLANK_UNTOUCHED}
	 * 
	 * @return List of blank squares on the board
	 */
	public List<T> getAllBlankSquares() {
		return gameBoardHelper.getAllBlankSquares(this.gameBoard);
	}
	
	public List<T> getAllNumberedSquares() {
		return gameBoardHelper.getAllNumberedSquares(this.gameBoard);
	}
	
	public List<T> getAllFlaggedSquares() {
		return gameBoardHelper.getAllFlaggedSquares(this.gameBoard);
	}
	
	public List<T> getSurroundingSquares(GameSquare square) {
		return gameBoardHelper.getSurroundingSquares(this.gameBoard, square);
	}
	
	public List<T> getSurroundingFlaggedSquares(GameSquare square) {
		return gameBoardHelper.getSurroundingFlaggedSquares(this.gameBoard, square);
	}
	
	public List<T> getSurroundingBlankSquares(GameSquare square) {
		return gameBoardHelper.getSurroundingBlankSquares(this.gameBoard, square);
	}
	
	public List<T> getSurroundingNumberedSquares(GameSquare square) {
		return gameBoardHelper.getSurroundingNumberedSquares(this.gameBoard, square);
	}
}
