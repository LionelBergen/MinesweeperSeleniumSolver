package component.model;

import static utility.util.MathUtil.getRandomNumber;
import static utility.util.Utility.isTouching;

import java.util.List;
import java.util.stream.Collectors;

public class GameBoard<T extends GameSquare> {
	private List<T> gameBoard = null;
	
	public void setGameBoard(List<T> gameSquares) {
		this.gameBoard = gameSquares;
	}
	
	public int getSize() {
		return this.gameBoard.size();
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
	
	public List<T> getAllBlankSquares() {
		return getAllSquaresOfType(SquareValue.BLANK_UNTOUCHED);
	}
	
	public List<T> getAllNumberedSquares() {
		return getAllSquaresOfType(SquareValue.NUMBERED_VALUES);
	}
	
	public List<T> getallFlaggedSquares() {
		return getAllSquaresOfType(SquareValue.FLAGGED);
	}
	
	public List<T> getSurroundingFlaggedSquares(GameSquare square) {
		return getAllSquaresOfType(SquareValue.FLAGGED).stream().filter(e-> isTouching(square, e)).collect(Collectors.toList());
	}
	
	public List<T> getSurroundingBlankSquares(GameSquare square) {
		return getAllBlankSquares().stream().filter(e -> isTouching(square, e)).collect(Collectors.toList());
	}
	
	public List<T> getSurroundingNumberedSquares(GameSquare square) {
		return getAllNumberedSquares().stream().filter(e -> isTouching(square, e)).collect(Collectors.toList());
	}
	
	public boolean isGameWon() {
		return this.getAllBlankSquares().size() == 0;
	}
	
	private List<T> getAllSquaresOfType(List<SquareValue> squareValuesToFilterBy) {
		return this.gameBoard.stream().filter(e -> squareValuesToFilterBy.contains(e.getValue())).collect(Collectors.toList());
	}
	
	private List<T> getAllSquaresOfType(SquareValue squareValueToFilterBy) {
		return this.gameBoard.stream().filter(e -> squareValueToFilterBy == e.getValue()).collect(Collectors.toList());
	}
}
