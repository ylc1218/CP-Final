package cloud.project.database;

import cloud.project.parse.Article;

public class ArticleDbHandler {
	
	
	public int insertArticle(Article article){
		return MysqlService.insertArticle(article);		
	}
}
