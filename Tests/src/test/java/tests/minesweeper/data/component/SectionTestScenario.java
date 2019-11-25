package tests.minesweeper.data.component;

import java.util.List;

import solver.component.Section;
import solver.component.SweeperSet;

public class SectionTestScenario {
	private Section section;
	private List<SweeperSet> expectedResults;
	
	public SectionTestScenario(Section section, List<SweeperSet> expectedResults) {
		this.section = section;
		this.expectedResults = expectedResults;
	}
	
	public Section getSection() {
		return section;
	}
	
	public void setSection(Section section) {
		this.section = section;
	}
	
	public List<SweeperSet> getExpectedResults() {
		return expectedResults;
	}
	
	public void setExpectedResults(List<SweeperSet> expectedResults) {
		this.expectedResults = expectedResults;
	}
}
