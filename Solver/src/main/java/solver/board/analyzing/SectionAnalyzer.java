package solver.board.analyzing;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import component.model.GameSquare;
import solver.component.Section;
import solver.component.SectionAnalyzedResults;
import solver.component.ResultSet;
import static utility.util.GameBoardHelper.GameBoardHelper;

public class SectionAnalyzer {
	public static SectionAnalyzedResults breakupSection(Section section) {
		SectionAnalyzedResults result = null;
		Set<GameSquare> squaresInSectionList = section.getGameSquares();
		
		List<ResultSet> set = new ArrayList<ResultSet>();
		
		for (GameSquare square : squaresInSectionList) {
			if (square.getValue().isNumbered()) {
				List<GameSquare> surroundingBlankSquares = GameBoardHelper.getSurroundingBlankSquares(squaresInSectionList, square);
				int numberOfMines = GameBoardHelper.getNumberOfMinesSurroundingSquare(squaresInSectionList, square);
				
				ResultSet sweeperSet = new ResultSet(surroundingBlankSquares, numberOfMines);
				
				set.add(sweeperSet);
			}
		}
		result = new SectionAnalyzedResults(set);
		
		for (GameSquare gs : squaresInSectionList) {
			result.put(gs);
		}
		
		return result;
	}
}
