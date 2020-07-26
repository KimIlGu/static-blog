package com.kig.java.blog.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.kig.java.blog.controller.ArticleController;
import com.kig.java.blog.controller.BoardController;
import com.kig.java.blog.controller.BuildController;
import com.kig.java.blog.controller.Controller;
import com.kig.java.blog.controller.MemberController;
import com.kig.java.blog.util.Factory;
import com.kig.java.blog.util.Request;

public class App {
	private Map<String, Controller> controllers;

	private void initController() {
		controllers = new HashMap<>();
		controllers.put("build", new BuildController());
		controllers.put("board", new BoardController());
		controllers.put("article", new ArticleController());
		controllers.put("member", new MemberController());
	}
	
	App() {
		initController();
		Factory.getBoardService().create("공지사항", "notice");
		Factory.getBoardService().create("자유게시판", "free");
		Factory.getMemberService().join("admin", "admin", "김일구", "관리자");
		Factory.getSession().setCurrentBoard(Factory.getDB().getBoard(1));
	}
	public void start() {
		
		while(true) {
			System.out.printf("명령어 : ");
			String command = Factory.getScanner().nextLine().trim();
			
			if (command.length() == 0) {
				continue;
			}
			
			if (command.equals("exit")) {
				System.out.println("프로그램 종료!");
				break;
			}
			
			Request request = new Request(command);
			
			if (controllers.containsKey(request.getControllerName()) == false) {
				continue;
			}
			
			if (request.isValidRequest() == false) {
				continue;
			}
			
			controllers.get(request.getControllerName()).doAction(request);
		}
		Factory.getScanner().close();
	}
}