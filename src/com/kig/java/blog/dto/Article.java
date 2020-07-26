package com.kig.java.blog.dto;

public class Article extends Dto {
	private String title;
	private String body;
	private int hit;
	private int boardId;
	private int memberId;
	
	public Article() {
		
	}
	
	public Article(int boardId, int memberId, String title, String body) {
		this.title = title;
		this.body = body;
		this.boardId = boardId;
		this.memberId = memberId;
	}
	
	@Override
	public String toString() {
		return "Article [title=" + title + ", body=" + body + ", hit=" + hit + ", boardId=" + boardId + ", memberId="
				+ memberId + ", getId()=" + getId() + ", getRegDate()=" + getRegDate() + "]";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getHit() {
		return hit;
	}
	
	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getBoardId() {
		return boardId;
	}

	public int getMemberId() {
		return memberId;
	}
}