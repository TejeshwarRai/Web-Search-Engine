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

	/**
	 * Method used to find the number of occurrences of a string/word
	 *
	 * @param path
	 * @param wordToBeSearched
	 */
	static int numberOfOccurrence(Path path, String wordToBeSearched) {

		int totalOccurances;

		TST<Integer> intTernarySearch = new TST<Integer>();

		List<String> eachLine = null;
		try {
			eachLine = Files.readAllLines(path, StandardCharsets.ISO_8859_1); // wrapping with try catch if file get
																				// null
		} catch (IOException e) {
			e.printStackTrace();
		}

		// running loop until null
		for (String line : Objects.requireNonNull(eachLine)) {

			StringTokenizer stringTokenizer = new StringTokenizer(line);
			while (stringTokenizer.hasMoreTokens()) {
				String Token = stringTokenizer.nextToken();
				if (intTernarySearch.get(Token) == null) {
					intTernarySearch.put(Token, 1);
				} else {
					intTernarySearch.put(Token, intTernarySearch.get(Token) + 1);
				}
			}
		}

		if (intTernarySearch.get(wordToBeSearched) != null)
			totalOccurances = intTernarySearch.get(wordToBeSearched);
		else
			totalOccurances = 0;

		return totalOccurances;
	}

	static void findFrequency(String wordsearch) {
		int totalNumber;
		File[] files = new File("src/WebPagesInText/").listFiles(); // Getting path of file
		for (File file : files) { // Iterating the folder to get all the html files
			String fileName = "src/WebPagesInText/"+file.getName();
			// Reference
			// https://www.geeksforgeeks.org/java-program-to-read-a-file-to-string/

			int frequency = findFrequencyInAFile(Paths.get(fileName),wordsearch);

			// printing total occurrence
			System.out.println("In file " + "\"" + fileName + "\"" + " frequency is : " + frequency);
		}
	}
	
	static int findFrequencyInAFile(Path path, String wordsearch) {
		StringBuilder builder = new StringBuilder();
		try (BufferedReader buffer = new BufferedReader(
				new FileReader(path.toString()))) {
			String str;
			// Appending text from file to String builder
			while ((str = buffer.readLine()) != null) {

				builder.append(str).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Converting string builder to string
		String text = builder.toString();
		HashMap<String, Integer> mapp = new HashMap<>();

		String[] listt = text.split(" ");
		int i = 0;
		for (String st : listt) {
			String s = st.toLowerCase();

			if (!s.equals("")) {
				if (mapp.containsKey(s)) {
					int c = mapp.get(s);
					mapp.put(s, c + 1);
				} else {
					mapp.put(s, i);
				}

			}
		}

		int frequency = 0;
		if (mapp.containsKey(wordsearch)) {
			frequency = mapp.get(wordsearch);
		}
		return frequency;
	}

}