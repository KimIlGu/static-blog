package com.kig.java.blog.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kig.java.blog.dto.Member;
import com.kig.java.blog.service.MemberService;
import com.kig.java.blog.util.Factory;
import com.kig.java.blog.util.Request;

public class MemberController extends Controller {
	private MemberService memberService;

	public MemberController() {
		memberService = Factory.getMemberService();
	}

	@Override
	public void doAction(Request request) {
		if (request.getActionName().equals("join")) {
			doActionJoin();
		} else if (request.getActionName().equals("login")) {
			doActionLogin();
		} else if (request.getActionName().equals("logout")) {
			doActionLogout();
		} else if (request.getActionName().equals("whoami")) {
			doActionWhoami();
		}
	}

	private void doActionJoin() {
		String loginId;
		String loginPw;
		String loginPwConfirm;
		String name;
		String nickname;

		String pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{9,12}$";
		String pwPattern2 = "(.)\\1\\1\\1";

		while (true) {
			System.out.printf("아이디 : ");
			loginId = Factory.getScanner().nextLine().trim();

			if (loginId.length() == 0) {
				System.out.printf("아이디를 입력해주세요.\n");
				continue;
			} else if (loginId.length() < 4) {
				System.out.printf("아이디를 4자 이상 입력해주세요.\n");
				continue;
			} else if (memberService.isUsedLoginedId(loginId)) {
				System.out.printf("현재 사용중인 아이디 입니다.\n");
				continue;
			}
			break;
		}

		while (true) {
			while (true) {
				System.out.printf("비밀번호 : ");
				loginPw = Factory.getScanner().nextLine().trim();

				Matcher matcher = Pattern.compile(pwPattern).matcher(loginPw);
				Matcher matcher2 = Pattern.compile(pwPattern2).matcher(loginPw);

				if (loginPw.length() == 0) {
					System.out.printf("비밀번호를 입력해주세요.\n");
					continue;
				} else if (loginPw.contains(" ")) {
					System.out.printf("비밀번호 중간에 공백 사용할 수 없습니다.\n");
					continue;
				} else if (matcher2.find()) {
					System.out.printf("같은문자는 4개 이상 사용할 수 없습니다.\n");
					continue;
				} else if (loginPw.contains(loginId)) {
					System.out.printf("아이디는 비밀번호에 사용할 수 없습니다.\n");
					continue;
				} else if (!matcher.matches()) {
					System.out.printf("비밀번호는 영문 대/소문자, 숫자, 특수문자 포함, 9~12자리 입니다.\n");
					continue;
				}
				break;
			}

			while (true) {
				System.out.printf("비밀번호 확인 : ");
				loginPwConfirm = Factory.getScanner().nextLine().trim();

				if (loginPwConfirm.length() == 0) {
					System.out.printf("비밀번호를 입력해주세요.\n");
					continue;
				} else if (loginPwConfirm.equals(loginPw) == false) {
					System.out.printf("비밀번호와 일치하지 않습니다.\n");
					continue;
				}
				break;
			}
			break;
		}

		while (true) {
			System.out.printf("이름 : ");
			name = Factory.getScanner().nextLine().trim();

			if (name.length() == 0) {
				System.out.printf("이름을 입력해주세요.\n");
				continue;
			}
			break;
		}

		while (true) {
			System.out.printf("닉네임 : ");
			nickname = Factory.getScanner().nextLine().trim();

			if (nickname.length() == 0) {
				System.out.printf("닉네임을 입력해주세요.\n");
				continue;
			}
			break;
		}
		memberService.join(loginId, loginPw, name, nickname);
	}

	private void doActionLogin() {
		if (Factory.getSession().getLoginedMember() != null) {
			System.out.printf("현재 로그인 상태입니다.\n");
		} else {
			System.out.printf("아이디 : ");
			String loginId = Factory.getScanner().nextLine().trim();

			System.out.printf("비밀번호 : ");
			String loginPw = Factory.getScanner().nextLine().trim();

			Member member = memberService.getMemberByLoginIdAndLoginPw(loginId, loginPw);

			if (member == null) {
				System.out.printf("일치하는 회원이 없습니다.\n");
			} else {
				Factory.getSession().setLoginedMember(member);
				System.out.printf("%s님 로그인 하셨습니다.\n", member.getName());
			}
		}
	}

	private void doActionLogout() {
		Member loginedMember = Factory.getSession().getLoginedMember();

		if (loginedMember == null) {
			System.out.printf("현재 로그아웃 상태입니다.\n");
		} else {
			Factory.getSession().setLoginedMember(null);
			System.out.printf("로그아웃 성공 하였습니다.\n");
		}
	}

	private void doActionWhoami() {
		Member loginedMember = Factory.getSession().getLoginedMember();
		
		if (loginedMember == null) {
			System.out.printf("로그인 후 사용 가능합니다.\n");
		} else {
			System.out.printf("회원명 : %s\n", loginedMember.getName());
		}
	}
}