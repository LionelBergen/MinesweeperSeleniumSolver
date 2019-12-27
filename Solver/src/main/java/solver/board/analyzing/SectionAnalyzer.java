package solver.board.analyzing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import component.model.Section;
import component.model.gamesquare.GameSquare;
import solver.component.SectionAnalyzedResults;
import solver.component.Rule;
import static utility.util.GameBoardHelper.GameBoardHelper;

public class SectionAnalyzer {
	public static Collection<Section> getSections(List<Rule> rules, Collection<GameSquare> allSquares) {
		//Map<SectionSet, Section> contents;
		//RuleSet sectionRules = new RuleSet(rules);
		
		SectionAnalyzedResults result = new SectionAnalyzedResults(rules);
		
		for (GameSquare gs : allSquares) {
			result.put(gs);
		}
		
		return result.getContents().values();
	}
	
	public static List<Rule> breakupSectionIntoRules(Section section) {
		return breakupSectionIntoRules(Arrays.asList(section));
	}
	
	public static List<Rule> breakupSectionIntoRules(Collection<Section> sections) {
		List<Rule> ruleSet = new ArrayList<Rule>();
		
		for (Section section : sections) {
			Set<GameSquare> squaresInSectionList = section.getGameSquares();
			
			for (GameSquare square : squaresInSectionList) {
				if (square.getValue().isNumbered()) {
					List<GameSquare> surroundingBlankSquares = GameBoardHelper.getSurroundingBlankSquares(squaresInSectionList, square);
					int numberOfMines = GameBoardHelper.getNumberOfMinesSurroundingSquare(squaresInSectionList, square);
					
					ruleSet.add(new Rule(surroundingBlankSquares, numberOfMines, section));
				}
			}
		}
		
		return ruleSet;
	}
}
