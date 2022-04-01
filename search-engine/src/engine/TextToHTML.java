package engine;

import java.util.Random;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TextToHTML {

	public static void main(String args[]) {
		try {
			convertToTextFile("https://complereinfosystem.com");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * Reference:: https://www.programiz.com/java-programming/printwriter
	 * 
	 * @param url
	 */
	public static void convertToTextFile(String htmlURL) throws IOException {

		File webPagesInTextDir = new File("src/WebPagesInText");
		if (!webPagesInTextDir.exists()) {
			webPagesInTextDir.mkdir();
		}

		Document doc = Jsoup.connect(htmlURL).get();
		String location = null;

		long name = System.currentTimeMillis();
		String loc = String.valueOf(name);

		location = "src/WebPagesInText/" + loc + ".txt";

		File file3 = new File(location);
		if (!file3.exists()) {
			file3.createNewFile();
		}
		Cache.addcache(htmlURL + " " + loc + ".txt");

		String text = doc.text();
		// initialize the print writer object
		PrintWriter out = new PrintWriter(location);
		// write all the text to the file
		out.println(text);
		// close the file
		out.close();
		
	}
	
	
}
