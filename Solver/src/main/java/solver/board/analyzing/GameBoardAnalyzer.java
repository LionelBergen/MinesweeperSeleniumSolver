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
	private int totalMines;
	private int totalUnidentifiedSquares;
	Map<Section, BigDecimal> results;

	// TODO: totalMines, totalUnidentifiedSquares should be on GameBaord
	public static Map<Section, BigDecimal> calculateOddsForEverySection(GameBoard gameBoard, int totalMines, int totalUnidentifiedSquares) {
		GameBoardAnalyzer gameBoardAnalyzer = new GameBoardAnalyzer();
		gameBoardAnalyzer.gameBoard = gameBoard;
		gameBoardAnalyzer.totalMines = totalMines;
		gameBoardAnalyzer.totalUnidentifiedSquares = totalUnidentifiedSquares;
		
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
		this.results = OddsCalculator.calculateOdds(this.allPossibleCombinations, this.totalMines, this.totalUnidentifiedSquares);
		
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
