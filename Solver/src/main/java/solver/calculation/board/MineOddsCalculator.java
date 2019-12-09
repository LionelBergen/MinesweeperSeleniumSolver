package solver.calculation.board;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import component.model.Section;
import solver.board.analyzing.SolutionAnalyzer;
import solver.component.KeyValue;
import solver.component.Rule;
import utility.logging.Logger;
import utility.util.MathUtil;

/**
 * Calculates the odds of hitting a mine for every section, given a set of {@link Rule}s.
 * 
 * @author Lionel Bergen
 */
public class MineOddsCalculator {
	/**
	 * Calculates the odds of a mine being in any of the squares in each section
	 * Note: The odds will be for any 1 (ONE) mine in the section. All mines in the section are the same
	 * 
	 * @param rules
	 * @param sections
	 * @return
	 */
	public static Map<Section, Double> calculateOdds(List<Rule> rules, Collection<Section> sections) {
		Logger.setCurrentTime();
		Set<List<KeyValue>> results = calculateAllPossibilities(rules, sections);
		Logger.logTimeTook("Calculating all " + results.size() + " possiblities");
		
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
		
		Map<Section, Double> returnValue = new HashMap<>();
		
		for (int i=0; i<values.length; i++) {
			double oddsOfMineForASingleSquareInSection = (values[i] / totalCombinations) / keys[i].getMaxValue();
			returnValue.put((Section) keys[i].getKey(), oddsOfMineForASingleSquareInSection);
		}
		
		return returnValue;
	}
	
	/**
	 * 
	 * 
	 * @param rules
	 * @param sections
	 * @return
	 */
	public static Set<List<KeyValue>> calculateAllPossibilities(List<Rule> rules, Collection<Section> sections) {
		Collection<KeyValue> keyValues = transformSectionsToKeyValues(sections);
		
		final int maxSum = rules.stream().mapToInt(Rule::getResultsEqual).sum();
		final int minSum = rules.stream().mapToInt(Rule::getResultsEqual).max().getAsInt();
		
		Logger.logMessage("Iterating from: " + minSum + " to: " + maxSum);
		
		// Get all possible solutions
		Set<List<KeyValue>> uniqueResults = new HashSet<>();
		for (int i = minSum; i<=maxSum; i++) {
			Logger.setCurrentTime();
			List<List<KeyValue>> results = SolutionAnalyzer.getAllPossibilities(i, keyValues);
			uniqueResults.addAll(results);
			Logger.logTimeTook("Getting " + results.size() + " possibilities for: " + i);
		}
		
		Set<List<KeyValue>> results = new HashSet<>();
		Logger.setCurrentTime();
		
		// Filter solutions that do not follow the 'Rules'. For example, if [K+NOP+J] = 1, then J and K cannot both be 1.
		for (List<KeyValue> resul : uniqueResults) {
			boolean valid = true;
			
			for (Rule rs : rules) {
				int actualResult = 0;
				for (KeyValue values : resul) {
					Section section = (Section) values.getKey();
					
					if (rs.getSquares().containsAll(section.getGameSquares())) {
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
		Logger.logTimeTook("Filtering out " + (uniqueResults.size() - results.size()) + " results");
		
		return results;
	}
	
	private static Collection<KeyValue> transformSectionsToKeyValues(Collection<Section> sections) {
		return sections.stream().map(e -> new KeyValue(0, e.getGameSquares().size(), e)).collect(Collectors.toList());
	}
}
