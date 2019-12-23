package tests.minesweeper.solver.data;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GameBoardTestData {
	private static final String DATA_DIRECTORY = "tests/minesweeper/solver/data/";
	
	private static final String SPECIAL_SENARIO_03_FILE_NAME = "SpecialScenario03.json";
	private static final ClassLoader CLASS_LOADER = new GameBoardTestData().getClass().getClassLoader();;
	
	public static void main(String[] args) throws Exception {
		
		File file = getFile(SPECIAL_SENARIO_03_FILE_NAME);
		String fileContents = Files.readString(Paths.get(file.getPath()), StandardCharsets.US_ASCII);
		
		JSONParser parser = new JSONParser();
		JSONObject result = (JSONObject) parser.parse(fileContents);
		
		//JSONArray jsonArray = 
		System.out.println(result.get("mines"));
		System.out.println(result);
	}
	
	private static File getFile(String fileName) {
		return new File(CLASS_LOADER.getResource(DATA_DIRECTORY + fileName).getFile());
	}
}
