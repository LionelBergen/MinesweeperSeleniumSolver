package solver.calculation.board;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import component.model.Section;
import solver.calculation.RulesCombinationCalculator;
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
		List<List<KeyValue>> results = RulesCombinationCalculator.getAllVariations(sections, rules);
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
}
