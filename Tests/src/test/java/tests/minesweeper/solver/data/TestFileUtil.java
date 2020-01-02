package tests.minesweeper.solver.data;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestFileUtil {
	private TestFileUtil() { }
	
	private static final String DATA_DIRECTORY = "tests/minesweeper/solver/data/";
	private static final ClassLoader CLASS_LOADER = new TestFileUtil().getClass().getClassLoader();

	// TODO: issue with running full suite vs running single JUnit test. I think we can copy the files to a dist directory on build
	public static File getFile(String fileName) {
		URL resource = CLASS_LOADER.getResource(fileName);
		
		if (resource != null) {
			return new File(resource.getFile());
		} else {
			return new File(CLASS_LOADER.getResource(DATA_DIRECTORY + fileName).getFile());
		}
	}
	
	public static JSONObject getJsonFromFile(String fileName) throws IOException, ParseException {
		File file = TestFileUtil.getFile(fileName);
		String fileContents = Files.readString(Paths.get(file.getPath()), StandardCharsets.US_ASCII);
		
		JSONParser parser = new JSONParser();
		return (JSONObject) parser.parse(fileContents);
	}
}
