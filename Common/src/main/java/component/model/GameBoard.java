package component.model;

import static utility.util.MathUtil.getRandomNumber;

import java.util.List;

import component.model.gamesquare.GameSquare;
import component.model.gamesquare.SquareValue;
import utility.util.GameBoardHelper;

/**
 * Contains {@link GameSquare}'s representing a Minesweeper board.
 *  
 * @author Lionel Bergen
 */
public abstract class GameBoard {
	private final GameBoardHelper gameBoardHelper = new GameBoardHelper();
	private List<GameSquare> gameBoard = null;
	
	public List<GameSquare> getGameBoard() {
		return this.gameBoard;
	}
	
	protected void setGameBoard(List<GameSquare> gameSquares) {
		this.gameBoard = gameSquares;
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
	public GameSquare getRandomLonelySquare() {
		return getAllBlankSquares().stream().filter(e -> getSurroundingBlankSquares(e).size() == 8).findFirst().orElse(null);
	}

	public GameSquare getRandomOpenElement() {
		List<GameSquare> blankSquares = getAllBlankSquares();
		
		return blankSquares.get(getRandomNumber(0, blankSquares.size() - 1));
	}
	
	/**
	 * Returns all GameSquare's matching {@link SquareValue#BLANK_UNTOUCHED}
	 * 
	 * @return List of blank squares on the board
	 */
	public List<GameSquare> getAllBlankSquares() {
		return gameBoardHelper.getAllBlankSquares(this.gameBoard);
	}
	
	public List<GameSquare> getAllNumberedSquares() {
		return gameBoardHelper.getAllNumberedSquares(this.gameBoard);
	}
	
	public List<GameSquare> getAllFlaggedSquares() {
		return gameBoardHelper.getAllFlaggedSquares(this.gameBoard);
	}
	
	public List<GameSquare> getSurroundingSquares(GameSquare square) {
		return gameBoardHelper.getSurroundingSquares(this.gameBoard, square);
	}
	
	public List<GameSquare> getSurroundingFlaggedSquares(GameSquare square) {
		return gameBoardHelper.getSurroundingFlaggedSquares(this.gameBoard, square);
	}
	
	public List<GameSquare> getSurroundingBlankSquares(GameSquare square) {
		return gameBoardHelper.getSurroundingBlankSquares(this.gameBoard, square);
	}
	
	public List<GameSquare> getSurroundingNumberedSquares(GameSquare square) {
		return gameBoardHelper.getSurroundingNumberedSquares(this.gameBoard, square);
	}
}
