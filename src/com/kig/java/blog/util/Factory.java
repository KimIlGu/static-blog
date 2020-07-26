package com.kig.java.blog.util;

import java.util.Scanner;

import com.kig.java.blog.dao.ArticleDao;
import com.kig.java.blog.dao.BoardDao;
import com.kig.java.blog.dao.MemberDao;
import com.kig.java.blog.db.DB;
import com.kig.java.blog.service.ArticleService;
import com.kig.java.blog.service.BoardService;
import com.kig.java.blog.service.BuildService;
import com.kig.java.blog.service.MemberService;

public class Factory {
	private static Scanner scanner;
	private static MemberService memberService;
	private static MemberDao memberDao;
	private static ArticleService articleService;
	private static ArticleDao articleDao;
	private static BoardService boardService;
	private static BoardDao boardDao;
	private static BuildService buildService;
	private static DB db;
	private static Session session;
	
	public static Scanner getScanner() {
		if (scanner == null) {
			scanner = new Scanner(System.in);
		}
		return scanner;
	}
	
	public static MemberService getMemberService() {
		if (memberService == null) {
			memberService = new MemberService();
		}
		return memberService;
	}
	
	public static MemberDao getMemberDao() {
		if (memberDao == null) {
			memberDao = new MemberDao();
		}
		return memberDao;
	}
	
	public static ArticleService getArticleService() {
		if (articleService == null) {
			articleService = new ArticleService();
		}
		return articleService;
	}
	
	public static ArticleDao getArticleDao() {
		if (articleDao == null) {
			articleDao = new ArticleDao();
		}
		return articleDao;
	}

	public static BoardService getBoardService() {
		if (boardService == null) {
			boardService = new BoardService();
		}
		return boardService;
	}
	
	public static BoardDao getBoardDao() {
		if (boardDao == null) {
			boardDao = new BoardDao();
		}
		return boardDao;
	}
	
	public static BuildService getBuildService() {
		if (buildService == null) {
			buildService = new BuildService();
		}
		return buildService;
	}

	public static DB getDB() {
		if (db == null) {
			db = new DB();
		}
		return db;
	}
	
	public static Session getSession() {
		if (session == null) {
			session = new Session();
		}
		return session;
	}
}