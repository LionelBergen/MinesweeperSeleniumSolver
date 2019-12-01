package tests.minesweeper.solver.calculation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import component.model.GameSquare;
import solver.board.analyzing.SectionAnalyzer;
import solver.board.analyzing.SolutionAnalyzer;
import solver.component.KeyValue;
import solver.component.Rule;
import solver.component.RuleSet;
import solver.component.Section;
import solver.component.SectionAnalyzedResults;
import tests.minesweeper.data.SectionTestScenarios;

// TODO: Rename
public class SomeClass {
	public static void stuff(SectionAnalyzedResults input) {
		Set<KeyValue> unique = new HashSet<KeyValue>();
		
		for (Entry<RuleSet, Section> results : input.getContents().entrySet()) {
			Set<GameSquare> gameSquares = results.getValue().getGameSquares();
			System.out.println(gameSquares + " = " + results.getKey().getResultSets());
			unique.add(new KeyValue(0, gameSquares.size(), gameSquares));
		}
		
		System.out.println();
		
		final int sum = input.getOriginalSet().stream().mapToInt(Rule::getResultsEqual).sum();
		
		System.out.println(unique);
		System.out.println(sum);
		System.out.println();
		
		List<List<KeyValue>> resultz = SolutionAnalyzer.getAllPossibilities(sum, new ArrayList<KeyValue>(unique));
		
		for (List<KeyValue> resul : resultz) {
			// resul = [[D, E, H, L] = 1, [N, O, P] = 3, [C] = 0, [G] = 0, [K] = 0, [A, B, F, I] = 1, [J] = 0]
			System.out.println(resul);
			
			// rs = (A+B+F+I) + (C) + (G) + (J) = 3, ...
			for (Rule rs : input.getOriginalSet()) {
				for (KeyValue values : resul) {
					@SuppressWarnings("unchecked")
					HashSet<KeyValue> rez = (HashSet<KeyValue>) values.getKey();
					
					//System.out.println(rez);
					/*if (rs.getSquares().equals(values.getKey())) {
						System.out.println("yolo");
					}*/
				}
			}
		}
		
		for (Rule rs : input.getOriginalSet()) {
			List<Section> secs = new ArrayList<Section>();
			
			for (Entry<RuleSet, Section> results : input.getContents().entrySet()) {
				if (results.getKey().getResultSets().contains(rs)) {
					secs.add(results.getValue());
				}
			}

			int results = rs.getResultsEqual();
			List<List<GameSquare>> ggg = secs.stream().map(e -> e.getGameSquares().stream().collect(Collectors.toList()))
				.collect(Collectors.toList());
			
			List<KeyValue> objects = new ArrayList<>();
			
			// Transform
			for (List<GameSquare> x : ggg) {
				KeyValue newKeyValue = new KeyValue(0, x.size(), x);
				objects.add(newKeyValue);
			}
			
			System.out.println(rs.getResultsEqual() + " = " + secs.stream().map(e -> e.getGameSquares().stream().collect(Collectors.toList()))
					.collect(Collectors.toList()));
		}
		
		Integer y = 2;
	}
	
	@Test
	public void stuffTest() {
		SectionAnalyzedResults actualResult = SectionAnalyzer.breakupSection(SectionTestScenarios.SCENARIO_SPECIAL_01.getSection());
		
		stuff(actualResult);
	}
}
