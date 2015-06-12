package cloud.project.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.management.Query;
import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

import cloud.project.Header;
import cloud.project.parse.Article;

public class MysqlService {
	private static final String DB_NAME = "creeper";
	
	private static Connection connect(){
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");			
			con = DriverManager.getConnection("jdbc:mysql://"+Header.DB_DNS+"/"+DB_NAME+"?useUnicode=true&characterEncoding=utf-8",
					Header.DB_ACCOUNT, Header.DB_PSW);			
		} catch (SQLException e) {
			System.out.println("Connect DB Exception :" + e.toString());
		} catch (ClassNotFoundException e){
			e.toString();
		}
		return con;
	}
	
	private static Statement createStatement(Connection con){
		Statement stat=null;
		try {
			stat=con.createStatement();
		} catch (SQLException e) {
			System.out.println("Create stat DB Exception :" + e.toString());
		}
		return stat;
	}
	
	private static void disconnect(Connection con){
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("Disconnect DB Exception :" + e.toString());
		}
	}
	
	
	public static CachedRowSet select(String sql){
		CachedRowSet crs=null;
		ResultSet rs=null;
		Connection con=connect();
		Statement stat=MysqlService.createStatement(con);
		try {
			rs=stat.executeQuery(sql);
			crs=new CachedRowSetImpl();
	        crs.populate(rs);	
	        rs.close();
		} catch (SQLException e) {
			System.out.println("SelectDB Exception :" + e.toString());
		} finally{
			 disconnect(con);
		}
		return crs;
	}
	
	public static int update(String sql){
		int result=-1;
		Connection con=connect();
		Statement stat=MysqlService.createStatement(con);
		try {
			result=stat.executeUpdate(sql);	
		} catch (SQLException e) {
			System.out.println("UpdateDB Exception :" + e.toString());
		} finally{
			disconnect(con);
		}
		return result;
	}
	
	public static int insert(String sql){
		Connection con=connect();
		Statement stat=MysqlService.createStatement(con);		
		int result=0;
		try {
			result=stat.executeUpdate(sql);			
		} catch (SQLException e) {
			System.out.println("insertDB Exception :" + e.toString());
		} finally{
			disconnect(con);
		}
		return result;
	}
	
	public static int insertGetAuto(String sql){
		Connection con=connect();
		Statement stat=MysqlService.createStatement(con);
		int uid=-1;
		try {
			stat.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stat.getGeneratedKeys();
		    if (rs.next()) {
		      uid = rs.getInt(1);
		    }
		} catch (SQLException e) {
			System.out.println("insertDB Exception :" + e.toString());
		} finally{
			disconnect(con);
		}
		return uid;
	}
	
	public static int insertArticle(Article article){
		Connection con=connect();
		//"INSERT INTO article(title, articleSourceId, url, category_id, author, time, content, image) "
		PreparedStatement stat;
		
		try {
			stat = con.prepareStatement("INSERT INTO articles(title, articleSourceId, url, categoryId, author, time, content, image, caption, wordCount)"
					+ "VALUES(?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			stat.setString(1, article.getTitle()); //title
			stat.setInt(2, article.getSrc()); //src_id
			stat.setString(3, article.getUrl()); //url
			stat.setInt(4, article.getCat()); //cat_id
			stat.setString(5, article.getAuthor()); //author
			stat.setObject(6, article.getDate()); //time		
			stat.setString(7, article.getParagraphStr()); //content
			stat.setString(8, article.getS3ImgUrl()); //img url
			stat.setString(9, article.getFirst50Words()); //caption
			stat.setInt(10, article.getWordCnt()); //wordCount
			stat.executeUpdate();
			
			ResultSet rs = stat.getGeneratedKeys();
			rs.next();
			con.close();			
			return rs.getInt(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
		
	}
	
	
}
