package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.StringTokenizer;

import lib.TST;

public class WordFrequency {

	private static final String DIR_PATH = "src/WebPagesInText/";

	/*
	 * public static void main(String[] args) {
	 * 
	 * readAllFiles(); }
	 */

	/**
	 * Method used to read all text files from any directory
	 * 
	 * @throws IOException
	 *
	 */

	static void findFrequency(String wordToBeSearched) {

		HashMap<String, Integer> mapp = new HashMap<>();

		File files = new File(DIR_PATH);

		// Get list of all the files in form of String Array
		String[] fileNames = files.list();

		for (String fileName : Objects.requireNonNull(fileNames)) {
			String file = DIR_PATH + fileName;
			File currfile = new File(file);
			if (currfile.exists() && currfile.isFile() && currfile.canRead()) {
				Path path = Paths.get(file);

				List<String> lines = null;
				try {
					lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1); // wrapping with try catch if file
																					// get null
				} catch (IOException e) {
					e.printStackTrace();
				}

				for (String s : lines) {
					if (mapp.containsKey(s)) {
						int c = mapp.get(s);
						mapp.put(s, c + 1);
					}
					// mapp.put(s,i);

				}

				int frequency = 0;
				if (mapp.containsKey(wordToBeSearched)) {
					frequency = mapp.get(wordToBeSearched);
				}

				System.out.println(file + "-" + frequency);

				// printing total occurrence
				/*
				 * System.out.println("The total number of occurrences of '" + wordToBeSearched
				 * + "' in '" + path.getFileName() + "' is " + totalNumber);
				 */

			}
		}
	}

}
