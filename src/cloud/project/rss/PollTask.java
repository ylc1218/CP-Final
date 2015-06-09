package cloud.project.rss;

import java.util.ArrayList;
import java.util.TimerTask;

import cloud.project.parse.HtmlParser;
import cloud.project.rss.RSS.RSSItem;

 
public class PollTask extends TimerTask {
	private RSS rss;
	
	public PollTask(RSS rss) {
		this.rss = rss;
	}
	
	public void getFullUrl_Cnn(String oriUrl){
		
	}
	
	@Override
	public void run() {
		System.out.println("Polling feed...");
		ArrayList<RSSItem> list = null;
		try {
			list = rss.poll();
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		for(RSSItem item : list) {
			//System.out.println(item.getFeedTitle() + " - " + item.getTitle() + ": " + item.getLink());
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
