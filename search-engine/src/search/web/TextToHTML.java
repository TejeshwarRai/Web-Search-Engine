package search.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TextToHTML {
	
	public static void testfiles(String url) throws IOException
	{
		File dir = new File("TextFiles");
		if(!dir.exists())
		dir.mkdir();
    
		Document doc = Jsoup.connect(url).get();
		String location=null;

		long name = System.currentTimeMillis();
        String loc =String.valueOf(name);
        
		location ="TextFiles//"+loc+".txt";
				
		File file3 = new File(location);
        if(!file3.exists())
		file3.createNewFile();
		Elements element = doc.select("*");
		
		Cache.addcache(url+" "+loc+".txt");

		BufferedWriter filefinale = new BufferedWriter(new FileWriter(file3));
		for (Element e : element) {
			filefinale.write(e.ownText());
			filefinale.newLine();
		}

		filefinale.flush();
		filefinale.close();
		
	}

}
