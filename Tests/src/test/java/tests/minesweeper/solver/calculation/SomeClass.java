package tests.minesweeper.solver.calculation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.Test;

import solver.board.analyzing.SectionAnalyzer;
import solver.board.analyzing.SolutionAnalyzer;
import solver.component.ResultSet;
import solver.component.ResultSetCollection;
import solver.component.Section;
import solver.component.SectionAnalyzedResults;
import tests.minesweeper.data.SectionTestScenarios;

// TODO: Rename
public class SomeClass {
	public static void stuff(SectionAnalyzedResults input) {
		for (Entry<ResultSetCollection, Section> results : input.getContents().entrySet()) {
			System.out.println(results.getValue().getGameSquares() + " = " + results.getKey().getResultSets());
		}
		
		System.out.println();
		
		for (ResultSet rs : input.getOriginalSet()) {
			List<Section> secs = new ArrayList<Section>();
			
			for (Entry<ResultSetCollection, Section> results : input.getContents().entrySet()) {
				if (results.getKey().getResultSets().contains(rs)) {
					secs.add(results.getValue());
				}
			}
			
			//SolutionAnalyzer.SOLUTION_ANALYZER.
			
			System.out.println(rs.getResultsEqual() + " = " + secs.stream().map(e -> e.getGameSquares().stream().collect(Collectors.toList()))
					.collect(Collectors.toList()));
		}
	}
	
	@Test
	public void stuffTest() {
		SectionAnalyzedResults actualResult = SectionAnalyzer.breakupSection(SectionTestScenarios.SCENARIO_SPECIAL_01.getSection());
		
		stuff(actualResult);
	}
}
