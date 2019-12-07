package solver.board.analyzing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import component.model.GameBoard;
import component.model.Section;
import component.model.gamesquare.GameSquare;
import component.model.gamesquare.SquareValue;

/**
 * Utilizes {@link GameBoard} methods to get {@link Section}s which can be used in calculations
 * 
 * @author Lionel Bergen
 */
public class BoardAnalyzer {
	public static List<Section> breakupBoard(GameBoard gameBoard) {
		final List<Section> sectionsOfInterest = new ArrayList<>();
		
		// Get a resultset for every number
		for (GameSquare gameSquare : gameBoard.getAllNumberedSquares()) {
			Section rs = new Section();
			
			addSquareToResultSet(gameBoard, gameSquare, rs);
			
			sectionsOfInterest.add(rs);
		}
		
		// remove duplicates
		return new ArrayList<Section> (new HashSet<Section>(sectionsOfInterest));
	}
	
	private static void addSquareToResultSet(GameBoard gameBoard, GameSquare gameSquare, Section resultSet) { 
		addSquareToResultSet(gameBoard, gameSquare, resultSet, false);
	}
	
	private static void addSquareToResultSet(GameBoard gameBoard, GameSquare gameSquare, Section resultSet, boolean numberedOnly) {
		resultSet.add(gameSquare);
		
		final List<? extends GameSquare> squaresToProcess = numberedOnly ? gameBoard.getSurroundingNumberedSquares(gameSquare) : gameBoard.getSurroundingSquares(gameSquare);
		
		// doesn't matter if duplicates are added, ResultSet should handle that
		for (GameSquare touchingSquare : squaresToProcess) {
			if (touchingSquare.getValue().isNumbered()) {
				if (!resultSet.getGameSquares().contains(touchingSquare)) {
					addSquareToResultSet(gameBoard, touchingSquare, resultSet);
				}
			} else {
				List<GameSquare> touchingSquaresNotInResults = getTouchingSquaresNotInResults(gameBoard, resultSet, touchingSquare);
				// If the squares are all blank, no need to process touching squares (it's a 'border' square)
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
	
	private static List<GameSquare> getTouchingSquaresNotInResults(GameBoard gameBoard, Section resultSet, GameSquare square) {
		return gameBoard.getSurroundingSquares(square).stream().filter(e -> !resultSet.getGameSquares().contains(e)).collect(Collectors.toList());
	}
}
