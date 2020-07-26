package com.kig.java.blog.dto;

public class Member extends Dto {
	private String loginId;
	private String loginPw;
	private String name;
	private String nickname;
	
	public Member() {
		
	}
	
	public Member(String loginId, String loginPw, String name, String nickname) {
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.name = name;
		this.nickname = nickname;
	}

	public final String getLoginId() {
		return loginId;
	}

	public final String getLoginPw() {
		return loginPw;
	}

	public final String getName() {
		return name;
	}

	public final String getNickname() {
		return nickname;
	}
}