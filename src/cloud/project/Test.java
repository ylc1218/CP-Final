package cloud.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

import cloud.project.database.MysqlService;
import cloud.project.dictionary.TranslationHandler;
import cloud.project.dictionary.TranslationHandler.WordParsed;
import cloud.project.image.ImageHandler;
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
		//PollHandler.pollVoa();
		//PollHandler.poll60s();
		//ImageHandler.saveImage("http://gdb.voanews.com/30D156D4-4570-4DB3-9AEE-41994F5EB36A_cx0_cy10_cw0_w800_h450.jpg", "./myImg.jpg");
		//HtmlParser.parseCnn("http://edition.cnn.com/2015/06/09/living/cnnphotos-american-puzzles/index.html?utm_source=feedburner&utm_medium=feed&utm_campaign=Feed%3A+rss%2Fcnn_latest+%28RSS%3A+CNN+-+Most+Recent%29%20excpetion", new Date());
	 		 
		//TranslationHandler.getDefaultWordsTranslation();
	 	/*WordParsed word = TranslationHandler.getTranslationHtml("longer");
	 	System.out.println(word.explains);*/
	 		 
	 	//FileMgr.saveArticle("temp.txt", "lulu means awesome");
	 	tryPeriodicallyPoll();
	}
	
	private static void tryPeriodicallyPoll() throws Exception{
		System.out.println("Poll message start." + new Date());
		final RSS rssCnn = new RSS("http://rss.cnn.com/rss/cnn_latest.rss", 60);
		final RSS rssVoa = new RSS("http://www.voanews.com/api/epiqq", 60);
		final RSS rss60s = new RSS("http://rss.sciam.com/sciam/60secsciencepodcast", 60);
		
		rssCnn.eat();
		rssVoa.eat();
		rss60s.eat();
		
		TimerTask taskCnn = new TimerTask() {				
			@Override
			public void run() {
				System.out.println("task cnn");
				PollHandler.pollCnn(rssCnn);
			}
		};
		
		TimerTask taskVoa = new TimerTask() {			
			@Override
			public void run() {
				System.out.println("task voa");
				PollHandler.pollVoa(rssVoa);
			}
		};
		
		TimerTask task60s = new TimerTask() {			
			@Override
			public void run() {
				System.out.println("task 60s");
				PollHandler.poll60s(rss60s);
			}
		};
		
		/* Schedule a timer to poll feeds every 5 minutes */
		Timer timerCnn = new Timer();
		Timer timerVoa = new Timer();
		Timer timer60s = new Timer();
		
		long delay = 60 * 1 * 1000; // 5 minutes
		
		timerCnn.schedule(taskCnn, delay, delay); // No need to set the second parameter to 0, we've just eaten the feeds.
		timerVoa.schedule(taskVoa, delay, delay); // No need to set the second parameter to 0, we've just eaten the feeds.
		timer60s.schedule(task60s, delay, delay); // No need to set the second parameter to 0, we've just eaten the feeds.
	}

	private static void testJson() throws JSONException{
		//String s =" {\"translation\":[\"loni\"],\"basic\":{\"explains\":[\"n. (Loni)鈭箏���(敺瑯���)瘣側\"]},\"query\":\"Loni\",\"errorCode\":0,\"web\":[{\"value\":[\"摰�恕\",\"Laboratory Of Neuro Imaging\",\"UCLA Laboratory of Neuro Imaging\"],\"key\":\"LONI\"},{\"value\":[\"蝵側繚餈芷蝐單\"],\"key\":\"loni dunamis\"},{\"value\":[\"��倌\"],\"key\":\"Loni Zwahlen\"}]}";
		String s ="{\"translation\":[\"aaaaaaaaaaa\"],\"query\":\"aaaaaaaaaaa\"}";
		JSONObject jsonObj = new JSONObject(s);
		if(jsonObj.has("basic")){
			JSONObject basicObj = (JSONObject) jsonObj.get("basic");
			JSONArray jsonArr = basicObj.getJSONArray("explains");
			System.out.println(jsonArr.toString());
		}
		else if(jsonObj.has("translation")){
			JSONArray translationObj = (JSONArray) jsonObj.get("translation");
			System.out.println(translationObj.toString());
		}
		
		
	}
	private static void sendGet() throws Exception {
		//System.out.println("sendGet ...");
		String url = "http://fanyi.youdao.com/openapi.do?type=data&doctype=jsonp&version=1.1&relatedUrl=http%3A%2F%2Ffanyi.youdao.com%2Fopenapi%3Fpath%3Dweb-mode%26mode%3Ddicter&keyfrom=test&key=null&callback=c&translate=on&q=punctuation&ts=1433926580206";		
		
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
		
		PrintWriter writer = new PrintWriter("text.txt", "utf-8");
		writer.println(response.toString());
		writer.close();
 
	}

}
 
/* RSS class: https://gist.github.com/3856912 */
