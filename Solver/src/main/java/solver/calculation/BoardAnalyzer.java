package solver.calculation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import component.model.GameBoard;
import component.model.GameSquare;
import component.model.SquareValue;
import solver.component.ResultSet;

/**
 * Utilizes {@link GameBoard} methods to get {@link ResultSet}s which can be used in calculations
 * 
 * @author Lionel Bergen
 */
public class BoardAnalyzer {
	
	public static List<ResultSet> breakupBoard(GameBoard<GameSquare> gameBoard) {
		List<ResultSet> results = new ArrayList<ResultSet>();
		
		// Get a resultset for every number
		for (GameSquare gameSquare : gameBoard.getAllNumberedSquares()) {
			ResultSet rs = new ResultSet();
			
			addSquareToResultSet(gameBoard, gameSquare, rs);
			
			results.add(rs);
		}
		
		// remove duplicates
		return new ArrayList<ResultSet>(new HashSet<ResultSet>(results));
	}
	
	private static void addSquareToResultSet(GameBoard<GameSquare> gameBoard, GameSquare gameSquare, ResultSet resultSet) { 
		addSquareToResultSet(gameBoard, gameSquare, resultSet, false);
	}
	
	private static void addSquareToResultSet(GameBoard<GameSquare> gameBoard, GameSquare gameSquare, ResultSet resultSet, boolean numberedOnly) {
		resultSet.add(gameSquare);
		
		final List<GameSquare> squaresToProcess = numberedOnly ? gameBoard.getSurroundingNumberedSquares(gameSquare) : gameBoard.getSurroundingSquares(gameSquare);
		
		// doesn't matter if duplicates are added, ResultSet should handle that
		for (GameSquare touchingSquare : squaresToProcess) {
			if (touchingSquare.getValue().isNumbered()) {
				if (!resultSet.getGameSquares().contains(touchingSquare)) {
					addSquareToResultSet(gameBoard, touchingSquare, resultSet);
				}
			} else {
				List<GameSquare> touchingSquaresNotInResults = getTouchingSquaresNotInResults(gameBoard, resultSet, touchingSquare);
				
				if (numberOfBlankSquares(touchingSquaresNotInResults) == touchingSquaresNotInResults.size()) {
					resultSet.add(touchingSquare);
				} else {
					if (!resultSet.getGameSquares().contains(touchingSquare)) {
						addSquareToResultSet(gameBoard, touchingSquare, resultSet, true);
					}
				}
			}
		}
	}
	
	private static int numberOfBlankSquares(List<GameSquare> gameSquares) {
		return (int) gameSquares.stream().filter(e -> e.getValue().equals(SquareValue.BLANK_UNTOUCHED)).count();
	}
	
	private static List<GameSquare> getTouchingSquaresNotInResults(GameBoard<GameSquare> gameBoard, ResultSet resultSet, GameSquare square) {
		return gameBoard.getSurroundingSquares(square).stream().filter(e -> !resultSet.getGameSquares().contains(e)).collect(Collectors.toList());
	}
}
