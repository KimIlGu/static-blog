package com.kig.java.blog.controller;

import com.kig.java.blog.service.BuildService;
import com.kig.java.blog.util.Factory;
import com.kig.java.blog.util.Request;

public class BuildController extends Controller {
	private BuildService buildService;

	public BuildController() {
		buildService = Factory.getBuildService();
	}

	@Override
	public void doAction(Request request) {
		if (request.getActionName().equals("site")) {
			actionSite(request);
		} else if (request.getActionName().equals("startAutoSite")) {
			actionStartAutoSite(request);
		} else if (request.getActionName().equals("stopAutoSite")) {
			actionStopAutoSite(request);
		}
	}

	private void actionSite(Request request) {
		buildService.buildSite();
		System.out.println("게시물 수동 생성");
	}
	
	private void actionStartAutoSite(Request request) {
		System.out.println("=========================== Auto ===========================");
		buildService.buildStartAutoSite();
	}

	private void actionStopAutoSite(Request request) {
		System.out.println("=========================== Manual ===========================");
		buildService.buildStropAutoSite();
	}
}