package com.kig.java.blog.service;

import java.util.List;

import com.kig.java.blog.dao.BoardDao;
import com.kig.java.blog.dto.Board;
import com.kig.java.blog.util.Factory;

public class BoardService {
	private BoardDao boardDao;
	
	public BoardService() {
		boardDao = Factory.getBoardDao();
	}
	
	public Board create(String name, String code) {
		Board oldBoard = boardDao.getBoardByNameAndCode(name, code);
		
		if (oldBoard != null) {
			return null;
		}
		
		Board board = new Board(name, code);
		boardDao.save(board);
		
		return board;
	}

	public Board remove(String code) {
		Board oldBoard = boardDao.getBoardByCode(code);
		
		if (oldBoard != null) {
			boardDao.remove(oldBoard);
			
			return oldBoard;
		}
		
		return null;
	}

	public List<Board> getBoards() {
		return boardDao.getBoards();
	}
}