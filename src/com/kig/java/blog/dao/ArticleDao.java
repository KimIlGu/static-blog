package com.kig.java.blog.dao;

import java.util.List;

import com.kig.java.blog.db.DB;
import com.kig.java.blog.dto.Article;
import com.kig.java.blog.util.Factory;

public class ArticleDao {
	DB db;

	public ArticleDao() {
		db = Factory.getDB();
	}

	public void save(Article article) {
		db.save(article);
	}

	public Article getArticleById(int id) {
		return db.getArticleById(id);
	}

	public void modify(Article article) {
		db.modify(article);
	}

	public void remove(Article article) {
		db.remove(article);
	}

	public List<Article> getArticlesByPageIdAndKeyword(int pageId, String keyword) {
		return db.getArticlesByPageIdAndKeyword(pageId, keyword);
	}
}