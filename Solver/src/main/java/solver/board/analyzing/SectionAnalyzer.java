package solver.board.analyzing;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import component.model.GameSquare;
import solver.component.Section;
import solver.component.SectionAnalyzedResults;
import solver.component.Rule;
import static utility.util.GameBoardHelper.GameBoardHelper;

// TODO: Break this up into getting the List<Rules>, and then getting the broken up rules based on List<Rule>.
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
	
	/*public static List<Section> breakupRules(List<Rule> ruleSet, Set<GameSquare> allSquares) {
		SectionAnalyzedResults result = new SectionAnalyzedResults(ruleSet);
		
		for (GameSquare gs : allSquares) {
			result.put(gs);
		}
		
		return result.getContents().keySet()
	}
	
	public static List<Rule> breakupSection(Section section) {
		Set<GameSquare> squaresInSectionList = section.getGameSquares();
		
		List<Rule> ruleSet = new ArrayList<Rule>();
		
		for (GameSquare square : squaresInSectionList) {
			if (square.getValue().isNumbered()) {
				List<GameSquare> surroundingBlankSquares = GameBoardHelper.getSurroundingBlankSquares(squaresInSectionList, square);
				int numberOfMines = GameBoardHelper.getNumberOfMinesSurroundingSquare(squaresInSectionList, square);
				
				ruleSet.add(new Rule(surroundingBlankSquares, numberOfMines));
			}
		}
		
		return ruleSet;
	}*/
}
