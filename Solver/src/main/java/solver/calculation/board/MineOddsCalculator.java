package solver.calculation.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import component.model.Section;
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
		final List<KeyValue> knownValues = new ArrayList<>();
		
		for (Rule rule : rules) {
			List<Section> sectionsRelatingToRule = getSectionsInRule(sections, rule);
			
			getAllVariationsOfARule(sectionsRelatingToRule, rule);
			
			List<KeyValue> valuesForRule = transformSectionsToKeyValues(sectionsRelatingToRule);
			populateListWithKnown(valuesForRule, knownValues);
			
			if (isRuleFollowed(rule, valuesForRule)) {
				printRuleInfo(valuesForRule, rule);
				knownValues.addAll(valuesForRule);
				// This rule is now solved.
				continue;
			}
			
			// Get the first unknown value to modify
			KeyValue section = getFirstValueNotFoundInList(valuesForRule, knownValues);
			int valueToSet = Math.min(rule.getResultsEqual(), section.getMaxValue());
			section.setValue(valueToSet);
			while (anyValuesTooHigh(rule, valuesForRule)) {
				valueToSet--;
				section.setValue(valueToSet);
			}
			
			if (valueToSet < 0 ) {
				throw new RuntimeException("rules cannot be fulfulled");
			}
			
			if (isRuleFollowed(rule, valuesForRule)) {
				printRuleInfo(valuesForRule, rule);
				knownValues.addAll(valuesForRule);
			} else {
				int x = 0;
			}
		}
		
		return null;
	}
	
	private static KeyValue getFirstValueNotFoundInList(List<KeyValue> values, List<KeyValue> filterList) {
		return values.stream().filter(e -> !filterList.contains(e)).findFirst().orElse(null);
	}
	
	// for debugging only
	private static void printRuleInfo(List<KeyValue> values, Rule rule) {
		List<String> y = values.stream().map(e -> ((Section) e.getKey()) + "=" + e.getValue()).collect(Collectors.toList());
		
		System.out.println("solved: " + y + "= " + rule.getResultsEqual());
	}
	
	/**
	 * If any key in the knownValues list matches a key in the listToPopulate, sets the value of the 'listToPopulate' to the value found 
	 * 
	 * @param listToPopulate List to modify
	 * @param knownValues Gets values from here
	 */
	private static void populateListWithKnown(Collection<KeyValue> listToPopulate, Collection<KeyValue> knownValues) {
		for (KeyValue value : listToPopulate) {
			for (KeyValue knownValue : knownValues) {
				if (knownValue.getKey().equals(value.getKey())) {
					value.setValue(knownValue.getValue());
				}
			}
		}
	}
	
	// Ignores rules which are broken due to being too low (E.g actual=1 rule=2).
	private static boolean anyValuesTooHigh(Collection<Rule> rules, Collection<KeyValue> values) {
		boolean allRulesAreFollowed = true;
		
		for (Rule rule : rules) {
			int actualResult = 0;
			
			for (KeyValue value : values) {
				Section section = (Section) value.getKey();
				
				if (rule.getSquares().containsAll(section.getGameSquares())) {
					actualResult += value.getValue();
				}
			}
			
			if (actualResult > rule.getResultsEqual()) {
				allRulesAreFollowed = false;
				break;
			}
		}
		
		return !allRulesAreFollowed;
	}
	
	// TODO: check if both these methods are needed
	private static boolean anyValuesTooHigh(Rule rules, Collection<KeyValue> values) {
		return anyValuesTooHigh(Arrays.asList(rules), values);
	}
	
	/**
	 * Get all sections relating to a rule. For example if the Rule is {A, BC + D} = 3, we will get A, BC and D.
	 * 
	 * @param sections our HayStack. Find Sections here
	 * @param rule our Needle. Contains sections to be found
	 * @return Sections in the rule
	 */
	private static List<Section> getSectionsInRule(Collection<Section> sections, Rule rule) {
		return sections.stream().filter(e -> rule.getSquares().containsAll(e.getGameSquares())).collect(Collectors.toList());
	}
	
	private static List<Rule> getRulesFilteredBySection(Collection<Rule> rules, Section section) {
		return rules.stream().filter(e -> e.getSectionThisRuleCameFrom().equals(section)).collect(Collectors.toList());
	}
	
	private static List<KeyValue> transformSectionsToKeyValues(Collection<Section> sections) {
		return sections.stream().map(e -> new KeyValue(0, e.getGameSquares().size(), e)).collect(Collectors.toList());
	}
}
