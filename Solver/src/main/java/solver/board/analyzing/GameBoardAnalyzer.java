package solver.board.analyzing;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import component.model.GameBoard;
import component.model.Section;
import solver.calculation.OddsCalculator;
import solver.calculation.RulesCombinationCalculator;
import solver.component.AssignedValue;
import solver.component.Rule;

/**
 * Contains a single static method used to calculate the possibility for each square in a game board having a mine
 * Example usage: To get the square with the least odds of having a mine, {@link #getOddsForEverySquare(GameBoard, int, int)} then sort by value
 * 
 * @author Lionel Bergen
 */
public class GameBoardAnalyzer {
	private List<Section> fullSections;
	private List<Rule> rules;
	private Collection<Section> sections;
	private GameBoard gameBoard;
	private List<List<AssignedValue>> allPossibleCombinations;
	Map<Section, BigDecimal> results;

	public static Map<Section, BigDecimal> calculateOddsForEverySection(GameBoard gameBoard) {
		GameBoardAnalyzer gameBoardAnalyzer = new GameBoardAnalyzer();
		gameBoardAnalyzer.gameBoard = gameBoard;
		
		gameBoardAnalyzer.breakupBoard(gameBoard)
			.createRules()
			.splitIntoSections()
			.calculateAllVariations()
			.calculateOdds();

		return gameBoardAnalyzer.results;
	}
	
	private GameBoardAnalyzer() { };
	
	private GameBoardAnalyzer calculateOdds() {
		// TODO: totalMines, totalUnidentifiedSquares should be on GameBaord
		this.results = OddsCalculator.calculateOdds(this.allPossibleCombinations, this.gameBoard.getTotalUnidentifiedMines(), this.gameBoard.getNumberOfUnidentifiedSquares());
		
		return this;
	}
	
	private GameBoardAnalyzer calculateAllVariations() {
		this.allPossibleCombinations = RulesCombinationCalculator.getAllVariations(sections, rules);
		
		return this;
	}
	
	private GameBoardAnalyzer splitIntoSections() {
		// TODO: sections as 2nd argument?
		sections = SectionAnalyzer.getSections(this.rules, this.gameBoard.getAllBlankSquares());
		
		return this;
	}
	
	private GameBoardAnalyzer createRules() {
		this.rules = SectionAnalyzer.breakupSectionIntoRules(this.fullSections);
		
		return this;
	}
	
	private GameBoardAnalyzer breakupBoard(GameBoard gameBoard) {
		this.fullSections = BoardAnalyzer.breakupBoard(gameBoard);
		
		return this;
	}
}
