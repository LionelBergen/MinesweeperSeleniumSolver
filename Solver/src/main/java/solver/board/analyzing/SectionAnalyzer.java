package solver.board.analyzing;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import component.model.GameSquare;
import solver.component.Section;
import solver.component.SectionAnalyzedResults;
import solver.component.Rule;
import static utility.util.GameBoardHelper.GameBoardHelper;

public class SectionAnalyzer {
	public static SectionAnalyzedResults breakupSection(Section section) {
		SectionAnalyzedResults result = null;
		Set<GameSquare> squaresInSectionList = section.getGameSquares();
		
		List<Rule> ruleSet = new ArrayList<Rule>();
		
		for (GameSquare square : squaresInSectionList) {
			if (square.getValue().isNumbered()) {
				List<GameSquare> surroundingBlankSquares = GameBoardHelper.getSurroundingBlankSquares(squaresInSectionList, square);
				int numberOfMines = GameBoardHelper.getNumberOfMinesSurroundingSquare(squaresInSectionList, square);
				
				ruleSet.add(new Rule(surroundingBlankSquares, numberOfMines));
			}
		}
		result = new SectionAnalyzedResults(ruleSet);
		
		for (GameSquare gs : squaresInSectionList) {
			result.put(gs);
		}
		
		return result;
	}
}
