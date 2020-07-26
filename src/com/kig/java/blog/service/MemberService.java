package com.kig.java.blog.service;

import com.kig.java.blog.dao.MemberDao;
import com.kig.java.blog.dto.Member;
import com.kig.java.blog.util.Factory;

public class MemberService {
	private MemberDao memberDao;

	public MemberService() {
		memberDao = Factory.getMemberDao();
	}

	public boolean isUsedLoginedId(String loginId) {
		Member oldMember = memberDao.getMemberByLoginId(loginId);

		if (oldMember == null) {
			return false;
		}
		return true;
	}

	public void join(String loginId, String loginPw, String name, String nickname) {
		Member member = new Member(loginId, loginPw, name, nickname);
		memberDao.save(member);
	}

	public Member getMemberByLoginIdAndLoginPw(String loginId, String loginPw) {
		return memberDao.getMemberByLoginIdAndLoginPw(loginId, loginPw); 
	}
}