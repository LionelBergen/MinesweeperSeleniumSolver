package solver.board.analyzing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import solver.calculation.MathCombinationCalculator;
import solver.component.KeyValue;

public final class SolutionAnalyzer {
	private SolutionAnalyzer() { };
	
	/**
	 * 
	 * @param sumOfAllLists Sum of all objects must equal this
	 * @param objects
	 * @return
	 */
	public static List<List<KeyValue>> getAllPossibilities(final int sumOfAllLists, final List<KeyValue> objects) {
		if (objects.size() <= 1) {
			return Arrays.asList(objects);
		}
		
		// Each 'object's value is it's max value. Meaning the value should never be more than its max
		List<KeyValue> x = objects.stream().map(e -> transform(e, 0)).collect(Collectors.toList());
		
		Set<List<Integer>> allNumberCombinations = MathCombinationCalculator.getAllNumberCombinations(sumOfAllLists, objects.size());
		Set<List<Integer>> allNumberCombinationsInAllOrders = new HashSet<>();
		
		for (List<Integer> result : allNumberCombinations) {
			
			List<List<Integer>> allPossibilities = generatePerm(result);

			// "generatePerm" produces lots of duplicates. But since we use a Set, we'll lose all duplicates
			allNumberCombinationsInAllOrders.addAll(allPossibilities);
		}
		
		// remove empty lists
		allNumberCombinationsInAllOrders = allNumberCombinationsInAllOrders.stream().filter(e -> !e.isEmpty()).collect(Collectors.toSet());
		
		List<List<KeyValue>> transformedResults = new ArrayList<>();
		
		for (List<Integer> result : allNumberCombinationsInAllOrders) {
			List<KeyValue> transformedResult = transform(result, x);
			
			if (transformedResult != null) {
				transformedResults.add(transformedResult);
			}
		}
		
		return transformedResults;
	}
	
	private static List<KeyValue> transform(List<Integer> keys, List<KeyValue> values) {
		List<KeyValue> transformedResult = new ArrayList<>();
		for (int i=0; i<values.size(); i++) {
			int value = keys.get(i);
			
			if (value <= values.get(i).getMaxValue()) {
				transformedResult.add(new KeyValue(value, values.get(i).getKey()));
			}
			else {
				return null;
			}
		}
		
		return transformedResult;
	}
	
	private static <E> List<List<E>> generatePerm(List<E> original) {
		if (original.size() == 0) {
    		List<List<E>> result = new ArrayList<List<E>>(); 
    		result.add(new ArrayList<E>()); 
    		return result; 
    	}
		E firstElement = original.remove(0);
		List<List<E>> returnValue = new ArrayList<List<E>>();
		List<List<E>> permutations = generatePerm(original);
		for (List<E> smallerPermutated : permutations) {
			for (int index=0; index <= smallerPermutated.size(); index++) {
				List<E> temp = new ArrayList<E>(smallerPermutated);
				temp.add(index, firstElement);
				returnValue.add(temp);
			}
		}
    	return returnValue;
    }
	
	private static KeyValue transform(KeyValue original, int newValue) {
		return new KeyValue(newValue, original.getMaxValue(), original.getKey());
	}
}
