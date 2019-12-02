package solver.board.analyzing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import component.model.GameSquare;
import solver.component.Section;
import solver.component.SectionAnalyzedResults;
import solver.component.SectionSet;
import solver.component.Rule;
import static utility.util.GameBoardHelper.GameBoardHelper;

public class SectionAnalyzer {
	public static SectionAnalyzedResults breakupSection(Section section) {
		SectionAnalyzedResults result = null;
		
		List<Rule> ruleSet = breakupSectionIntoRules(section);
		result = new SectionAnalyzedResults(ruleSet);
		
		Set<GameSquare> squaresInSectionList = section.getGameSquares();
		for (GameSquare gs : squaresInSectionList) {
			result.put(gs);
		}
		
		return result;
	}
	
	public static Set<SectionSet> getSectionSets(List<Rule> rules, Collection<GameSquare> allSquares) {
		SectionAnalyzedResults result = new SectionAnalyzedResults(rules);
		
		for (GameSquare gs : allSquares) {
			result.put(gs);
		}
		
		return result.getContents().keySet();
	}
	
	public static Collection<Section> getSections(List<Rule> rules, Collection<GameSquare> allSquares) {
		SectionAnalyzedResults result = new SectionAnalyzedResults(rules);
		
		for (GameSquare gs : allSquares) {
			result.put(gs);
		}
		
		return result.getContents().values();
	}
	
	public static List<Rule> breakupSectionIntoRules(Section section) {
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
	}
}
