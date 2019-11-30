package solver.board.analyzing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import component.model.GameSquare;
import solver.component.Section;
import solver.component.SectionAnalyzedResults;
import solver.component.ResultSet;
import solver.component.ResultSetCollection;

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

		// Now that we have the definite rules, lets create sub sections
		/*for (ResultSet s : set) {
			List<ResultSet> others = set.stream().filter(e -> !e.equals(s)).collect(Collectors.toList());
			
			for (GameSquare gs : s.getSquares()) {
				List<ResultSet> setsThisSquareIsAPartOf = createCopy(others.stream().filter(e -> e.getSquares().contains(gs)).collect(Collectors.toList()));
				setsThisSquareIsAPartOf.add(createCopy(s));
				List<GameSquare> otherSquaresInSameSet = result.get(setsThisSquareIsAPartOf);
				
				if (otherSquaresInSameSet == null) {
					otherSquaresInSameSet = new ArrayList<GameSquare>();
					result.put(setsThisSquareIsAPartOf, otherSquaresInSameSet);
				}
				
				otherSquaresInSameSet.add(gs);
				System.out.println(gs + " is apart of: " + setsThisSquareIsAPartOf.size() + " sets.");
			}
		}*/
		
		for (Entry<ResultSetCollection, Section> results : result.getContents().entrySet()) {
			System.out.println(results.getValue().getGameSquares() + " = " + results.getKey().getResultSets());
		}
		
		System.out.println();
		
		for (ResultSet rs : set) {
			List<Section> secs = new ArrayList<Section>();
			
			for (Entry<ResultSetCollection, Section> results : result.getContents().entrySet()) {
				if (results.getKey().getResultSets().contains(rs)) {
					secs.add(results.getValue());
				}
			}
			
			System.out.println(rs.getResultsEqual() + " = " + secs.stream().map(e -> e.getGameSquares().stream().collect(Collectors.toList()))
					.collect(Collectors.toList()));
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
