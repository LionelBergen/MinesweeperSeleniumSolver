package utility.util;

import static utility.util.Utility.isTouching;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import component.model.gamesquare.GameSquare;
import component.model.gamesquare.SquareValue;

public final class GameBoardHelper {
	public static final GameBoardHelper GameBoardHelper = new GameBoardHelper();
	
	public GameBoardHelper() {}
	
	public List<GameSquare> getSurroundingSquares(Collection<GameSquare> gameSquares, GameSquare square) {
		return gameSquares.stream().filter(e -> isTouching(square, e)).collect(Collectors.toList());
	}
	
	public List<GameSquare> getSurroundingFlaggedSquares(Collection<GameSquare> gameSquares, GameSquare square) {
		return getAllSquaresOfType(gameSquares, SquareValue.FLAGGED).stream().filter(e-> isTouching(square, e)).collect(Collectors.toList());
	}
	
	public List<GameSquare> getSurroundingBlankSquares(Collection<GameSquare> gameSquares, GameSquare square) {
		return getAllBlankSquares(gameSquares).stream().filter(e -> isTouching(square, e)).collect(Collectors.toList());
	}
	
	public List<GameSquare> getSurroundingNumberedSquares(Collection<GameSquare> gameSquares, GameSquare square) {
		return getAllNumberedSquares(gameSquares).stream().filter(e -> isTouching(square, e)).collect(Collectors.toList());
	}
	
	/**
	 * Returns all GameSquare's matching {@link SquareValue#BLANK_UNTOUCHED}
	 * 
	 * @return List of blank squares on the board
	 */
	public List<GameSquare> getAllBlankSquares(Collection<GameSquare> gameSquares) {
		return getAllSquaresOfType(gameSquares, SquareValue.BLANK_UNTOUCHED);
	}
	
	public List<GameSquare> getAllNumberedSquares(Collection<GameSquare> gameSquares) {
		return getAllSquaresOfType(gameSquares, SquareValue.NUMBERED_VALUES);
	}
	
	public List<GameSquare> getAllFlaggedSquares(Collection<GameSquare> gameSquares) {
		return getAllSquaresOfType(gameSquares, SquareValue.FLAGGED);
	}
	
	public int getNumberOfMinesSurroundingSquare(Collection<GameSquare> gameSquares, GameSquare gameSquare) {
		return gameSquare.getValue().getNumberOfSurroundingMines() - getSurroundingFlaggedSquares(gameSquares, gameSquare).size();
	}
	
	private List<GameSquare> getAllSquaresOfType(Collection<GameSquare> gameSquares, List<SquareValue> squareValuesToFilterBy) {
		return gameSquares.stream().filter(e -> squareValuesToFilterBy.contains(e.getValue())).collect(Collectors.toList());
	}
	
	private List<GameSquare> getAllSquaresOfType(Collection<GameSquare> gameSquares, SquareValue squareValueToFilterBy) {
		return gameSquares.stream().filter(e -> squareValueToFilterBy == e.getValue()).collect(Collectors.toList());
	}
}
