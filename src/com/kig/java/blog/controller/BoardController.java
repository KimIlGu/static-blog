package com.kig.java.blog.controller;

import java.util.List;

import com.kig.java.blog.dto.Board;
import com.kig.java.blog.service.BoardService;
import com.kig.java.blog.util.Factory;
import com.kig.java.blog.util.Request;

public class BoardController extends Controller {
	private BoardService boardService;

	public BoardController() {
		boardService = Factory.getBoardService();
	}

	@Override
	public void doAction(Request request) {
		if (request.getActionName().equals("create")) {
			doActionCreate();
		} else if (request.getActionName().equals("remove")) {
			doACtionRemove(request);
		} else if (request.getActionName().equals("list")) {
			doACtionList(request);
		} else if (request.getActionName().equals("change")) {
			doACtionChange(request);
		}
	}

	private void doActionCreate() {
		if (Factory.getSession().getLoginedMember() == null) {
			System.out.printf("로그인 후 사용 가능합니다.\n");
		} else if (Factory.getSession().getLoginedMember().getId() != 1) {
			System.out.printf("권한이 없습니다.\n");
		} else {
			System.out.printf("이름 : ");
			String name = Factory.getScanner().nextLine().trim();
			System.out.printf("코드 : ");
			String code = Factory.getScanner().nextLine().trim();
			
			Board board = boardService.create(name, code);

			if (board == null) {
				System.out.printf("이미 존재하는 게시판 입니다.\n");
			} else {
				System.out.printf("%d번 게시판을 생성하였습니다.\n", board.getId());
			}
		}
	}

	private void doACtionRemove(Request request) {
		if (Factory.getSession().getLoginedMember() == null) {
			System.out.printf("로그인 후 사용 가능합니다.\n");
		} else if (Factory.getSession().getLoginedMember().getId() != 1) {
			System.out.printf("권한이 없습니다.\n");
		} else {
			String code = request.getArg1();
			if (code != null) {
				Board board = boardService.remove(code);
			
				if (board == null) {
					System.out.printf("존재하지 않는 게시판 입니다.\n");
				} else {
					System.out.printf("%d번 게시판을 삭제하였습니다.\n", board.getId());
				}
			} else {
				System.out.printf("코드를 입력해주세요.\n");
			}
		}
	}

	private void doACtionList(Request request) {
		if (Factory.getSession().getLoginedMember() == null) {
			System.out.printf("로그인 후 사용 가능합니다.\n");
		} else {
			int pageId = 1;
			
			if (request.getArg1() != null) {
				pageId = Integer.parseInt(request.getArg1());
			}
			
			List<Board> boards = boardService.getBoards();
			
			if (boards.isEmpty() == false) {
				int newPageId = (boards.size() - 1) / 10 + 1;
	
				if (newPageId < pageId) {
					System.out.printf("페이지가 존재하지 않습니다.\n");
				} else {
				
					System.out.printf("%s|%s|%s|%s\n", "번호", "생성날짜", "이름", "코드");
		
					int start = boards.size() - 1 - (pageId - 1) * 10;
					int end = boards.size() - 10 - (pageId - 1) * 10;
		
					for (int i = start; i >= end; i--) {
						if (i >= 0) {
							Board board = boards.get(i);
							System.out.printf("%d|%s|%s|%s\n", board.getId(), board.getRegDate(), board.getName(), board.getCode());
					
						}
					}
					System.out.println();
					
					for (int first = 1; first <= newPageId; first++) {
						if (first == pageId) {
							System.out.printf("[%d] ", first);
						} else {
							System.out.printf("%d ", first);
						}
					}
					System.out.println();
				}
			} else if (boards.isEmpty() == true){
				System.out.printf("게시판이 존재하지 않습니다.\n");
			}
		}
	}

	private void doACtionChange(Request request) {
		if (Factory.getSession().getLoginedMember() == null) {
			System.out.printf("로그인 후 사용 가능합니다.\n");
		} else {
			String code = request.getArg1();
	
			if (code != null) {
				Board board = Factory.getBoardDao().getBoardByCode(code);
	
				if (board != null) {
					String currentBoardName = Factory.getSession().getCurrentBoard().getName();
					String currentBoardCode = Factory.getSession().getCurrentBoard().getCode();
					System.out.println(currentBoardCode);
					System.out.println(board.getCode());
					if (currentBoardCode.equals(board.getCode())) {
						System.out.printf("현재 이미 %s 게시판 입니다.\n", currentBoardName);
					} else {
						Factory.getSession().setCurrentBoard(board);
						System.out.printf("%s 게시판으로 이동하였습니다.\n", board.getName());
					}
				} else {
					System.out.printf("게시판이 존재하지 않습니다.\n");
				}
			} else {
				System.out.printf("코드를 입력해주세요.\n");
			}
		}
	}
}