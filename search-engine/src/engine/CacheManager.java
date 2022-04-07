package engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.io.FileUtils;

import lib.In;

public class CacheManager {

	/**
	 * 
	 * In this function, clear the cache file and delete the directory which
	 * contains the text files
	 * 
	 * 
	 * @throws IOException
	 */
	public static void deleteCache() throws IOException {

		// Get the cache file
		File cacheFile = new File("src/Cache.txt");

		// Clear the cache file
		if (cacheFile.exists()) {
			// File output stream
			FileOutputStream fos = new FileOutputStream(cacheFile);

			// Initialize the print writer
			PrintWriter pw = new PrintWriter(fos);

			// write all the text to the file
			pw.write("");
			pw.close();
			System.out.println("The file is deleted successfully! ");
		} else {
			System.out.println("File doesn't exist");
		}

		// Delete the directory
		File textDir = new File("src/WebPagesInText/");

		// check if directory exists then delete it
		if (textDir.exists()) {
			FileUtils.cleanDirectory(textDir);
			textDir.delete();
		}
		System.out.println("Cache is cleared successfully");
	}

	/**
	 * Create cache file if it doesn't exist
	 * Add the filename into the cache file
	 * 
	 * @param cache
	 * @throws IOException
	 */
	public static void addcache(String cache) throws IOException {

		// Get the cache file
		File cacheFile = new File("src/Cache.txt");

		// If cache file doesn't exist then create the file
		if (!cacheFile.exists()) {
			cacheFile.createNewFile();
		}

		// File output stream
		FileOutputStream fos = new FileOutputStream(cacheFile, true /* append = true */);

		// Initialize the print writer
		PrintWriter pw = new PrintWriter(fos);

		// write all the text to the file
		pw.append(cache + "\n");

		// close the file
		pw.close();
	}

	/**
	 * 
	 * Check if url is available in the file
	 * 
	 * @param Url
	 * @return
	 * @throws IOException
	 */
	public static Boolean isAvailable(String Url) throws IOException {
		
		In cacheFile = new In("src/Cache.txt");
		
		// If file is empty then return false
		if(cacheFile.isEmpty()) {
			return false;
		}
		
		// Read line by line from cache file 	
		// Until it is empty
		while (!cacheFile.isEmpty()) {
			String line = cacheFile.readLine();
			// Get the URL from the string
			String urlInCache = line.split(" ")[0];
			// Compare URL with the input URL if found return true
			if (urlInCache.equals(Url)) {
				return true;
			}
		}

		return false;
	}

}
