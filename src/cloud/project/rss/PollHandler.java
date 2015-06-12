package cloud.project.rss;

import java.util.ArrayList;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import cloud.project.AwsManager;
import cloud.project.FileMgr;
import cloud.project.Header;
import cloud.project.database.ArticleDbHandler;
import cloud.project.parse.Article;
import cloud.project.parse.HtmlParser;
import cloud.project.rss.RSS.RSSItem;

public class PollHandler {
	public static void putSqs(String str){
		AmazonSQS sqsClient = AwsManager.getSqsClient();
		SendMessageRequest req = new SendMessageRequest();
		req.setQueueUrl(Header.SQS_URL);
		req.setMessageBody(str);
		sqsClient.sendMessage(req);
	}
	
	
	public static void pollCnn(RSS rss){
		
		ArticleDbHandler dbHandler = new ArticleDbHandler();
		/* Construct a new RSS object with a poll timeout of 60 seconds */
		//rss = new RSS("http://rss.cnn.com/rss/cnn_latest.rss", 60);
		
		System.out.println("Polling cnn feed...");
		ArrayList<RSSItem> list = null;
		try {
			list = rss.poll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(list.size());
		for(RSSItem item : list) {
			Article article = HtmlParser.parseCnn(item.getLink(), item.getDate());
			if(article==null) continue;
			System.out.println(article);
			try{
				int id = dbHandler.insertArticle(article);
				if(id==-1){
					System.out.println("db id = -1");
					continue;
				}
				else{
					System.out.println("article mysql saved");
					String fileUrl = FileMgr.saveArticle(id+".txt", article.getFileStr());
					System.out.println("article txt saved - "+fileUrl);
					putSqs(fileUrl);
					System.out.println("article in sqs");
				}			
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void pollVoa(RSS rss){
		ArticleDbHandler dbHandler = new ArticleDbHandler();
		/* Construct a new RSS object with a poll timeout of 60 seconds */
		//rss = new RSS("http://www.voanews.com/api/epiqq", 60);
		
		System.out.println("Polling voa feed...");
		ArrayList<RSSItem> list = null;
		try {
			list = rss.poll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(list.size());
		for(RSSItem item : list) {
			Article article = HtmlParser.parseVoa(item.getLink(), item.getDate(), item.getImg());
			if(article==null) continue;
			System.out.println(article);
			try{
				int id = dbHandler.insertArticle(article);
				if(id==-1){
					System.out.println("db id = -1");
					continue;
				}
				else{
					System.out.println("article mysql saved");
					String fileUrl = FileMgr.saveArticle(id+".txt", article.getFileStr());
					System.out.println("article txt saved - "+fileUrl);
				}			
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void poll60s(RSS rss){
		
		ArticleDbHandler dbHandler = new ArticleDbHandler();
		/* Construct a new RSS object with a poll timeout of 60 seconds */
		//rss = new RSS("http://rss.sciam.com/sciam/60secsciencepodcast", 60);
		
		System.out.println("Polling 60s feed...");
		ArrayList<RSSItem> list = null;
		try {
			list = rss.poll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(list.size());
		for(RSSItem item : list) {
			Article article = HtmlParser.parse60s(item.getLink(), item.getDate());
			if(article==null) continue;
			System.out.println(article);
			try{
				int id = dbHandler.insertArticle(article);
				if(id==-1){
					System.out.println("db id = -1");
					continue;
				}
				else{
					System.out.println("article mysql saved");
					String fileUrl = FileMgr.saveArticle(id+".txt", article.getFileStr());
					System.out.println("article txt saved - "+fileUrl);
				}			
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	
	private static void tryRss(){
		RSS rss;
		/* Construct a new RSS object with a poll timeout of 60 seconds */
		rss = new RSS("http://www.voanews.com/api/zt$opeitim", 60);
		
		/* Eat (cache) existing items so that we don't get spammed with messages when we poll the feed */
		//rss.eat();
		
		System.out.println("Polling feed...");
		ArrayList<RSSItem> list = null;
		try {
			list = rss.poll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(list.size());
		for(RSSItem item : list) {
			System.out.println(item.getTitle() + ": " + item.getLink());
		}
		
		/* Schedule a timer to poll feeds every 5 minutes */
		/*Timer pollTimer = new Timer();
		
		long delay = 60 * 5 * 1000; // 5 minutes
		
		pollTimer.schedule(new PollTask(rss), delay, delay); // No need to set the second parameter to 0, we've just eaten the feeds.*/
	}
}
