package com.kig.java.blog.dao;

import com.kig.java.blog.db.DB;
import com.kig.java.blog.dto.Member;
import com.kig.java.blog.util.Factory;

public class MemberDao {
	private DB db;

	public MemberDao() {
		db = Factory.getDB();
	}

	public Member getMemberByLoginId(String loginId) {
		return db.getMemberByLoginId(loginId);
	}

	public void save(Member member) {
		db.save(member);
	}

	public Member getMemberByLoginIdAndLoginPw(String loginId, String loginPw) {
		return db.getMemberByLoginIdAndLoginPw(loginId, loginPw);
	}
}