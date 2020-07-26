package com.kig.java.blog.dto;

public class Board extends Dto {
	private String name;
	private String code;
	
	public Board() {
		
	}
	
	public Board(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}
}