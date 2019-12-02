package tests.minesweeper.solver.calculation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;

import component.model.GameSquare;
import solver.board.analyzing.SectionAnalyzer;
import solver.board.analyzing.SolutionAnalyzer;
import solver.component.KeyValue;
import solver.component.Rule;
import solver.component.Section;
import tests.minesweeper.data.SectionTestScenarios;
import utility.util.MathUtil;

// TODO: Rename
public class SomeClass {
	public static void stuff(List<Rule> rules, Collection<Section> sections) {
		Set<KeyValue> unique = new HashSet<KeyValue>();
		
		for (Section section : sections) {
			Set<GameSquare> gameSquares = section.getGameSquares();
			unique.add(new KeyValue(0, gameSquares.size(), gameSquares));
		}
		
		System.out.println();
		
		final int maxSum = rules.stream().mapToInt(Rule::getResultsEqual).sum();
		final int minSum = rules.stream().mapToInt(Rule::getResultsEqual).max().getAsInt();
		
		System.out.println(unique);
		System.out.println(maxSum);
		System.out.println(minSum);
		System.out.println();
		
		// Get all possible solutions
		Set<List<KeyValue>> uniqueResults = new HashSet<>();
		for (int i = minSum; i<=maxSum; i++) {
			uniqueResults.addAll(SolutionAnalyzer.getAllPossibilities(i, new ArrayList<KeyValue>(unique)));
		}
		
		Set<List<KeyValue>> results = new HashSet<>();
		
		// Filter solutions that do not follow the 'Rules'. For example, if [K+NOP+J] = 1, then J and K cannot both be 1.
		for (List<KeyValue> resul : uniqueResults) {
			boolean valid = true;
			
			for (Rule rs : rules) {
				int actualResult = 0;
				for (KeyValue values : resul) {
					@SuppressWarnings("unchecked")
					HashSet<GameSquare> rez = (HashSet<GameSquare>) values.getKey();
					
					if (rs.getSquares().containsAll(rez)) {
						actualResult += values.getValue();
					}
				}
				
				if (actualResult != rs.getResultsEqual()) {
					valid = false;
					break;
				}
			}
			
			if (valid) {
				results.add(resul);
			}
		}
		
		long[] ncrs = new long[results.iterator().next().size()];
		
		// 2d array
		KeyValue[] keys = new KeyValue[results.iterator().next().size()];
		double[] values = new double[results.iterator().next().size()];

		long totalCombinations = 0;
		for (List<KeyValue> x : results) {
			for (int i=0; i < x.size(); i++) {
				KeyValue keyValue = x.get(i);
				
				long totalCombinationsForSquareOnRow = MathUtil.nCr(keyValue.getMaxValue(), keyValue.getValue());
				ncrs[i] = totalCombinationsForSquareOnRow;
			}
			
			long totalCombinationsForResult = 1;
			for (long z : ncrs) {
				totalCombinationsForResult *= z;
			}
			System.out.println("total combinations: " + totalCombinationsForResult);
			totalCombinations += totalCombinationsForResult;
			
			for (int i=0; i < x.size(); i++) {
				KeyValue keyValue = x.get(i);
				double oddsInSection = ((double) keyValue.getValue() / keyValue.getMaxValue()) * totalCombinationsForResult;
				values[i] += oddsInSection;
				keys[i] = keyValue;
			}
		}
		
		System.out.println("total combinations: " + totalCombinations);
		
		for (int i=0; i<values.length; i++) {
			System.out.println(keys[i] + " = " + ((values[i] / totalCombinations) / keys[i].getMaxValue()));
		}
	}
	
	@Test
	public void stuffTest() {
		Section section = SectionTestScenarios.SCENARIO_SPECIAL_01.getSection();
		
		List<Rule> rules = SectionAnalyzer.breakupSectionIntoRules(section);
		Collection<Section> allSubSectionsFromRules = SectionAnalyzer.getSections(rules, section.getGameSquares());
		
		stuff(rules, allSubSectionsFromRules);
	}
}
