package tests.minesweeper.data.creator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

/**
 * Creates HTMl, CSS, JS files used to create test scenerios.
 * 
 * These scenerios can be used for visualizing, or building automated test cases
 *  
 * @author Lionel Bergen
 */
public class GameBoardTestScenerioCreator {
	private static final String TEMP_DIRECTORY = System.getProperty("java.io.tmpdir");
	private static final String OUTPUT_DIRECTORY = TEMP_DIRECTORY + "minesweeper-outputfiles" + File.separator;
	
	public static void main(String[] args) throws Exception {
		final File outputDirectory = new File(OUTPUT_DIRECTORY);
		deleteFolder(outputDirectory);
		System.out.println("Deleted: " + outputDirectory);
		
		//File htmlFile = System.getd
		System.out.println(getFileContentsFromAssets("minesweeper.html"));
	}
	
	private static void deleteFolder(File folder) throws IOException {
		String[] entries = folder.list();
		
		if (entries != null) {
			for(String s: entries){
			    File currentFile = new File(folder.getPath(), s);
			    currentFile.delete();
			}
		}
	}
	
	private static String getFileContentsFromAssets(String fileName) throws IOException {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		
		return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
	}
}
