package utility.util;

import static utility.util.Utility.isTouching;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import component.model.GameSquare;
import component.model.SquareValue;

public final class GameBoardHelper<T extends GameSquare> {
	public static final GameBoardHelper<GameSquare> GameBoardHelper = new GameBoardHelper<GameSquare>();
	
	public GameBoardHelper() {}
	
	public List<T> getSurroundingSquares(List<T> gameSquares, GameSquare square) {
		return gameSquares.stream().filter(e -> isTouching(square, e)).collect(Collectors.toList());
	}
	
	public List<T> getSurroundingFlaggedSquares(Collection<T> gameSquares, GameSquare square) {
		return getAllSquaresOfType(gameSquares, SquareValue.FLAGGED).stream().filter(e-> isTouching(square, e)).collect(Collectors.toList());
	}
	
	public List<T> getSurroundingBlankSquares(Collection<T> gameSquares, GameSquare square) {
		return getAllBlankSquares(gameSquares).stream().filter(e -> isTouching(square, e)).collect(Collectors.toList());
	}
	
	public List<T> getSurroundingNumberedSquares(List<T> gameSquares, GameSquare square) {
		return getAllNumberedSquares(gameSquares).stream().filter(e -> isTouching(square, e)).collect(Collectors.toList());
	}
	
	/**
	 * Returns all GameSquare's matching {@link SquareValue#BLANK_UNTOUCHED}
	 * 
	 * @return List of blank squares on the board
	 */
	public List<T> getAllBlankSquares(Collection<T> gameSquares) {
		return getAllSquaresOfType(gameSquares, SquareValue.BLANK_UNTOUCHED);
	}
	
	public List<T> getAllNumberedSquares(List<T> gameSquares) {
		return getAllSquaresOfType(gameSquares, SquareValue.NUMBERED_VALUES);
	}
	
	public List<T> getAllFlaggedSquares(List<T> gameSquares) {
		return getAllSquaresOfType(gameSquares, SquareValue.FLAGGED);
	}
	
	private List<T> getAllSquaresOfType(List<T> gameSquares, List<SquareValue> squareValuesToFilterBy) {
		return gameSquares.stream().filter(e -> squareValuesToFilterBy.contains(e.getValue())).collect(Collectors.toList());
	}
	
	private List<T> getAllSquaresOfType(Collection<T> gameSquares, SquareValue squareValueToFilterBy) {
		return gameSquares.stream().filter(e -> squareValueToFilterBy == e.getValue()).collect(Collectors.toList());
	}
}
