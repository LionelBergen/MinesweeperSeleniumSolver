package solver.board.analyzing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import component.model.GameSquare;
import solver.component.Section;
import solver.component.SweeperSet;

import static utility.util.GameBoardHelper.GameBoardHelper;

public class SectionAnalyzer {
	public static List<SweeperSet> breakupSection(Section section) {
		Set<GameSquare> squaresInSection = section.getGameSquares();
		List<SweeperSet> set = new ArrayList<SweeperSet>();
		// TODO:
		List<GameSquare> squaresInSectionList = new ArrayList<GameSquare>(squaresInSection);
		
		for (GameSquare square : squaresInSection) {
			if (square.getValue().isNumbered()) {
				List<GameSquare> surroundingBlankSquares = GameBoardHelper.getSurroundingBlankSquares(squaresInSectionList, square);
				// TODO: This logic should be in another class
				int numberOfMines = square.getValue().getNumberOfSurroundingMines() - GameBoardHelper.getSurroundingFlaggedSquares(squaresInSectionList, square).size();
				
				SweeperSet sweeperSet = new SweeperSet(surroundingBlankSquares, 
						numberOfMines);
				
				set.add(sweeperSet);
			}
		}
		
		return set;
	}
}
