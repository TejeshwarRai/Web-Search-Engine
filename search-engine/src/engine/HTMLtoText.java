package engine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HTMLtoText {

	public static void main(String args[]) {
		try {
			convertToTextFile("https://wikihow.com");
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

		Connection jsoupConn = Jsoup.connect(htmlURL);
		Document doc = jsoupConn.get();
		

		String fileName = "";
	    
		long millis = System.currentTimeMillis();
	    
		String datetime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	    
		String rndchars = RandomStringUtils.randomAlphanumeric(16);
	    
		fileName = rndchars + "_" + datetime + "_" + millis;


		File f = new File("src/WebPagesInText/" + fileName + ".txt");

		if (!f.exists()) {
			f.createNewFile();
		}
		CacheManager.addcache(htmlURL + " " + fileName + ".txt");

		String text = doc.text();
		// initialize the print writer object
		PrintWriter out = new PrintWriter("src/WebPagesInText/" + fileName + ".txt");
		// write all the text to the file
		out.println(text);
		// close the file
		out.close();
		
	}
	
	
}
