package cloud.project.parse;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cloud.project.Header;
import cloud.project.database.ArticleDbHandler;
import cloud.project.image.ImageHandler;

public class HtmlParser {
	public static ArticleDbHandler articleDbHandler = new ArticleDbHandler();
	
	public static void parseCnn(String urlStr, Date date){
		//http://edition.cnn.com/services/rss/
		//http://rss.cnn.com/rss/cnn_latest.rss
		//http://edition.cnn.com/2015/06/01/politics/yemen-four-americans-held-houthis/index.html
		try{
			Response response = Jsoup.connect(urlStr).followRedirects(true).execute();
			Document xmlDoc = response.parse();
			urlStr = response.url().toString();
			URL url = new URL(urlStr);
			
			Elements titleEle = xmlDoc.getElementsByClass("pg-headline");
			
			//author
			Elements authorEle = xmlDoc.getElementsByClass("metadata__byline__author");						
			if("CNN Library".equalsIgnoreCase(authorEle.text())){ //filter fast facts
				System.out.println(titleEle.text()+"--- by CNN Library");
				return;
			}
			String author = authorEle.text();
			if(author.startsWith("By ")) author = author.replaceFirst("By ", ""); //trim prefix
			if(author.endsWith(", CNN")) author = author.replace(", CNN",  ""); //trim postfix
			else if(author.endsWith(", special to CNN")) author = author.replace(", special to CNN",  "");
			else if(author.endsWith(", Special to CNN")) author = author.replace(", Special to CNN",  "");
			else if(author.endsWith(", for CNN")) author = author.replace(", for CNN",  "");
			
			//category
			int src = ArticleCategory.SRC_CNN;
			String[] path = url.getPath().split("/");
			String catStr = path[4];						
			if(catStr.equalsIgnoreCase("opinions")){ //filter "opinion"
				System.out.println(titleEle.text()+"--- is opinion");
				return;
			}			
			int cat = ArticleCategory.getCatId(src, catStr);			
			//System.out.println(catStr+" "+cat);
			
			//image
			String img = "";
			Element imgBlkEle = xmlDoc.getElementsByClass("pg-rail-tall__head").first();
			if(imgBlkEle!=null){
				Elements imgContainer = imgBlkEle.getElementsByClass("l-container");
				if(imgContainer.size()==1){ //more likely is image, size()==2 is usually video
					Elements imgsEle = imgContainer.first().getElementsByTag("img");
					Element imgEle = imgsEle.first();
					img = imgEle.attr("data-src-medium");				
				}
			}
			else{
				imgBlkEle = xmlDoc.getElementsByClass("el__position").first();
				if(imgBlkEle!=null){
					Elements imgsEle = imgBlkEle.getElementsByTag("img");
					Element imgEle = imgsEle.first();
					img = imgEle.attr("data-src-medium");
				}
			}									
			
			//content			
			Elements paragraphsEle = xmlDoc.getElementsByClass("zn-body__paragraph"); 
	
			LinkedList<String> paragraphs = new LinkedList<String>();
			for(Element element : paragraphsEle){
				paragraphs.add(element.text());
			}
												
			//Article(String title, String author, String time, String url, int src, int cat, LinkedList<String> paragraphs, img)
			Article article = new Article(titleEle.text(), author, date, urlStr, src, cat, paragraphs, img);
			if(img!=null && img!=""){
				String s3Url = ImageHandler.saveImage(img, new Date().getTime()+".jpg");
				article.setS3ImgUrl(s3Url);
			}
			System.out.println(article);			
			//articleDbHandler.insertArticle(article);
			
		}catch(Exception e){
			System.out.println(urlStr+" excpetion");
			e.printStackTrace();
			return;
		}
		
	}
	
