package com.kig.java.blog.controller;

import java.util.List;

import com.kig.java.blog.dto.Article;
import com.kig.java.blog.service.ArticleService;
import com.kig.java.blog.util.Factory;
import com.kig.java.blog.util.Request;

public class ArticleController extends Controller {
	private ArticleService articleService;

	public ArticleController() {
		articleService = new ArticleService();
	}

	@Override
	public void doAction(Request request) {
		if (request.getActionName().equals("write")) {
			doActionWrite(request);
		} else if (request.getActionName().equals("modify")) {
			doActionModify(request);
		} else if (request.getActionName().equals("remove")) {
			doActionRemove(request);
		} else if (request.getActionName().equals("list")) {
			doActionList(request);
		} else if (request.getActionName().equals("detail")) {
			doActionDetail(request);
		}
	}

	private void doActionWrite(Request request) {
		if (Factory.getSession().getLoginedMember() == null) {
			System.out.printf("로그인 후 사용 가능합니다.\n");
		} else if (request.getArg2() != null) {
			System.out.printf("잘못된 명령어 입니다.");
		} else {
			System.out.printf("제목 : ");
			String title = Factory.getScanner().nextLine();
			System.out.printf("내용 : ");
			String body = Factory.getScanner().nextLine();

			int boardId = Factory.getSession().getCurrentBoard().getId();
			int memberId = Factory.getSession().getLoginedMember().getId();

			Article article = new Article(boardId, memberId, title, body);
			articleService.write(article);

			System.out.printf("%d번 글을 생성하였습니다.\n", article.getId());
		}
	}

	private void doActionModify(Request request) {
		if (Factory.getSession().getLoginedMember() == null) {
			System.out.printf("로그인 후 사용 가능합니다.\n");
		} else if (request.getArg1().length() == 0) {
			System.out.printf("번호를 입력해주세요.\n");
		} else {
			int articleId = Integer.parseInt(request.getArg1());
			int loginedId = Factory.getSession().getLoginedMember().getId();
			
			Article article = articleService.getArticleById(articleId);

			if (article == null) {
				System.out.printf("존재하지 않는 게시물 입니다.\n");
			} else if (Factory.getSession().getLoginedMember().getId() == 1) { 
				doActionDoModify(article);
			} else if (article.getMemberId() != loginedId) {
				System.out.printf("작성자 본인만 수정 가능합니다.\n");
			} else {
				doActionDoModify(article);
			}
		}
	}
	private void doActionDoModify(Article article) {
		System.out.printf("제목 : ");
		String title = Factory.getScanner().nextLine();
		System.out.printf("내용 : ");
		String body = Factory.getScanner().nextLine();

		article.setTitle(title);
		article.setBody(body);
		
		articleService.modify(article);
		
		System.out.printf("%d번 게시물을 수정하였습니다.\n", article.getId());
	}

	private void doActionRemove(Request request) {
		if (Factory.getSession().getLoginedMember() == null) {
			System.out.printf("로그인 후 사용 가능합니다.\n");
		} else if (request.getArg1().length() == 0) {
			System.out.printf("번호를 입력해주세요.\n");
		} else {
			int articleId = Integer.parseInt(request.getArg1());
			int loginedId = Factory.getSession().getLoginedMember().getId();
			
			Article article = articleService.getArticleById(articleId);

			if (article == null) {
				System.out.printf("존재하지 않는 게시물 입니다.\n");
			} else if (Factory.getSession().getLoginedMember().getId() == 1) { 
				doActionDoRemove(article);
			} else if (article.getMemberId() != loginedId) {
				System.out.printf("작성자 본인만 삭제 가능합니다.\n");
			} else {
				doActionDoRemove(article);
			}
		}
	}
	
	private void doActionDoRemove(Article article) {
		articleService.remove(article);
		System.out.printf("%번 게시물을 삭제 하였습니다.\n", article.getId());
	}

	private void doActionList(Request request) {
		if (Factory.getSession().getLoginedMember() == null) {
			System.out.printf("로그인 후 사용 가능합니다.\n");
		} else {
			int pageId = 1;
			String keyword = "";

			try {
				if (request.getArg1().trim().length() > 0) {
					pageId = Integer.parseInt(request.getArg1());
				}
				if (request.getArg2().trim().length() > 0) {
					keyword = request.getArg2();
				}
			} catch (NullPointerException e) {
			}
			/*
			 * # pageId(번호) 입력이 없을 경우 # keyword(문자열) 입력이 없을 경우
			 * 
			 * Integer.parseInt() 매개변수로 null이 들어가기 때문에 NullPointer 예외가 발생하는 것이 당연하다. 자바
			 * 컴파일러는 위험을 감지하고 예외를 발생시키지만 우리는 NullPointer 예외가 발생했을 때 아무런 조치도 취하지 않을 것이다.
			 * 
			 * e.printStackTrace() : 에러 메세지의 발생 근원지를 찾아서 단계별로 출력
			 */

			List<Article> articles = articleService.getArticlesByPageIdAndKeyword(pageId, keyword);
			
			if (articles.isEmpty() == false) {
				int newPageId = (articles.size() - 1) / 10 + 1;

				if (newPageId < pageId) {
					System.out.printf("존재하지 않는 페이지입니다.\n");
				} else {
					System.out.printf("%s|%s|%s|%s\n", "번호", "작성일", "제목", "작성자");

					int start = articles.size() - 1 - (pageId - 1) * 10;
					int end = articles.size() - 10 - (pageId - 1) * 10;

					for (int i = start; i >= end; i--) {
						if (i >= 0) {
							Article article = articles.get(i);
							String writer = Factory.getDB().getMember(article.getMemberId()).getName();

							System.out.printf("%s|%s|%s|%s\n", article.getId(), article.getRegDate(), article.getTitle(), 
									writer);
						}
					}
					System.out.println();
					for (int first = 1; first <= newPageId; first++) {
						if (first == pageId) {
							System.out.printf("[%s] ", first);
						} else {
							System.out.printf("%s ", first);
						}
					}
					System.out.println();
				}
			} else if (articles.isEmpty() == true) {
				System.out.printf("게시물이 존재하지 않습니다.\n");
			}
		}
	}

	private void doActionDetail(Request request) {
		if (Factory.getSession().getLoginedMember() == null) {
			System.out.printf("로그인 후 사용 가능합니다.\n");
		} else if (request.getArg1().length() == 0) {
			System.out.printf("번호를 입력해주세요.\n");
		} else {
			int id = Integer.parseInt(request.getArg1());
			Article article = articleService.getArticleById(id);

			if (article != null) {
				String writer = Factory.getDB().getMember(article.getMemberId()).getNickname();
				System.out.println(article.getId() + "번 게시물 상세보기");
				System.out.printf("제목 : %s\n", article.getTitle());
				System.out.printf("내용 : %s\n", article.getBody());
				System.out.printf("작성자 : %s\n", writer);
				System.out.printf("작성일 : %s\n", article.getRegDate());
				System.out.printf("조회수 : %s\n", article.getHit());
				article.setHit(article.getHit() + 1);
				Factory.getArticleService().write(article);
			} else {
				System.out.printf("%d번 존재하지 않는 게시물입니다.\n", id);
			}
		}
	}
}