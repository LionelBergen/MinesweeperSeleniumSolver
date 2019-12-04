package tests.minesweeper.data.creator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
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
		
		// Delete the folder
		deleteFolder(outputDirectory);
		System.out.println("Deleted: " + outputDirectory);
		
		String htmlFileContents =  getFileContentsFromAssets("minesweeper.html");
		htmlFileContents = htmlFileContents.replace("${title}", "test122");
		
		createFile(outputDirectory, "minesweeper.html", htmlFileContents);
		createFile(outputDirectory, "minesweeper.css", getFileContentsFromAssets("minesweeper.css"));
		
		// Print link to the HTML file we created
		System.out.println("Created: " + OUTPUT_DIRECTORY + "minesweeper.html");
	}
	
	private static void createFile(File folder, String fileName, String contents) throws IOException {
		File newFile = new File(folder + File.separator + fileName);
		FileUtils.writeStringToFile(newFile, contents, StandardCharsets.UTF_8);
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
