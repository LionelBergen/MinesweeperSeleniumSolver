package solver.board.analyzing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import solver.component.KeyValue;

public final class SolutionAnalyzer {
	public static final SolutionAnalyzer SOLUTION_ANALYZER = new SolutionAnalyzer();
	
	private class KeyValueWithMax extends KeyValue {
		private int maxValue = 0;
		
		public KeyValueWithMax(int maxValue, int value, Object key) {
			super(value, key);
			this.maxValue = maxValue;
		}

		public int getMaxValue() {
			return maxValue;
		}

		public void setMaxValue(int maxValue) {
			this.maxValue = maxValue;
		}
	}
	
	private SolutionAnalyzer() { };
	
	public List<List<KeyValue>> getAllPossibilities(int maxValue, List<KeyValue> objects) {
		if (objects.size() <= 1) {
			return Arrays.asList(objects);
		}
		
		List<List<Integer>> combinations = new ArrayList<>();
		
		Iterator<KeyValue> objectsIter = objects.iterator();
		
		for (int i = maxValue; i > 0; i--) {
			int other = 1;
			
			List<Integer> resultList = new ArrayList<>();
			resultList.add(i);
			
			while (other-- > maxValue - i) {
				resultList.add(other);
			}
			
			while (resultList.size() < objects.size()) {
				resultList.add(0);
			}
			
			List<List<Integer>> allPossibilities = generatePerm(resultList);
			System.out.println(allPossibilities.stream().map(e -> e.toString()).collect(Collectors.joining("\n")));
			//System.out.println(resultList);
		}
		
		return null;
	}
	
	public <E> List<List<E>> generatePerm(List<E> original) {
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
	
	private KeyValueWithMax transform(KeyValue original, int newValue) {
		return new KeyValueWithMax(original.getValue(), newValue, original.getKey());
	}
	
	private List<KeyValue> createCopy(List<KeyValue> original) {
		List<KeyValue> result = new ArrayList<>();
		
		for (KeyValue value : original) {
			result.add(new KeyValue(value.getValue(), value.getKey()));
		}
		
		return result;
	}
}
