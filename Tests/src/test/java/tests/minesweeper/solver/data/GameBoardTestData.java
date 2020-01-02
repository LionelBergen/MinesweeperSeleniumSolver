package tests.minesweeper.solver.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import component.model.RegularGameBoard;
import component.model.Section;
import component.model.gamesquare.GameSquare;
import component.model.gamesquare.SquareValue;
import tests.minesweeper.data.component.GameBoardTestScenario;

public class GameBoardTestData {
	private static final String SPECIAL_SENARIO_02_FILE_NAME = "SpecialScenario02.json";
	private static final String SPECIAL_SENARIO_03_FILE_NAME = "SpecialScenario03.json";
	
	/**
	 * A Game board containing 2 flags, and a full large square of numbers
	 */
	public static final GameBoardTestScenario SPECIAL_SCENARIO_02 = getTestScenario(SPECIAL_SENARIO_02_FILE_NAME);
	
	/**
	 * Described here: https://math.stackexchange.com/questions/3466402/calculating-minesweeper-odds-is-this-calculation-correct
	 */
	public static final GameBoardTestScenario SPECIAL_SCENARIO_03 = getTestScenario(SPECIAL_SENARIO_03_FILE_NAME);
	
	private static GameBoardTestScenario getTestScenario(String fileName) {
		try {
			JSONObject result = TestFileUtil.getJsonFromFile(fileName);
			
			int numberOfMines = Integer.parseInt(result.get("mines").toString());
			List<GameSquare> gameSquaresFromResults = new ArrayList<GameSquare>();
			
			JSONArray listOfAllSquares = (JSONArray) result.get("squares");
			Map<String, List<GameSquare>> sections = new HashMap<>();
			
			for (Iterator<?> iter = listOfAllSquares.iterator(); iter.hasNext();) {
				JSONObject gameSquare = (JSONObject) iter.next();
				SquareValue squareValue = SquareValue.BLANK_UNTOUCHED;
				
				String type = "";
				if (gameSquare.get("type") != null) {
					type = gameSquare.get("type").toString();
					
					if (type.contains("flag")) {
						squareValue = SquareValue.FLAGGED;
					}
				}
				String name = gameSquare.get("name").toString();
				if (squareValue != null && isSingleDigitNumber(name)) {
					squareValue = SquareValue.fromNumber(name);
				}
				
				int x = Integer.parseInt(gameSquare.get("x").toString());
				int y = Integer.parseInt(gameSquare.get("y").toString());
				String sectionGroupBy = (String) gameSquare.get("colour");
				
				GameSquare gameSquareResult = new GameSquare(name, squareValue, x, y);
				gameSquaresFromResults.add(gameSquareResult);
				
				if (!name.isEmpty() && !isSingleDigitNumber(name) && sectionGroupBy != null) {
					List<GameSquare> squaresInSection = sections.get(sectionGroupBy);
					if (squaresInSection == null) {
						squaresInSection = new ArrayList<GameSquare>();
					}
					
					squaresInSection.add(gameSquareResult);
					sections.put(sectionGroupBy, squaresInSection);
				}
			}
			
			RegularGameBoard gameBoard = new RegularGameBoard(gameSquaresFromResults, numberOfMines);
			
			return createTestScenario(gameBoard, sections.values());
		} catch(Exception e ) {
			throw new RuntimeException(e);
		}
	}
	
	private static GameBoardTestScenario createTestScenario(RegularGameBoard gameBoard, Collection<List<GameSquare>> gameSquaresList) {
		List<Section> sections = new ArrayList<Section>();
		
		for (List<GameSquare> squares : gameSquaresList) {
			sections.add(new Section(new HashSet<GameSquare>(squares)));
		}
		
		return new GameBoardTestScenario(gameBoard, sections);
	}
	
	private static boolean isSingleDigitNumber(String text) {
		return text != null && !text.isEmpty() && Character.isDigit(text.charAt(0));
	}
}
