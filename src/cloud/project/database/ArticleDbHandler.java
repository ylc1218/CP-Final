package cloud.project.database;

import cloud.project.parse.Article;

public class ArticleDbHandler {
	
	
	public int insertArticle(Article article){
		MysqlService.insertArticle(article);
		System.out.println("inserArticle done");
		return 0;
	}
}
