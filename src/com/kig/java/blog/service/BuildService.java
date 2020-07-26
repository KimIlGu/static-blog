package com.kig.java.blog.service;

import java.util.List;

import com.kig.java.blog.dto.Article;
import com.kig.java.blog.dto.Board;
import com.kig.java.blog.util.Factory;
import com.kig.java.blog.util.Util;

public class BuildService {
	private static boolean workStarted;
	
	private ArticleService articleService;

	static {
		workStarted = false;
	}

	public BuildService() {
		articleService = Factory.getArticleService();
	}

	// 각 게시판 별 게시물리스트 페이지 생성
	public void buildSite() {
		Util.makeDir("site");
		Util.makeDir("site/article");

		String head = Util.getFileContents("site_template/part/head.html");
		String foot = Util.getFileContents("site_template/part/foot.html");

		// 각 게시판 별 게시물리스트 페이지 생성
		List<Board> boards = Factory.getBoardService().getBoards();

		for (Board board : boards) {
			
			String fileName = board.getCode() + "-list-1.html";

			String html = "";

			List<Article> articles = Factory.getDB().getArticlesByBoardCode(board.getCode());
			
//			String template = Util.getFileContents("site_template/article/list.html");
			
			for (Article article : articles) {
				
			}

//			html = template.replace("${TR}", html);

			html = head + html + foot;

			Util.writeFileContents("site/article/" + fileName, html);
		}
		
		// 게시물 별 파일 생성
		List<Article> articles = Factory.getDB().getArticles();

		for (Article article : articles) {
			String html = "";
			
			html += "<div>번호 : " + article.getId() + "</div>";
			html += "<div>제목 : " + article.getTitle() + "</div>";
			html += "<div>내용 : " + article.getBody() + "</div>";
			html += "<div>작성자 : " + Factory.getDB().getMember(article.getMemberId()).getNickname() + "</div>";
			html += "<div>작성일 : " + article.getRegDate() + "</div>";
			html += "<div>조회수 : " + article.getHit() + "</div>";

			html += "<div><a href=\"" + (article.getId() - 1) + ".html\">이전글</a></div>";
			html += "<div><a href=\"" + (article.getId() + 1) + ".html\">다음글</a></div>";

			html = head + html + foot;

			Util.writeFileContents("site/article/" + article.getId() + ".html", html);
		}
	}

	public void buildStartAutoSite() {
		workStarted = true;
		
		new Thread(() -> {
			while (workStarted) {
				buildSite();
				System.out.println("생성");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
				}
			}
		}).start();
	}

	public void buildStropAutoSite() {
		try {
			System.out.println("10초 후 종료");
			Thread.sleep(10000);
			System.out.println("자동 생성 종료");
		} catch (InterruptedException e) {
		}
		workStarted = false;
	}
}