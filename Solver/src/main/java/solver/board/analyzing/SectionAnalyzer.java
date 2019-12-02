package solver.board.analyzing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import component.model.GameSquare;
import solver.component.Section;
import solver.component.SectionAnalyzedResults;
import solver.component.SectionSet;
import solver.component.Rule;
import solver.component.RuleSet;

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
	
	/*public static List<Section> breakupRules(final List<Rule> rules, final Set<GameSquare> allSquares) {
		final RuleSet ruleSet = new RuleSet(rules);
		final Map<SectionSet, Section> contents;
		
		for (GameSquare gs : allSquares) {
			put(gs, ruleSet);
		}
		
		return result.getContents().keySet()
	}
	
	private void put(GameSquare gameSquare, final RuleSet rules) {
		final List<Section> sectionsThisSquareIsAPartOf = 
				rules.getRules().stream()
				.filter(e -> e.getSquares().contains(gameSquare))
				.map(e -> new Section(new HashSet<>(e.getSquares())))
				.collect(Collectors.toList());
		
		Set<GameSquare> otherSquaresInSameSet = get(setsThisSquareIsAPartOf);
		
		if (otherSquaresInSameSet == null) {
			otherSquaresInSameSet = new HashSet<GameSquare>();
			put(setsThisSquareIsAPartOf, otherSquaresInSameSet);
		}
		
		// Add the square to the set
		otherSquaresInSameSet.add(gameSquare);
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
