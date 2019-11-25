package tests.minesweeper.data.component;

import java.util.List;

import component.model.RegularGameBoard;
import solver.component.Section;

public class TestScenario {
	private RegularGameBoard gameBoard;
	private List<Section> expectedSections;
	
	public TestScenario(RegularGameBoard board, List<Section> expectedSections) {
		this.gameBoard = board;
		this.expectedSections = expectedSections;
	}
	
	public RegularGameBoard getGameBoard() {
		return gameBoard;
	}
	
	public void setGameBoard(RegularGameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	public List<Section> getExpectedSections() {
		return expectedSections;
	}
	
	public void setExpectedSections(List<Section> expectedSections) {
		this.expectedSections = expectedSections;
	}
}