	public static void parseVoa(String urlStr, Date date, String img) throws Exception {
		//http://www.voanews.com/api/epiqq
		//http://www.voanews.com/content/study-says-well-known-common-drug-may-block-ebola/2806197.html
		try{
			Response response = Jsoup.connect(urlStr).followRedirects(true).execute();
			Document xmlDoc = response.parse();
			urlStr = response.url().toString();
			URL url = new URL(urlStr);
			String[] path = url.getPath().split("/");
			
			if(!path[1].equals("content")){
				System.out.println(urlStr+"-- is not content");
				return;
			}
			
			Elements catEle = xmlDoc.getElementsByClass("sitetitle");
			Element titleEle = catEle.get(0).siblingElements().get(0);
			Elements authorEle = xmlDoc.getElementsByClass("author");
			
			Element textEle = xmlDoc.getElementById("ctl00_ctl00_cpAB_cp1_cbcContentBreak");
			Elements paragraphsEle = textEle.getElementsByTag("p");
			LinkedList<String> paragraphs = new LinkedList<String>();
			for(Element element : paragraphsEle){
				paragraphs.add(element.text());
			}
			
			
			int src = ArticleCategory.SRC_VOA;
			String catStr = catEle.text().split("/")[1].trim();
			int cat = ArticleCategory.getCatId(src, catStr);
			
			System.out.println(catStr+" "+cat);
			
			//Article(String title, String author, String time, String url, int src, int cat, LinkedList<String> paragraphs)
			Article article = new Article(titleEle.text(), authorEle.text(), date, urlStr, src, cat, paragraphs, img);			
			if(img!=null && img!=""){
				String s3Url = ImageHandler.saveImage(img, new Date().getTime()+".jpg");
				article.setS3ImgUrl(s3Url);
			}
			System.out.println(article);
			//articleDbHandler.insertArticle(article);
		}catch(Exception e){
			System.out.println(urlStr+" excpetion");
			e.printStackTrace();
			return;
		}
		
	}
	
	public static void parse60s(String urlStr, Date date) throws Exception {
		//http://rss.sciam.com/sciam/60secsciencepodcast
		//http://www.scientificamerican.com/podcast/episode/ancient-human-migration-route-marked-by-snail-shell-bread-crumbs/?utm_source=feedburner&utm_medium=feed&utm_campaign=Feed%3A+sciam%2F60secsciencepodcast+%28Podcast%3A+Scientific+American+-+60-Second+Science%29
		Response response = Jsoup.connect(urlStr).followRedirects(true).execute();
		Document xmlDoc = response.parse();
		urlStr = response.url().toString();
		URL url = new URL(urlStr);
		String img = "";

		
		Elements titleEle = xmlDoc.getElementsByClass("article-title");				
		
		//author
		Elements author_timeEle = xmlDoc.getElementsByClass("article-author");
		String[] authorSplit = author_timeEle.text().split("\\|");
		String author = authorSplit[0].split("By ")[1];
		
		//content
		Elements textEle = xmlDoc.getElementsByClass("article-content");
		Elements paragraphsEle = textEle.get(0).getElementsByTag("p");		
		LinkedList<String> paragraphs = new LinkedList<String>();
		for(int i=0;i<paragraphsEle.size()-2;i++){
			Element element = paragraphsEle.get(i);
			paragraphs.add(element.text());
		}
		
		//category
		int src = ArticleCategory.SRC_60S;
		Elements catEle = xmlDoc.getElementsByAttributeValue("itemProp", "title");
		String catStr = catEle.get(0).text();		
		int cat = ArticleCategory.getCatId(src, catStr);
		System.out.println(catStr+" "+cat);
		
		//Article(String title, String author, String time, String url, int src, int cat, LinkedList<String> paragraphs)
		Article article = new Article(titleEle.text(), author, date, urlStr, src, cat, paragraphs, img);
		System.out.println(article);
		//articleDbHandler.insertArticle(article);		
	}
	//http://www.reuters.com/article/2015/06/06/us-tennis-french-women-idUSKBN0OM0MS20150606
	//http://www.scientificamerican.com/
	
}
