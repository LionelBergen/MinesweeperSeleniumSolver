package tests.minesweeper.data.component;

import java.util.List;
import java.util.Map;

import component.model.GameSquare;
import solver.component.Section;
import solver.component.ResultSet;

public class SectionTestScenario {
	private Section section;
	private List<ResultSet> expectedOrigResults;
	private Map<List<ResultSet>, List<GameSquare>> expectedContents;
	
	public SectionTestScenario(Section section, Map<List<ResultSet>, List<GameSquare>> expectedContents, List<ResultSet> expectedOrigResults) {
		this.section = section;
		this.expectedOrigResults = expectedOrigResults;
		this.expectedContents = expectedContents;
	}
	
	public Section getSection() {
		return section;
	}
	
	public void setSection(Section section) {
		this.section = section;
	}
	
	public List<ResultSet> getExpectedOrigResults() {
		return expectedOrigResults;
	}
	
	public void setExpectedOrigResults(List<ResultSet> expectedOrigResults) {
		this.expectedOrigResults = expectedOrigResults;
	}

	public Map<List<ResultSet>, List<GameSquare>> getExpectedContents() {
		return expectedContents;
	}

	public void setExpectedContents(Map<List<ResultSet>, List<GameSquare>> expectedContents) {
		this.expectedContents = expectedContents;
	}
}
