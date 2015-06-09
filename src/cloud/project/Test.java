package cloud.project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cloud.project.database.MysqlService;
import cloud.project.parse.HtmlParser;
import cloud.project.rss.PollHandler;
import cloud.project.rss.RSS;
import cloud.project.rss.RSS.RSSItem;

public class Test {
	
 
	public static void main(String[] args) throws Exception {								
		//HtmlParser.parseCnn("http://edition.cnn.com/2015/06/06/football/champions-league-juventus-barcelona/index.html?utm_source=feedburner&utm_medium=feed&utm_campaign=Feed%3A+rss%2Fedition_football+%28RSS%3A+CNNi+-+Football%29");
		//HtmlParser.parseVoa("http://www.voanews.com/content/robin-williams-family-at-odds-over-actor-estate/2802825.html");
		//HtmlParser.parse60s("http://www.scientificamerican.com/podcast/episode/vaccine-aims-at-fly-host-of-disease-parasite/");
		//PollHandler.pollCnn();
		PollHandler.pollVoa();
		//PollHandler.poll60s();
	}	

	
	private static void sendGet() throws Exception {
		 System.out.println("sendGet ...");
		String url = "http://rss.cnn.com/~r/rss/cnn_latest/~3/a4jPEJ7BK1Y/index.html";
 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
 
	}
}
 
/* RSS class: https://gist.github.com/3856912 */
