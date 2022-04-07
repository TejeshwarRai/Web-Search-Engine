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

public class FindWord {

	private static final String DIRECTORY_PATH = "src/WebPagesInText/";

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
	public static void readAllFiles() throws IOException {
		// create instance of directory
		File fileDir = new File(DIRECTORY_PATH);
		Scanner scanInput = new Scanner(System.in);
		String restart;

		// Get list of all the files in form of String Array
		String[] namingFiles = fileDir.list();
		
		// Map used to store Name of Text file mapped to the occurrence of word
		Map<String, Integer> hashMap = new HashMap<String, Integer>();

		do {
			System.out.println("Input Search Word: ");
			String searchKeyword = scanInput.nextLine(); // Read user input
			
			

			// loop for reading the contents of all the files
			for (String files : Objects.requireNonNull(namingFiles)) {

				String namedFile = DIRECTORY_PATH + files;
				File currfile = new File(namedFile);
				if (currfile.exists() && currfile.isFile() && currfile.canRead()) {
					Path filePath = Paths.get(namedFile);
					hashMap.put(filePath.getFileName().toString(), new Integer(numberOfOccurrence(filePath, searchKeyword)));

				}
			}

			Map<String, Integer> mapAfterSorting = sortingByValue(hashMap);

			// Ranking method called to rank the HTML files from most occurred to least
			// occurred
			RankingFiles(mapAfterSorting);						
					
			System.out.println("Search For Another Word? Y/N");
			restart = scanInput.nextLine();
		} while (restart.equals("y") || restart.equals("Y"));

		if (restart.equals("N") || restart.equals("n"))
			System.out.println("Thanks for using our Services. Help us get Good Score");

	}
	
	
	
	
	 // Method to sort the files on the basis of occurrence of the word
	  private static Map<String, Integer> sortingByValue(Map<String, Integer> unsortMap) {

	       
	        List<Map.Entry<String, Integer>> mappedList =
	                new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

	        
	        Collections.sort(mappedList, new Comparator<Map.Entry<String, Integer>>() {
	            public int compare(Map.Entry<String, Integer> o1,
	                               Map.Entry<String, Integer> o2) {
	                return (o1.getValue()).compareTo(o2.getValue());
	            }
	        });

	        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
	        Map<String, Integer> mapAfterSort = new LinkedHashMap<String, Integer>();
	        for (Map.Entry<String, Integer> entry : mappedList) {
	        	mapAfterSort.put(entry.getKey(), entry.getValue());
	        }

	        /*
	        //classic iterator example
	        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
	            Map.Entry<String, Integer> entry = it.next();
	            sortedMap.put(entry.getKey(), entry.getValue());
	        }*/


	        return mapAfterSort;
	    }
	
	
	
	 
	  
	  
	  public static <K, V> void RankingFiles(Map<K, V> map) throws IOException {
	  ArrayList keyWordList = new ArrayList(map.keySet());
		BufferedReader readingBuffer = new BufferedReader(new FileReader("src/Cache.txt"));
		ArrayList<String> lineList = new ArrayList<>();
		Map<String, String> hmtMap = new HashMap<String, String>();
		String individualLine = readingBuffer.readLine(); 
	  System.out.println("Ranking of files");
	  int rank=1;
		for (int i = keyWordList.size() - 1; i >= 0; i--) {
			
			
			while (individualLine != null) { 
				lineList.add(individualLine); individualLine = readingBuffer.readLine(); 
				} 
		
			
			for(String strng: lineList)
			{
				String[] tmp=strng.split(" ");
				hmtMap.put(tmp[1],tmp[0]);
				
			}
			
			
			// File
			String key = (String) keyWordList.get(i);
			
			
			System.out.println(rank+". "+" |||       Occurrence of Word: "+map.get(key) +"   |||       URL:"+hmtMap.get(key));
			
		   
			//Occurrence
			//int value =(int) map.get(key);
			//System.out.println("Value :: " + value);
			rank++;
		}
		
	
		
		
		
		
		
		readingBuffer.close();
	  
	  }
	  
	/**
	 * Method used to find the number of occurrences of a string/word
	 *
	 * @param path
	 * @param wordToBeSearched
	 */
	private static int numberOfOccurrence(Path path, String wordToBeSearched) {

		int totalOccurances;

		TST<Integer> intTernarySearch = new TST<Integer>();

		List<String> eachLine = null;
		try {
			eachLine = Files.readAllLines(path, StandardCharsets.ISO_8859_1); // wrapping with try catch if file get null
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

		// printing total occurrence
  /*	System.out.println("The total number of occurrences of '" + wordToBeSearched + "' in '" + path.getFileName()
				+ "' is " + totalNumber); */
		
		return totalOccurances;
	}
}

