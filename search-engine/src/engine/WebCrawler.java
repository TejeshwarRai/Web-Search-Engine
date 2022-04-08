package engine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;




public class WebCrawler implements Runnable{

	private Thread thread;
	private static Map<String, String> visit = new HashMap<String, String>();
	private String link;
	private static int maxlvl = 3;
	private static int total = 0;
	private static int totalf = 0;
	private static int count = 0;
	public static String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

	public WebCrawler(String link1, int maxlevel, int totalLink) {
		maxlvl = maxlevel;
		totalf = totalLink;
		link = link1;
		thread = new Thread(this);

		thread.start();
	}

	@Override
	public void run() {
		crawl(0, link);
	}

	private static void crawl(int lvl, String url) {
		if (total < totalf && lvl <= maxlvl) {
			Document doc = request(url);
			if (doc != null) {
				{
					total++;
					for (Element link : doc.select("a[href]")) {
						String nextURL = link.absUrl("href");

						if (isURL(nextURL)) {

							if (!visit.containsKey(nextURL)) {
								int nxtLvl = lvl++;

								crawl(nxtLvl, nextURL);
							}
						}
					}
				}
			}

		}

	}

	private static boolean isURL(String url) {
		String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(url);

		if (m.matches()) {
			return true;
		}
		return false;

	}

	private static Document request(String url) {
		try {
			Connection con = Jsoup.connect(url);
			Document doc = con.get();

			if (con.response().statusCode() == 200) {
				System.out.println(url);
				String title = doc.title();
				visit.put(url, title);
				writeFile(title, doc);

				return doc;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;

	}

	public static void writeFile(String title, Document doc) {
		count++;
		BufferedWriter writerTxt;
		try {
			writerTxt = new BufferedWriter(new FileWriter(".\\src\\saved\\" + title + "" + count + ".html"));
			writerTxt.write(doc.outerHtml());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getLocalizedMessage();
		}
	}

	public static void main(String[] args) {
		WebCrawler wc = new WebCrawler("https://uwindsor.ca/", 3, 11);

	}
}
