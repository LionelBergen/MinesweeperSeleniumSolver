package solver.calculation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import solver.component.AssignedValue;
import utility.util.MathUtil;

public class OddsCalculator {
	private static class WeightWithCases {
		BigDecimal weight;
		long numberOfCases;
		List<AssignedValue> cases;
		
		public WeightWithCases(BigDecimal weight, long numberOfCases, List<AssignedValue> cases) {
			this.weight = weight;
			this.numberOfCases = numberOfCases;
			this.cases = cases;
		}
	}
	
	private static class WeightHandler {
		final int totalMines;
		final BigDecimal numberOfSquaresBeingIdentified;
		// calculation, #of mines
		final Map<Integer, BigDecimal> weightCalculations = new HashMap<>();
		final List<WeightWithCases> weights = new ArrayList<>();
		
		public WeightHandler(int totalMines, BigDecimal numberOfSquaresBeingIdentified) {
			this.totalMines = totalMines;
			this.numberOfSquaresBeingIdentified = numberOfSquaresBeingIdentified;
		}

		public void add(Integer numberOfMines, Long numberOfCases, List<AssignedValue> recordsForCase) {
			if (weightCalculations.get(numberOfMines) == null) {
				BigDecimal a = BigDecimal.valueOf(totalMines - numberOfMines);
				BigDecimal weight = calculateWeight(a, numberOfSquaresBeingIdentified);
				weightCalculations.put(numberOfMines, weight);
				weights.add(new WeightWithCases(weight, numberOfCases, recordsForCase));
			}
			else {
				// reuse calculation
				BigDecimal weight = weightCalculations.get(numberOfMines);
				weights.add(new WeightWithCases(weight, numberOfCases, recordsForCase));
			}
		}
		
		public void setSumOfAllWeights() {
			BigDecimal sumOfAllWeights = BigDecimal.ZERO;
			
			for (WeightWithCases cases : weights) {
				sumOfAllWeights = sumOfAllWeights.add(cases.weight.multiply(BigDecimal.valueOf(cases.numberOfCases)));
			}
			
			for (WeightWithCases cases : weights) {
				cases.weight = MathUtil.divide(cases.weight, sumOfAllWeights);
			}
		}
		
		public List<WeightWithCases> keys() {
			return weights;
		}
	}
	
	public static Map<Object, BigDecimal> calculateOdds(final List<List<AssignedValue>> records, final int totalMines, final int totalUnidentifiedSquares) {
		final Map<Object, BigDecimal> results = new HashMap<>();
		
		// All records are of the same length & contain the exact same squares
		for (AssignedValue section : records.get(0)) {
			// strip the value, maxvalue so we can reference it using any
			results.put(section.getKey(), BigDecimal.ZERO);
		}
		final int numberOfUnidentifiedSquaresInSection = records.get(0).stream().collect(Collectors.summingInt(e -> e.getMaxValue()));
		final BigDecimal numberOfSquaresBeingIdentified = BigDecimal.valueOf(totalUnidentifiedSquares - numberOfUnidentifiedSquaresInSection);
		
		final WeightHandler weightHandler = new WeightHandler(totalMines, numberOfSquaresBeingIdentified);
		
		for (List<AssignedValue> record : records) {
			final Integer numberOfMines = record.stream().map(e -> e.getValue()).collect(Collectors.summingInt(Integer::intValue));
			final Long numberOfCases = calculateNumberOfCases(record);
			
			weightHandler.add(numberOfMines, numberOfCases, record);
		}
		
		weightHandler.setSumOfAllWeights();
		
		for (WeightWithCases y : weightHandler.keys()) {
			BigDecimal weightForCase = MathUtil.multiply(y.weight, BigDecimal.valueOf(y.numberOfCases));
			System.out.println("weight: " + weightForCase + " # of cases: " + y.numberOfCases);
			for (AssignedValue section : y.cases) {
				if (section.getValue() > 0) {
					BigDecimal weightPerSquare = BigDecimal.valueOf((double) section.getValue() / section.getMaxValue());
					BigDecimal weightCalculation = MathUtil.multiply(weightPerSquare, weightForCase);
					
					BigDecimal newValue = results.get(section.getKey()).add(weightCalculation);
					
					results.put(section.getKey(), newValue);
				}
			}
		}
		
		return results;
	}
	
	private static BigDecimal calculateWeight(BigDecimal a, BigDecimal b) {
		return MathUtil.binomial(b, a);
	}
	
	private static Long calculateNumberOfCases(List<AssignedValue> values) {
		return values.stream().map(e -> MathUtil.nCr(e.getMaxValue(), e.getValue()))
				.reduce(1L, (a, b) -> a * b);
	}
}
