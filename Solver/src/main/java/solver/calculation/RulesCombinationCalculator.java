package solver.calculation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import component.model.Section;
import solver.component.AssignedValue;
import solver.component.Rule;

import static solver.component.transformer.SectionTransformer.transformSectionsToKeyValues;

public class RulesCombinationCalculator {
	private static final int UNKNOWN_VALUE = 0;
	
	public static List<List<AssignedValue>> getAllVariations(Collection<Section> sections, Collection<Rule> rules) {
		// start by getting the first Rule & sections relating to it
		final Rule ruleToProcess = rules.iterator().next();
		List<Rule> rulesLeftToProcess = rules.stream().filter(e -> e != ruleToProcess).collect(Collectors.toList());
		List<Section> sectionRelatingToRule = getSectionsInRule(sections, ruleToProcess);
		
		// Get all possible combinations given the rule & sections
		List<List<AssignedValue>> allKnownValues = getAllVariationsOfARule(sectionRelatingToRule, ruleToProcess);
		
		// filter any items that are invalid given all rules
		allKnownValues = getValuesThatDontOverflow(allKnownValues, rules);
		
		for (Rule nextRule : rulesLeftToProcess) {
			Set<List<AssignedValue>> allCombinationsOfRule = new HashSet<>();
			
			for (List<AssignedValue> knownValues : allKnownValues) {
				allCombinationsOfRule.addAll(getAllCombinationsForRule(sections, rules, knownValues, nextRule));
			}
			allKnownValues = new ArrayList<>(allCombinationsOfRule);
		}
		
		// filter items with broken rules
		allKnownValues = allKnownValues.stream().filter(e -> !anyRulesBroken(rules, e)).collect(Collectors.toList());
		
		return allKnownValues;
	}
	
	/**
	 * Given a Section and rule, return all possible combinations that do not break the rule
	 * 
	 * @param sectionsRelatingToRule All sections the rule relates to
	 * @param rule Rule which cannot be broken
	 * @return All variations of values for the arguments
	 */
	public static List<List<AssignedValue>> getAllVariationsOfARule(Collection<Section> sectionsRelatingToRule, Rule rule) {
		List<AssignedValue> sectionsTransformed = transformSectionsToKeyValues(sectionsRelatingToRule, UNKNOWN_VALUE);
		return getAllVariationsOfARuleWithKnownValues(sectionsTransformed, rule);
	}
	
	public static List<List<AssignedValue>> getAllVariationsOfARuleWithKnownValues(Collection<AssignedValue> sectionsRelatingToRule, Rule rule) {
		List<List<AssignedValue>> results = new ArrayList<>();
		
		// No need to proces values we already know the values of
		List<AssignedValue> valuesWithKnown = sectionsRelatingToRule.stream().filter(e -> e.getValue() != UNKNOWN_VALUE).collect(Collectors.toList());
		sectionsRelatingToRule.removeAll(valuesWithKnown);
		
		// populate results with all variations of rule
		getAllVariationsOfARule(sectionsRelatingToRule, rule, results, valuesWithKnown);
		
		return results;
	}
	
	private static Collection<List<AssignedValue>> getAllCombinationsForRule(Collection<Section> allSections, Collection<Rule> allRules, Collection<AssignedValue> knownValues, Rule rule) {
		List<Section> sectionRelatingToRule = getSectionsInRule(allSections, rule);
		List<AssignedValue> sectionsTransformed = transformSectionsToKeyValues(sectionRelatingToRule, UNKNOWN_VALUE);
		populateListWithKnown(sectionsTransformed, knownValues);
		
		List<List<AssignedValue>> allValuesForRule = getAllVariationsOfARuleWithKnownValues(sectionsTransformed, rule);
		allValuesForRule = getValuesThatDontOverflow(allValuesForRule, allRules);
		
		// Add all known values to the list
		combineLists(knownValues, allValuesForRule);
		
		return allValuesForRule;
	}
	
	private static void getAllVariationsOfARule(Collection<AssignedValue> sections, Rule rule, Collection<List<AssignedValue>> results, List<AssignedValue> knownValues) {
		if (sections.isEmpty()) {
			if (isRuleFollowed(rule, knownValues)) {
				results.add(knownValues);
			}
		}
		else
		{
			// Get a section from the list
			AssignedValue section = sections.iterator().next();
			
			// We know the value cannot be higher than the max for the Section or the rule
			final int maxValue = Math.min(section.getMaxValue(), rule.getResultsEqual());
			
			// Add a list for every value from 0-max
			for (int i=0; i<=maxValue; i++) {
				// instantiate a new list. Doesn't matter that the list has the same object pointers we just need a new List object.
				List<AssignedValue> newKnownValues = new ArrayList<>(knownValues);
				
				newKnownValues.add(new AssignedValue(i, section.getMaxValue(), section.getKey()));

				// process other sections
				List<AssignedValue> otherSections = sections.stream().filter(e -> e != section).collect(Collectors.toList());
				getAllVariationsOfARule(otherSections, rule, results, newKnownValues);
			}
		}
	}

	private static boolean isRuleFollowed(Rule rule, Collection<AssignedValue> values) {
		int actualResult = getValueForSquaresInRule(values, rule);

		return actualResult == rule.getResultsEqual();
	}
	
	/**
	 * "multiply" a list.
	 */
	private static void combineLists(Collection<AssignedValue> valueToAddToAllLists, Collection<List<AssignedValue>> listToModify) {
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
	private static void populateListWithKnown(Collection<AssignedValue> listToPopulate, Collection<AssignedValue> knownValues) {
		for (AssignedValue value : listToPopulate) {
			for (AssignedValue knownValue : knownValues) {
				if (knownValue.getKey().equals(value.getKey())) {
					value.setValue(knownValue.getValue());
				}
			}
		}
	}
	
	private static boolean anyValueTooHigh(Collection<Rule> rules, Collection<AssignedValue> values) {
		return rules.stream().anyMatch(rule -> getValueForSquaresInRule(values, rule) > rule.getResultsEqual());
	}
	
	private static boolean anyRulesBroken(Collection<Rule> rules, Collection<AssignedValue> values) {
		return rules.stream().anyMatch(rule -> getValueForSquaresInRule(values, rule) != rule.getResultsEqual());
	}
	
	private static int getValueForSquaresInRule(Collection<AssignedValue> values, Rule rule) {
		// Filter the values where the rule contains all the squares, then add the value for each
		return values.stream()
			.filter(e -> rule.getSquares().containsAll(((Section) e.getKey()).getGameSquares()))
			.collect(Collectors.summingInt(e -> e.getValue()));
	}
	
	private static List<List<AssignedValue>> getValuesThatDontOverflow(Collection<List<AssignedValue>> valuesToFilter, Collection<Rule> rules) {
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
