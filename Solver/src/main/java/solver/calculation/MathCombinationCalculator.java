package solver.calculation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class MathCombinationCalculator {
	
	private MathCombinationCalculator() { }
	
	public static Set<List<Integer>> getAllNumberCombinations(int valueToAddUpTo, int numberOfDigits) {
		// Use a set to get rid of duplicates
		Set<List<Integer>> results = new HashSet<>();
		
		// Get a list of numbers from 1 - valueToAddUpTo
		List<Integer> numbers = new ArrayList<>();
		for (int i=1; i<valueToAddUpTo + 1; i++) {
			numbers.add(i);
		}
		
		// Get a list of all combinations adding up to our target
		results = getSummingSubsets(numbers, valueToAddUpTo);
		
		// Pad the smaller lists with 0's, remove the larget lists, sort every list (doesn't sort outer set)
		results = results.stream()
			.filter(e -> e.size() <= numberOfDigits)
			.map(e -> addZerosToList(e, numberOfDigits))
			.map(e -> sortList(e))
			.collect(Collectors.toSet());
		
		return results;
	}
	
	/**
	 * If the list passed is shorter than the 'newSize', add 0's to the list until the new size is reached
	 */
	private static List<Integer> addZerosToList(List<Integer> values, int newSize) {
		while (values.size() < newSize) {
			values.add(0);
		}
		
		return values;
	}
	
	private static List<Integer> sortList(List<Integer> values) {
		return values.stream().sorted((e1, e2) -> Integer.compare(e2, e1)).collect(Collectors.toList());
	}
	
	/**
	 * Given a list of numbers, returns a Set containing all possible combinations which sum equals the target
	 * 
	 * Example: 
	 * input   {1, 2}, 3 
	 * output: {{1, 1, 1}, {1, 2}, {3}}
	 * 
	 * @param numbers Numbers to include in combinations
	 * @param target Sum of every list will equal this
	 * @return Every list with a sum equal to the target
	 */
	private static Set<List<Integer>> getSummingSubsets(List<Integer> numbers, int target) {
		Set<List<Integer>> results = new HashSet<>();
	    List<List<Integer>> working = new ArrayList<List<Integer>>();
	    working.add(new ArrayList<Integer>());
	    
	    while (working.size() > 0) {
	    	List<List<Integer>> nextWork = new ArrayList<>();
	        for (int i = 0; i < working.size(); i++) {
	            for (int j = 0; j < numbers.size(); j++){
	            	List<Integer> subset = new ArrayList<Integer>(working.get(i));
	            	subset.add(numbers.get(j));
	            	
	                int sum = subset.stream().reduce((a,b) -> a + b).get();
	                if (sum <= target){
	                	if (sum == target) {
	                		results.add(subset);
	                	}
	                	else {
	                		nextWork.add(subset);
	                	}
	                }
	            }
	        }
	        working = nextWork;
	    }
	    
	    return results;
	}
}
