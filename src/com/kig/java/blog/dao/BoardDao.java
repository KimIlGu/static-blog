package com.kig.java.blog.dao;

import java.util.List;

import com.kig.java.blog.db.DB;
import com.kig.java.blog.dto.Board;
import com.kig.java.blog.util.Factory;

public class BoardDao {
	private DB db;
	
	public BoardDao() {
		db = Factory.getDB();
	}
	
	public Board getBoardByNameAndCode(String name, String code) {
		return db.getBoardByNameAndCode(name, code);
	}
	
	public int save(Board board) {
		return db.save(board);
	}

	public Board getBoardByCode(String code) {
		return db.getBoardByCode(code);
	}

	public void remove(Board board) {
		db.remove(board);
	}

	public List<Board> getBoards() {
		return db.getBoards();
	}
}