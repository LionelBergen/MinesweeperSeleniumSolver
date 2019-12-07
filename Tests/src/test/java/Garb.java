import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import solver.board.analyzing.BoardAnalyzer;
import solver.board.analyzing.SectionAnalyzer;
import solver.calculation.board.MineOddsCalculator;
import solver.component.KeyValue;
import solver.component.Rule;
import solver.component.section.Section;
import tests.minesweeper.data.GameBoardTestScenarios;
import tests.minesweeper.data.component.GameBoardTestScenario;
import utility.util.MathUtil;

public class Garb {
	
	@SuppressWarnings("unchecked")
	public static void main(String [] args) {
		final Map<Integer, BigDecimal> mineToWeight = new HashMap<>();
		mineToWeight.put(Integer.valueOf(5), BigDecimal.valueOf(0.00287497486));
		mineToWeight.put(Integer.valueOf(6), BigDecimal.valueOf(0.00072784173));
		mineToWeight.put(Integer.valueOf(7), BigDecimal.valueOf(0.00017286241));
		mineToWeight.put(Integer.valueOf(8), BigDecimal.valueOf(0.00003841386));
		
		GameBoardTestScenario test = GameBoardTestScenarios.SCENARIO_SPECIAL_03;
		
		List<Section> sections = (List<Section>) BoardAnalyzer.breakupBoard(test.getGameBoard());
		
		
		Section section1 = sections.get(0);
		Section section2 = sections.get(1);
		Set<List<KeyValue>> resultsLeftSide = new HashSet<>();
		Set<List<KeyValue>> results2 = new HashSet<>();
		Set<List<KeyValue>> resultsComplete = new HashSet<>();
				
		List<Rule> rules = SectionAnalyzer.breakupSectionIntoRules(section1);
		Collection<Section> subSections = SectionAnalyzer.getSections(rules, section1.getGameSquares());
		resultsLeftSide = MineOddsCalculator.calculateAllPossibilities(rules, subSections);
		
		rules = SectionAnalyzer.breakupSectionIntoRules(section2);
		subSections = SectionAnalyzer.getSections(rules, section2.getGameSquares());
		results2 = MineOddsCalculator.calculateAllPossibilities(rules, subSections);
		
		for (List<KeyValue> x : resultsLeftSide) {
			for (List<KeyValue> y : results2) {
				List<KeyValue> fullResult = new ArrayList<KeyValue>();
				fullResult.addAll(x);
				fullResult.addAll(y);
				resultsComplete.add(fullResult);
			}
		}
		
		/*(C)           = 1
			    (D+E+H+L)     = 0
			    (K)           = 0
			    (G)           = 0
			    (J)           = 1
			    (M+N+O)       = 0
			    (A+F+I+B)     = 1
			    (R+X+W+Q)     = 1
			    (S+U+Y)       = 0
			    (P+T+V)       = 1*/
			    
		System.out.println("S#   Mine Count  # of cases   weight "     + "          (C)          (DEHL)       (K)          (G)          (J)          (MNO)        (AFIB)      (RXWQ)      (SUY)         (PTV)"); 
		System.out.println("---" + "  ---------- " + " ----------   -------------    ----------   ----------   ----------   ----------   ----------   ----------   ----------   ----------   ----------   ----------"); 
		BigDecimal totalTotal = BigDecimal.ZERO;
		List<BigDecimal> totals = new ArrayList<BigDecimal>();
		for (int i=0; i < 10; i++) {
			totals.add(BigDecimal.ZERO);
		}
		
		int i = 1;
		for (List<KeyValue> x : resultsComplete) {
			int numberOfMines = x.stream().map(e -> e.getValue()).collect(Collectors.summingInt(Integer::intValue));
			Long numberOfCases = x.stream().map(e -> MathUtil.nCr(e.getMaxValue(), e.getValue()))
					.reduce(1L, (a, b) -> a * b);
			
			BigDecimal mineWeight = mineToWeight.get(Integer.valueOf(numberOfMines));
			BigDecimal sectionWeight = mineWeight.multiply(BigDecimal.valueOf(numberOfCases));
			BigDecimal weight1 = calculateWeight("C", x, sectionWeight);
			BigDecimal weight2 = calculateWeight("D", x, sectionWeight);
			BigDecimal weight3 = calculateWeight("K", x, sectionWeight);
			BigDecimal weight4 = calculateWeight("G", x, sectionWeight);
			BigDecimal weight5 = calculateWeight("J", x, sectionWeight);
			BigDecimal weight6 = calculateWeight("M", x, sectionWeight);
			BigDecimal weight7 = calculateWeight("A", x, sectionWeight);
			BigDecimal weight8 = calculateWeight("R", x, sectionWeight);
			BigDecimal weight9 = calculateWeight("S", x, sectionWeight);
			BigDecimal weight10 = calculateWeight("P", x, sectionWeight);
		
			System.out.print(String.format("%-4d %-11s %-12s %-16s %.8f   %.8f   %.8f   %.8f   %.8f   %.8f   %.8f   %.8f   %.8f   %.8f", i++, numberOfMines, numberOfCases, sectionWeight, weight1, weight2, weight3, weight4, weight5, weight6, weight7, weight8, weight9, weight10));
			System.out.print("\n");
			
			totals.set(0, totals.get(0).add(weight1));
			totals.set(1, totals.get(1).add(weight2));
			totals.set(2, totals.get(2).add(weight3));
			totals.set(3, totals.get(3).add(weight4));
			totals.set(4, totals.get(4).add(weight5));
			totals.set(5, totals.get(5).add(weight6));
			totals.set(6, totals.get(6).add(weight7));
			totals.set(7, totals.get(7).add(weight8));
			totals.set(8, totals.get(8).add(weight9));
			totals.set(9, totals.get(9).add(weight10));
			
			totalTotal = totalTotal.add(sectionWeight);
		}
		
		System.out.println(String.format("Totals:                          %.8f    %.8f   %.8f   %.8f   %.8f   %.8f   %.8f   %.8f   %.8f   %.8f   %.8f", 
				totalTotal, totals.get(0), totals.get(1), totals.get(2), totals.get(3), totals.get(4), totals.get(5), totals.get(6), totals.get(7), totals.get(8), totals.get(9)));
	}
	
	private static BigDecimal calculateWeight(String needle, List<KeyValue> hayStack, BigDecimal mineAmountWeight) {
		KeyValue keyValue = hayStack.stream().filter(e -> ((Section) e.getKey()).getGameSquares().stream().anyMatch(g -> g.getName().equals(needle))).findAny().get();
		
		if (keyValue.getValue() == 0) {
			return BigDecimal.ZERO;
		}
		
		double val = (double) keyValue.getValue() / keyValue.getMaxValue();
		BigDecimal calc = BigDecimal.valueOf(val);
		
		
		return calc.multiply(mineAmountWeight);
	}
	
}