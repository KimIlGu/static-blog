package com.kig.java.blog.table;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.kig.java.blog.dto.Article;
import com.kig.java.blog.dto.Board;
import com.kig.java.blog.dto.Dto;
import com.kig.java.blog.util.Factory;
import com.kig.java.blog.util.Util;

public class Table<T> {
	private Class<T> dataCls;
	private String tableName;
	private String tableDirPath;
	
	public Table(Class<T> dataCls, String dbDirPath) {
		this.dataCls = dataCls;
		this.tableName = Util.lcFirst(dataCls.getCanonicalName());
		this.tableDirPath = dbDirPath + "/" + tableName;
		Util.makeDir(tableDirPath);
	}

	public List<T> getRows() {
		int lastId = getLastId();
		
		List<T> rows = new ArrayList<>();
		
		for (int id = 1; id <= lastId; id++) {
			T row = getRow(id);
			
			if (row != null) {
				rows.add(row);
			}
		}
		return rows;
	}
	
	private int getLastId() {
		String filePath = getLastIdFilePath();
		
		if (Util.isFileExists(filePath) == false) {
			int lastId = 0;
			Util.writeFileContents(filePath, lastId);
			return lastId;
		}
		return Integer.parseInt(Util.getFileContents(filePath));
	}
	
	private String getLastIdFilePath() {
		return tableDirPath + "/lastId.txt";
	}
	
	public int saveRow(T data) {
		Dto dto = (Dto) data;
		
		if (dto.getId() == 0) {
			int lastId = getLastId();
			int newId = lastId + 1;
			((Dto) data).setId(newId);
			setLastId(newId);
		}
		
		String rowFilePath = getRowFilePath(dto.getId());
		Util.writeJsonFile(rowFilePath, data);
		
		return dto.getId();
	}

	private void setLastId(int lastId) {
		String filePath = getLastIdFilePath();
		Util.writeFileContents(filePath, lastId);
	}
	
	private String getRowFilePath(int id) {
		return tableDirPath + "/" + id + ".json";
	}
	
	public T getRow(int id) {
		return (T) Util.getObjectFromJson(getRowFilePath(id), dataCls);
	}
	
	public void remove(Article article) {
		String filePath = getRowFilePath(article.getId());

		if (Util.isFileExists(filePath) == false) {
			return;
		}
		File file = new File(filePath);
		file.delete();
	}
	

	public void remove(Board board) {
		String filePath = getRowFilePath(board.getId()); 
		
		for (Article article : Factory.getDB().getArticles()) {
			if (article.getBoardId() == board.getId()) {
				Factory.getDB().remove(article);
			}
		}
		File deleteFile = new File(filePath);
		deleteFile.delete();
	}
}