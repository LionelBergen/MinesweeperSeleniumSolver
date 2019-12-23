package tests.minesweeper.solver.data;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import component.model.RegularGameBoard;
import component.model.gamesquare.GameSquare;
import component.model.gamesquare.SquareValue;

public class GameBoardTestData {
	private static final String DATA_DIRECTORY = "tests/minesweeper/solver/data/";
	
	private static final String SPECIAL_SENARIO_03_FILE_NAME = "SpecialScenario03.json";
	private static final ClassLoader CLASS_LOADER = new GameBoardTestData().getClass().getClassLoader();;
	
	public static void main(String[] args) throws Exception {
		getTestScenario(SPECIAL_SENARIO_03_FILE_NAME);
	}
	
	private static RegularGameBoard getTestScenario(String fileName) throws Exception {
		File file = getFile(SPECIAL_SENARIO_03_FILE_NAME);
		String fileContents = Files.readString(Paths.get(file.getPath()), StandardCharsets.US_ASCII);
		
		JSONParser parser = new JSONParser();
		JSONObject result = (JSONObject) parser.parse(fileContents);
		
		int numberOfMines = Integer.parseInt(result.get("mines").toString());
		List<GameSquare> gameSquaresFromResults = new ArrayList<GameSquare>();
		
		JSONArray listOfAllSquares = (JSONArray) result.get("squares");
		
		for (Iterator<?> iter = listOfAllSquares.iterator(); iter.hasNext();) {
			JSONObject gameSquare = (JSONObject) iter.next();
			
			String name = gameSquare.get("name").toString();
			SquareValue squareValue = isSingleDigitNumber(name) ? SquareValue.fromValue(name) : SquareValue.BLANK_UNTOUCHED;
			int x = Integer.parseInt(gameSquare.get("x").toString());
			int y = Integer.parseInt(gameSquare.get("y").toString());
			
			gameSquaresFromResults.add(new GameSquare(name, squareValue, x, y));
		}
		
		System.out.println(numberOfMines);
		System.out.println(gameSquaresFromResults);
		
		RegularGameBoard gameBoard = new RegularGameBoard(gameSquaresFromResults, numberOfMines);
		
		return gameBoard;
	}
	
	private static boolean isSingleDigitNumber(String text) {
		return text != null && !text.isEmpty() && Character.isDigit(text.charAt(0));
	}
	
	private static File getFile(String fileName) {
		return new File(CLASS_LOADER.getResource(DATA_DIRECTORY + fileName).getFile());
	}
}
