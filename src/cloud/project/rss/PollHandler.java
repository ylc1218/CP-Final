package cloud.project.rss;

import java.util.ArrayList;

import cloud.project.parse.HtmlParser;
import cloud.project.rss.RSS.RSSItem;

public class PollHandler {
	public static void pollCnn() throws Exception{
		RSS rss;
		/* Construct a new RSS object with a poll timeout of 60 seconds */
		rss = new RSS("http://rss.cnn.com/rss/cnn_latest.rss", 60);
		
		System.out.println("Polling feed...");
		ArrayList<RSSItem> list = null;
		try {
			list = rss.poll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(list.size());
		for(RSSItem item : list) {
			//System.out.println(item. + " - " + item.getTitle() + ": " + item.getLink());
			HtmlParser.parseCnn(item.getLink(), item.getDate());
			//System.out.println("--------------------------------");
		}
	}
	
	public static void pollVoa() throws Exception{
		RSS rss;
		/* Construct a new RSS object with a poll timeout of 60 seconds */
		rss = new RSS("http://www.voanews.com/api/epiqq", 60);
		
		System.out.println("Polling feed...");
		ArrayList<RSSItem> list = null;
		try {
			list = rss.poll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(list.size());
		for(RSSItem item : list) {
			//System.out.println(item.getFeedTitle() + " - " + item.getTitle() + ": " + item.getLink());
			HtmlParser.parseVoa(item.getLink(), item.getDate(), item.getImg());
			System.out.println("--------------------------------");
		}
	}
	
	public static void poll60s() throws Exception{
		RSS rss;
		/* Construct a new RSS object with a poll timeout of 60 seconds */
		rss = new RSS("http://rss.sciam.com/sciam/60secsciencepodcast", 60);
		
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
			//HtmlParser.parse60s(item.getLink());
			//System.out.println("--------------------------------");
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
