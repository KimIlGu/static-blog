package com.kig.java.blog.util;

public class Request {
	private String requestStr;
	private String controllerName;
	private String actionName;
	private String arg1;
	private String arg2;
	
	public Request(String requestStr) {
		this.requestStr = requestStr;
		String[] requestStrBits = requestStr.split(" ");
		controllerName = requestStrBits[0];
		
		if (requestStrBits.length > 1) {
			actionName = requestStrBits[1];
		} 
		
		if (requestStrBits.length > 2) {
			arg1 = requestStrBits[2];
		}
		
		if (requestStrBits.length > 3) {
			arg2 = requestStrBits[3];
		}
	}
	
	public boolean isValidRequest() {
		return actionName != null;
	}

	public String getRequestStr() {
		return requestStr;
	}

	public String getControllerName() {
		return controllerName;
	}

	public String getActionName() {
		return actionName;
	}

	public String getArg1() {
		return arg1;
	}

	public String getArg2() {
		return arg2;
	}
}

// 1. requestStr 변수를 만든 이유는 무엇입니까?
// 일단 Request 객체를 생성한 메소드에서 전체 명령어를 사용가능케 하려고