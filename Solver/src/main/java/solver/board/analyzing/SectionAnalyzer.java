package solver.board.analyzing;

import java.util.List;
import java.util.Set;

import component.model.GameSquare;
import solver.component.Section;
import solver.component.SweeperSet;

public class SectionAnalyzer {
	public static List<SweeperSet> breakupSection(Section section) {
		Set<GameSquare> squaresInSection = section.getGameSquares();
		
		for (GameSquare square : squaresInSection) {
			if (square.getValue().isNumbered()) {
				
			}
		}
		
		return null;
	}
}
