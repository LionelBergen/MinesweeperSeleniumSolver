package solver.calculation;

import java.util.ArrayList;
import java.util.List;

import component.model.GameBoard;
import component.model.GameSquare;
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
		
		return results;
	}
	
	private static void addSquareToResultSet(GameBoard<GameSquare> gameBoard, GameSquare gameSquare, ResultSet resultSet) {
		resultSet.add(gameSquare);
		
		// doesn't matter if duplicates are added, ResultSet should handle that
		for (GameSquare touchingSquare : gameBoard.getSurroundingSquares(gameSquare)) {
			if (touchingSquare.getValue().isNumbered()) {
				addSquareToResultSet(gameBoard, touchingSquare, resultSet);
			} else {
				resultSet.add(touchingSquare);
			}
		}
	}
}
