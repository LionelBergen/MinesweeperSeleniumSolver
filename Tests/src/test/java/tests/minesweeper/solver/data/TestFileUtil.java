package tests.minesweeper.solver.data;

import java.io.File;
import java.net.URL;

public class TestFileUtil {
	private TestFileUtil() { }
	
	private static final String DATA_DIRECTORY = "tests/minesweeper/solver/data/";
	private static final ClassLoader CLASS_LOADER = new GameBoardTestData().getClass().getClassLoader();

	// TODO: issue with running full suite vs running single JUnit test. I think we can copy the files to a dist directory on build
	public static File getFile(String fileName) {
		URL resource = CLASS_LOADER.getResource(fileName);
		
		if (resource != null) {
			return new File(resource.getFile());
		} else {
			return new File(CLASS_LOADER.getResource(DATA_DIRECTORY + fileName).getFile());
		}
	}
}
