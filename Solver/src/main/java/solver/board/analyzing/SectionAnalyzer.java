package solver.board.analyzing;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import component.model.GameSquare;
import solver.component.Section;
import solver.component.SectionAnalyzedResults;
import solver.component.ResultSet;

import static utility.util.GameBoardHelper.GameBoardHelper;

public class SectionAnalyzer {
	public static SectionAnalyzedResults breakupSection(Section section) {
		SectionAnalyzedResults result = null;
		
		Set<GameSquare> squaresInSection = section.getGameSquares();
		List<ResultSet> set = new ArrayList<ResultSet>();
		// TODO:
		List<GameSquare> squaresInSectionList = new ArrayList<GameSquare>(squaresInSection);
		
		for (GameSquare square : squaresInSection) {
			if (square.getValue().isNumbered()) {
				List<GameSquare> surroundingBlankSquares = GameBoardHelper.getSurroundingBlankSquares(squaresInSectionList, square);
				// TODO: This logic should be in another class
				int numberOfMines = square.getValue().getNumberOfSurroundingMines() - GameBoardHelper.getSurroundingFlaggedSquares(squaresInSectionList, square).size();
				
				ResultSet sweeperSet = new ResultSet(surroundingBlankSquares, 
						numberOfMines);
				
				set.add(sweeperSet);
			}
		}
		
		result = new SectionAnalyzedResults(set);
		
		// Now that we have the definite rules, lets create sub sections
		for (ResultSet s : set) {
			List<ResultSet> others = set.stream().filter(e -> !e.equals(s)).collect(Collectors.toList());
			
			for (GameSquare gs : s.getSquares()) {
				List<ResultSet> otherSetsThisSquareIsAPartOf = createCopy(others.stream().filter(e -> e.getSquares().contains(gs)).collect(Collectors.toList()));
				otherSetsThisSquareIsAPartOf.add(createCopy(s));
				List<GameSquare> otherSquaresInSameSet = result.get(otherSetsThisSquareIsAPartOf);
				
				if (otherSquaresInSameSet == null) {
					otherSquaresInSameSet = new ArrayList<GameSquare>();
					result.put(otherSetsThisSquareIsAPartOf, otherSquaresInSameSet);
				}
				
				otherSquaresInSameSet.add(gs);
			}
		}
		
		return result;
	}
	
	private static List<ResultSet> createCopy(List<ResultSet> original) {
		List<ResultSet> copy = new ArrayList<ResultSet>();
		
		for (ResultSet orig : original) {
			copy.add(createCopy(orig));
		}
		
		return copy;
	}
	
	private static ResultSet createCopy(ResultSet orig) {
		return new ResultSet(orig.getSquares(), ResultSet.UNKNOWN_VALUE);
	}
}
