package search.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import search.utility.In;

public class Cache {

	public static void addcache(String cache) throws IOException {

		File fstream = new File("Cache.txt");
		if(!fstream.exists())
		{
		fstream.createNewFile();
		}
		BufferedWriter out = new BufferedWriter(new FileWriter(fstream, true));
		out.append(cache);
		out.newLine();
		out.flush();
		out.close();

	}

	public static void deleteCache() throws IOException {
		File fstream = new File("Cache.txt");
		
		if (fstream.exists()) {
			//fstream.delete();
			BufferedWriter out = new BufferedWriter(new FileWriter(fstream, false));
			out.write("");
			out.flush();
			out.close();
			System.out.println("Deleted the file: ");
		} else {
			System.out.println("Failed to delete the file.");
		}
		File f = new File("TextFiles//");
		if(f.exists())
		{
		FileUtils.cleanDirectory(f);
		f.delete();
		}
		System.out.println("Cache has been sucesfully deleted");
	}

	public static Boolean isAvailable(String Url) {
		Boolean flag = false;
		try {
			In in = new In("Cache.txt");

			while (!in.isEmpty()) {
				String s = in.readLine();
				String stringArray[]= s.split(" ");
				if (stringArray[0].equals(Url))
					flag = true;
			}
			// System.out.println();
		} catch (Exception e) {
			System.out.println(e);
		}
		return flag;
	}

}
