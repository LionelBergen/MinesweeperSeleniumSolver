package tests.minesweeper.data.component;

import java.util.List;
import java.util.Map;
import java.util.Set;

import component.model.Section;
import component.model.gamesquare.GameSquare;
import solver.component.Rule;

public class SectionTestScenario {
	private List<Section> sections;
	private List<Rule> expectedOrigResults;
	private Map<List<Section>, Set<GameSquare>> expectedContents;
	
	public SectionTestScenario(List<Section> sections, Map<List<Section>, Set<GameSquare>> expectedContents, List<Rule> expectedOrigResults) {
		this.sections = sections;
		this.expectedOrigResults = expectedOrigResults;
		this.expectedContents = expectedContents;
	}
	
	public List<Section> getSections() {
		return sections;
	}
	
	public void setSections(List<Section> sections) {
		this.sections = sections;
	}
	
	public List<Rule> getExpectedRules() {
		return expectedOrigResults;
	}
	
	public void setExpectedOrigResults(List<Rule> expectedOrigResults) {
		this.expectedOrigResults = expectedOrigResults;
	}

	public Map<List<Section>, Set<GameSquare>> getExpectedContents() {
		return expectedContents;
	}

	public void setExpectedContents(Map<List<Section>, Set<GameSquare>> expectedContents) {
		this.expectedContents = expectedContents;
	}
}
