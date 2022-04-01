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

	private static final String DIR_PATH = "TextFiles/";

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
		File dir = new File(DIR_PATH);
		Scanner s = new Scanner(System.in);
		String restart;

		// Get list of all the files in form of String Array
		String[] fileNames = dir.list();
		
		// Map used to store Name of Text file mapped to the occurrence of word
		Map<String, Integer> hm = new HashMap<String, Integer>();

		do {
			System.out.println("Enter word to be searched: ");
			String wordToBeSearched = s.nextLine(); // Read user input
			
			

			// loop for reading the contents of all the files
			for (String fileName : Objects.requireNonNull(fileNames)) {

				String file = DIR_PATH + fileName;
				File currfile = new File(file);
				if (currfile.exists() && currfile.isFile() && currfile.canRead()) {
					Path path = Paths.get(file);
					hm.put(path.getFileName().toString(), new Integer(numberOfOccurrence(path, wordToBeSearched)));

				}
			}

			Map<String, Integer> sortedMap = sortByValue(hm);

			// Ranking method called to rank the HTML files from most occurred to least
			// occurred
			Ranking(sortedMap);						
					
			System.out.println("Do you want to search another word? Y/N");
			restart = s.nextLine();
		} while (restart.equals("y") || restart.equals("Y"));

		if (restart.equals("N") || restart.equals("n"))
			System.out.println("Thank you for using our program, I hope we get 100 marks!");

	}
	
	
	
	
	 // Method to sort the files on the basis of occurrence of the word
	  private static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {

	       
	        List<Map.Entry<String, Integer>> list =
	                new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

	        
	        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
	            public int compare(Map.Entry<String, Integer> o1,
	                               Map.Entry<String, Integer> o2) {
	                return (o1.getValue()).compareTo(o2.getValue());
	            }
	        });

	        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
	        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
	        for (Map.Entry<String, Integer> entry : list) {
	            sortedMap.put(entry.getKey(), entry.getValue());
	        }

	        /*
	        //classic iterator example
	        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
	            Map.Entry<String, Integer> entry = it.next();
	            sortedMap.put(entry.getKey(), entry.getValue());
	        }*/


	        return sortedMap;
	    }
	
	
	
	 
	  
	  
	  public static <K, V> void Ranking(Map<K, V> map) throws IOException {
	  ArrayList keyList = new ArrayList(map.keySet());
		BufferedReader bufReader = new BufferedReader(new FileReader("Cache.txt"));
		ArrayList<String> listOfLines = new ArrayList<>();
		Map<String, String> hmt = new HashMap<String, String>();
		String line = bufReader.readLine(); 
	  System.out.println("Ranking of files");
	  int rank=1;
		for (int i = keyList.size() - 1; i >= 0; i--) {
			
			
			while (line != null) { 
				listOfLines.add(line); line = bufReader.readLine(); 
				} 
		
			
			for(String st: listOfLines)
			{
				String[] tmp=st.split(" ");
				hmt.put(tmp[1],tmp[0]);
				
			}
			
			
			// File
			String key = (String) keyList.get(i);
			
			System.out.println(rank+". "+" |||       Occurrence of Word: "+map.get(key) +"   |||       URL "+hmt.get(key));
			
		   
			
			
			//Occurrence
			//int value =(int) map.get(key);
			//System.out.println("Value :: " + value);
			rank++;
		}
		
	
		
		
		
		
		
		bufReader.close();
	  
	  }
	  
	/**
	 * Method used to find the number of occurrences of a string/word
	 *
	 * @param path
	 * @param wordToBeSearched
	 */
	private static int numberOfOccurrence(Path path, String wordToBeSearched) {

		int totalNumber;

		TST<Integer> integerTST = new TST<Integer>();

		List<String> lines = null;
		try {
			lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1); // wrapping with try catch if file get null
		} catch (IOException e) {
			e.printStackTrace();
		}

		// running loop until null
		for (String line : Objects.requireNonNull(lines)) {

			StringTokenizer stringTokenizer = new StringTokenizer(line);
			while (stringTokenizer.hasMoreTokens()) {
				String Token = stringTokenizer.nextToken();
				if (integerTST.get(Token) == null) {
					integerTST.put(Token, 1);
				} else {
					integerTST.put(Token, integerTST.get(Token) + 1);
				}
			}
		}

		if (integerTST.get(wordToBeSearched) != null)
			totalNumber = integerTST.get(wordToBeSearched);
		else
			totalNumber = 0;

		// printing total occurrence
  /*	System.out.println("The total number of occurrences of '" + wordToBeSearched + "' in '" + path.getFileName()
				+ "' is " + totalNumber); */
		
		return totalNumber;
	}
}

