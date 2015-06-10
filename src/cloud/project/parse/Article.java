package cloud.project.parse;

import java.util.Date;
import java.util.LinkedList;

public class Article{
	private String title;
	private String author;
	private Date date;
	private String url;
	private int src;
	private int cat;
	private LinkedList<String> paragraphs;
	private String img;
	private String s3ImgUrl="";
	
	public Article(String title, String author, Date date, String url, int src, int cat, LinkedList<String> paragraphs, String img){		
		this.title = title;
		this.author = author;
		this.date = date;
		this.url = url;
		this.src = src;
		this.cat = cat;
		this.paragraphs = paragraphs;
		this.img = img;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("url:"+ url+"\n");
		sb.append("title:"+ title+"\n");
		sb.append("img:"+img+"\n");
		sb.append("(s3-img:"+s3ImgUrl+")\n");
		sb.append("author:"+author+"\n");
		sb.append("date:"+date+"\n");
		sb.append("text:"+paragraphStr());			
		sb.append("-----------------------------\n");
		return sb.toString();
	}
	
	public String paragraphStr(){
		StringBuilder sb = new StringBuilder();
		for(String paragraph:paragraphs){
			sb.append("<p>"+paragraph+"</p>");
		}
		return sb.toString();
	}
	
	public String getTitle(){
		return title;
	}
	
	public Date getDate(){
		return date;
	}
	
	public String getAuthor(){
		return author;
	}
	
	public String getUrl(){
		return url;
	}
	
	public int getSrc(){
		return src;
	}
	
	public int getCat(){
		return cat;
	}
	
	public String getImg(){
		return img;
	}
	
	public String getS3ImgUrl(){
		return s3ImgUrl;
	}
	
	public void setS3ImgUrl(String url){
		s3ImgUrl = url;	
	}
}
