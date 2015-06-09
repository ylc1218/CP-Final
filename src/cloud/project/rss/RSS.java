package cloud.project.rss;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
 


import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
 







import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
 
/*
 * RSS Aggregation Class
 * @author Tim Roden <tim@timroden.ca> 
 * @url http://timroden.ca/
 * @usage:
 * 		- Construct the RSS class
 * 		- Eat the feeds first!: RSS.eat()
 * 		- Poll your feed whenever you like, checking for new updates: RSS.poll()
 */
 
public class RSS {
	private String feed;
	private ArrayList<String> cache = new ArrayList<String>();
	private RSSItem last = null;
	private int maxTime;	
	
	/*
	 * public RSS(String, int)
	 * @param String feed: The URL of the feed you wish to parse
	 * @param int maxTime: The maximum execution time in seconds	 
	 */
 
	public RSS(String feed, int maxTime) {
		this.maxTime = maxTime * 1000;
		this.feed = feed;
	}
 
	/* 
	 * public RSSItem getLast()
	 * @return RSSItem: The last item retrieved from the feed
	 */
	
	public RSSItem getLast() {
		return this.last;
	}
	
	/*
	 * public int eat()
	 * @throws Exception
	 * @return int: The amount of items eaten
	 */
 
	public int eat() throws Exception {
		int count = 0;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
 
		URL url = new URL(feed);  
 
		URLConnection con = url.openConnection();
 
		con.setConnectTimeout(maxTime);
		con.setReadTimeout(maxTime);
 
		Document doc = docBuilder.parse(con.getInputStream()); 
 
		doc.getDocumentElement().normalize();
 
		NodeList nList = doc.getElementsByTagName("item");
 
		for(int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
 
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
 
				String guid = getTagValue("guid", eElement);
 
				if(!cache.contains(guid)) {
					cache.add(guid);
				}
			}
			count++;
 
		}
		return count;
	}
	
	/*
	 * public ArrayList<RSSItem> poll()
	 * @throws Exception
	 * @return ArrayList<RSSItem>: An ArrayList of posts fetched from the feed not contained in the cache at the time of polling
	 */
 
	public ArrayList<RSSItem> poll() throws Exception {
		boolean done = false;
		long start = time();
 
		ArrayList<RSSItem> items = new ArrayList<RSSItem>();
 
		while(time() < start + maxTime && !done) {
 
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
 
			URL url = new URL(feed);  
 
			URLConnection con = url.openConnection();
 
			con.setConnectTimeout(maxTime);
			con.setReadTimeout(maxTime);
 
			Document doc = docBuilder.parse(con.getInputStream()); 
 
			doc.getDocumentElement().normalize();
 
			NodeList nList = doc.getElementsByTagName("item");
			
			String feedTitle = null;
			
			NodeList titleList = doc.getElementsByTagName("channel");
			
			for(int i = 0; i < titleList.getLength(); i++) {
				Node nNode = titleList.item(i);
				
				if(nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;			
					
					feedTitle = getTagValue("title", eElement);
					
					break;		
				}
			}
			
			for(int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
 
				if(nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
 
					String articleTitle = getTagValue("title", eElement);
					String articleLink = getTagValue("link", eElement);
					String articleGuid = getTagValue("guid", eElement);
					String articleImg = getImage(eElement);
					String articleTime = getTagValue("pubDate", eElement);					
					Date articleCtsDate = getCtsDate(articleTime);					
				    
					if(cache.contains(articleGuid)) {
						continue;						
					}
					
					cache.add(articleGuid);
 
					RSSItem item = new RSSItem(articleTitle, articleLink, articleCtsDate, articleImg);
 
					items.add(item);
 
					last = item;
				}
			}
 
			done = true;
		}
		return items;
	}
 
	/*
	 * private String getTagValue(String, Element)
	 * @param String sTag: The node that you wish to get the value of
	 * @param Element eElement: The element containing the node you wish to get the value of 
	 * @return String: the value of a node within an element
	 */
	
	private String getTagValue(String sTag, Element eElement) {		
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);		
		return nValue.getNodeValue();
	}
	
	private String getImage(Element eElement){
		String photoUrl="";
		NodeList nlList = eElement.getElementsByTagName("enclosure");
		if(nlList.getLength()>0){
			Node node=nlList.item(0);
			NamedNodeMap map = node.getAttributes();
			Node url = map.getNamedItem("url");
			Node type = map.getNamedItem("type");
			if(url!=null && type!=null && type.getNodeValue().startsWith("image"))
				photoUrl = url.getNodeValue();
		}				
		return photoUrl;
	}
 
	/* 
	 * private long time()
	 * @return long: Current Unix timestamp
	 */
	
	private long time() {
		return System.currentTimeMillis() / 1000;
	}
	
	private Date getCtsDate(String time) throws ParseException{
		DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
		formatter.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
		Date date = formatter.parse(time);
		return date;		
	}
	
	/*
	 * public class RSSItem
	 */
 
	public class RSSItem {
		private String title;
		private String link; 		
		private String img;
		private Date date;
 
		public RSSItem(String title, String link, Date date, String img) {
			this.title = title;
			this.link = link;
			this.img = img;
			this.date = date;
		}
		
		public String getTitle() {
			return this.title;
		}
 
		public String getLink() {
			return this.link;
		}

		public String getImg() {
			return this.img;
		}
		
		public Date getDate(){
			return this.date;
		}
	}		
}
