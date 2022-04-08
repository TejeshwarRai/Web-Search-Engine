package engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {

	public static final int MAX_DEPTH = 1;
	public static final String URL_PATTERN = "((http|https)://)(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}([-a-zA-Z0-9@:%._\\+~#?&//=]*)";

	public static void crawlWeb(int depth, String url, ArrayList<String> visitedURL) throws IOException {
		if (!isEmpty(url) && depth <= MAX_DEPTH) {
			Document doc = getDocument(url, visitedURL);
			if (doc != null) {
				Elements elements = doc.select("a[href]");
				for (Element element : elements) {
					String linkonPage = element.absUrl("href");
					if (!visitedURL.contains(linkonPage)) {
						crawlWeb(depth++, linkonPage, visitedURL);
						
					}
				}
			}
		}

	}

	private static boolean isEmpty(String url) {
		if (url != null && url != "" && Pattern.matches(URL_PATTERN, url))
			return false;
		return true;
	}

	private static Document getDocument(String url, ArrayList<String> visitedURL) {
		//TextToHTML html = new TextToHTML();
		Connection conn = Jsoup.connect(url);
		
		try {
			Document doc = conn.ignoreContentType(true).get();
			if (conn.response().statusCode() == 200) {
				System.out.println("Link on this page " + url);
				HTMLtoText.convertToTextFile(url);
				visitedURL.add(url);
				return doc;
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		return null;

	}


}
