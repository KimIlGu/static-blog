package com.kig.java.blog.service;

import java.util.List;

import com.kig.java.blog.dao.ArticleDao;
import com.kig.java.blog.dto.Article;
import com.kig.java.blog.util.Factory;

public class ArticleService {
	private ArticleDao articleDao;

	public ArticleService() {
		articleDao = Factory.getArticleDao();
	}

	public void write(Article article) {
		articleDao.save(article);
	}

	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}

	public void modify(Article article) {
		articleDao.modify(article);
	}

	public void remove(Article article) {
		articleDao.remove(article);
	}

	public List<Article> getArticlesByPageIdAndKeyword(int pageId, String keyword) {
		return articleDao.getArticlesByPageIdAndKeyword(pageId, keyword);
	}
}