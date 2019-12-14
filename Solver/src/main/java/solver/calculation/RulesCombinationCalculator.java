package solver.calculation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import component.model.Section;
import solver.component.KeyValue;
import solver.component.Rule;

import static solver.component.transformer.SectionTransformer.transformSectionsToKeyValues;

public class RulesCombinationCalculator {
	public static List<List<KeyValue>> getAllVariations(List<Section> allSections, List<Rule> rules) {
		final Rule firstRule = rules.get(0);
		List<Rule> otherRules = rules.stream().filter(e -> e != firstRule).collect(Collectors.toList());
		
		List<Section> sectionRelatingToRule = getSectionsInRule(allSections, firstRule);
		
		List<List<KeyValue>> values = getAllVariationsOfARule(sectionRelatingToRule, firstRule);
		// filter any items that are invalid given the other rules
		values = getValuesThatDontOverflow(values, rules);
		
		List<List<KeyValue>> allKnownValues = new ArrayList<>();
		allKnownValues.addAll(values);
		
		for (Rule nextRule : otherRules) {
			Set<List<KeyValue>> results = new HashSet<>();
			
			for (List<KeyValue> knownValues : allKnownValues) {
				results.addAll(foo(allSections, knownValues, nextRule, rules, results));
			}
			allKnownValues = new ArrayList<>(results);
		}
		
		// filter items with broken rules
		allKnownValues = allKnownValues.stream().filter(e -> !anyRulesBroken(rules, e)).collect(Collectors.toList());
		
		return allKnownValues;
	}
	
	private static List<List<KeyValue>> foo(List<Section> allSections, List<KeyValue> knownValues, Rule rule, List<Rule> allRules, Collection<List<KeyValue>> results) {
		List<Section> sectionRelatingToRule = getSectionsInRule(allSections, rule);
		List<KeyValue> sectionsTransformed = transformSectionsToKeyValues(sectionRelatingToRule);
		populateListWithKnown(sectionsTransformed, knownValues);
		
		List<List<KeyValue>> allValuesForRule = getAllVariationsOfARuleWithKnownValues(sectionsTransformed, rule);
		allValuesForRule = getValuesThatDontOverflow(allValuesForRule, allRules);
		
		// Add all known values to the list
		combineLists(knownValues, allValuesForRule);
		
		return allValuesForRule;
	}
	
	public static List<List<KeyValue>> getAllVariationsOfARule(List<Section> sectionsRelatingToRule, Rule rule) {
		List<KeyValue> sectionsTransformed = transformSectionsToKeyValues(sectionsRelatingToRule);
		return getAllVariationsOfARuleWithKnownValues(sectionsTransformed, rule);
	}
	
	public static List<List<KeyValue>> getAllVariationsOfARuleWithKnownValues(List<KeyValue> sectionsRelatingToRule, Rule rule) {
		List<List<KeyValue>> results = new ArrayList<>();
		
		List<KeyValue> valuesWithKnown = sectionsRelatingToRule.stream().filter(e -> e.getValue() > 0).collect(Collectors.toList());
		sectionsRelatingToRule.removeAll(valuesWithKnown);
		
		getAllVariationsOfARule(sectionsRelatingToRule, rule, results, valuesWithKnown);
		
		return results;
	}
	
	private static void getAllVariationsOfARule(List<KeyValue> sections, Rule rule, List<List<KeyValue>> results, List<KeyValue> values) {
		if (sections.isEmpty()) {
			if (isRuleFollowed(rule, values)) {
				results.add(values);
			}
			return;
		}
		
		KeyValue section = sections.get(0);
		final int maxValue = Math.min(section.getMaxValue(), rule.getResultsEqual());
		
		for (int i=0; i<=maxValue; i++) {
			List<KeyValue> valuesHere = new ArrayList<>();
			valuesHere.addAll(values);
			valuesHere.add(new KeyValue(i, section.getMaxValue(), section.getKey()));
			
			List<KeyValue> otherSections = sections.stream().filter(e -> e != section).collect(Collectors.toList());
			
			getAllVariationsOfARule(otherSections, rule, results, valuesHere);
		}
	}

	private static boolean isRuleFollowed(Rule rule, Collection<KeyValue> values) {
		int actualResult = 0;
		
		for (KeyValue value : values) {
			Section section = (Section) value.getKey();
			
			if (rule.getSquares().containsAll(section.getGameSquares())) {
				actualResult += value.getValue();
			}
		}
		
		return actualResult == rule.getResultsEqual();
	}
	
	/**
	 * "multiply" a list.
	 */
	private static void combineLists(List<KeyValue> valueToAddToAllLists, List<List<KeyValue>> listToModify) {
		// Add all items to the list that are not already on the list
		listToModify.stream().forEach(l2 -> 
			l2.addAll(valueToAddToAllLists.stream().filter(e -> !l2.stream().anyMatch(e2 -> e2.getKey().equals(e.getKey()))).collect(Collectors.toList()))
		);
	}
	
	/**
	 * If any key in the knownValues list matches a key in the listToPopulate, sets the value of the 'listToPopulate' to the value found 
	 * 
	 * @param listToPopulate List to modify
	 * @param knownValues Get values from here
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
	
	private static boolean anyValueTooHigh(Collection<Rule> rules, Collection<KeyValue> values) {
		return rules.stream().anyMatch(rule -> getValueForSquaresInRule(values, rule) > rule.getResultsEqual());
	}
	
	private static boolean anyRulesBroken(Collection<Rule> rules, Collection<KeyValue> values) {
		return rules.stream().anyMatch(rule -> getValueForSquaresInRule(values, rule) != rule.getResultsEqual());
	}
	
	private static int getValueForSquaresInRule(Collection<KeyValue> values, Rule rule) {
		// Filter the values where the rule contains all the squares, then add the value for each
		return values.stream()
			.filter(e -> rule.getSquares().containsAll(((Section) e.getKey()).getGameSquares()))
			.collect(Collectors.summingInt(e -> e.getValue()));
	}
	
	private static List<List<KeyValue>> getValuesThatDontOverflow(Collection<List<KeyValue>> valuesToFilter, Collection<Rule> rules) {
		return valuesToFilter.stream().filter(e -> !anyValueTooHigh(rules, e)).collect(Collectors.toList());
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
}
