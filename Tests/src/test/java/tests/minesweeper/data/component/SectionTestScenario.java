package tests.minesweeper.data.component;

import java.util.List;
import java.util.Map;

import component.model.GameSquare;
import solver.component.Rule;
import solver.component.section.Section;

public class SectionTestScenario {
	private Section section;
	private List<Rule> expectedOrigResults;
	private Map<List<Section>, List<GameSquare>> expectedContents;
	
	public SectionTestScenario(Section section, Map<List<Section>, List<GameSquare>> expectedContents, List<Rule> expectedOrigResults) {
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
	
	public List<Rule> getExpectedOrigResults() {
		return expectedOrigResults;
	}
	
	public void setExpectedOrigResults(List<Rule> expectedOrigResults) {
		this.expectedOrigResults = expectedOrigResults;
	}

	public Map<List<Section>, List<GameSquare>> getExpectedContents() {
		return expectedContents;
	}

	public void setExpectedContents(Map<List<Section>, List<GameSquare>> expectedContents) {
		this.expectedContents = expectedContents;
	}
}
