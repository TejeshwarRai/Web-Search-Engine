
package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import lib.Trie;
import lib.TrieNode;

public class SpellCorrector {

	private static final String DIR_PATH = "src/WebPagesInText";

	Trie trie = new Trie();
	Map<String, Integer> WordCount = new HashMap<>(); // creating a hashmap- WordCount

	public void loadSpellCorrector() { // https://stackabuse.com/java-list-files-in-a-directory/
		File f = new File(DIR_PATH);

		File[] files = f.listFiles(); // using a File class as an array

		for (int i = 0; i < files.length; i++) { // iterate through each file
			if (files[i].isFile()) { // check if specified file is present or not

				try {
					storeInDictionary(files[i]);
				} catch (IOException e) {
					System.out.println("in exception block1");
					e.printStackTrace();
				}
			}
		}
	}

	public void storeInDictionary(File fileName) throws IOException {

		try {
			FileReader textfileReader = new FileReader(fileName); // // reading text file into array
			BufferedReader bufferReader = new BufferedReader(textfileReader);

			String line = ""; // new line with value empty string

			while ((line = bufferReader.readLine()) != null) { // read file line by line only if it is not null
				String word = line.toLowerCase(); // converting to lower case

				// System.out.println(word);

				if (!line.contains(" ")) {
					word = word.toLowerCase(); // converting to lower case
					if (isAlpha(word)) {
						WordCount.put(word, WordCount.getOrDefault(word, 0) + 1); // increase count if word
						trie.addWord(word); // add word to trie
					}
				} else {
					String[] sWords = line.split("\\s");

					for (String sWord : sWords) {
						sWord = sWord.toLowerCase();
						if (isAlpha(sWord)) {
							WordCount.put(sWord, WordCount.getOrDefault(sWord, 0) + 1);
							trie.addWord(sWord); // add word to trie
						}
					}
				}
			}

			textfileReader.close();
			bufferReader.close();
		} catch (Exception e) {
			System.out.println("exception");
			e.printStackTrace();
			System.out.println(e);
		}
	}

	public static boolean isAlpha(String sWord) {

		return ((sWord != null) && (!sWord.equals("")) && (sWord.matches("^[a-zA-Z]*$"))); // check if it contains all
																							// alphabets
	}

	public String findSimilarWord(String input_word) { // input from the user
		String result = "";
		if (input_word.length() == 0 || input_word == null) { // if input is empty, return result
			return result;
		}

		String sLowerInput = input_word.toLowerCase(); // converting input to lower case

		TreeMap<Integer, TreeMap<Integer, TreeSet<String>>> map = new TreeMap<>();

		TrieNode node = trie.search(sLowerInput); // search input word in trie

		if (node == null) {
			for (String word : WordCount.keySet()) {
				int distance = lib.Sequences.editDistance(word, sLowerInput); // calculate min distance btw string and
																				// input word
				TreeMap<Integer, TreeSet<String>> similarWords = map.getOrDefault(distance, new TreeMap<>());

				int frequency = WordCount.get(word);
				TreeSet<String> set = similarWords.getOrDefault(frequency, new TreeSet<>());
				set.add(word);

				similarWords.put(frequency, set);
				map.put(distance, similarWords);
			}

			result = map.firstEntry().getValue().lastEntry().getValue().first();
		} else if (node != null) {
			result = sLowerInput;
		}

		return result;
	}

	public ArrayList autocomplete(String input_word) {
		ArrayList<String> ab = new ArrayList<String>();

		if (input_word.length() == 0 || input_word == null) {
			return ab;
		}

		String sLowerInput = input_word.toLowerCase();

		TrieNode node = trie.search(sLowerInput);


		if (node == null) {
			for (String word : WordCount.keySet()) {
				if (!word.isEmpty()) {
					if (word.startsWith(sLowerInput)) {
						ab.add(word);
					}
				}
			}
		}

		return ab;

	}

}