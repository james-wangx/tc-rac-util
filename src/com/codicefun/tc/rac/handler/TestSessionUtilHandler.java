package com.codicefun.tc.rac.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.codicefun.tc.rac.exception.TestException;
import com.codicefun.tc.rac.util.SessionUtil;

public class TestSessionUtilHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("Start TestSessionUtilHandler");

		testGetUser();
		testGetCurrentGroup();
		testGetCurrentRole();
		testGetHomeFolder();
		testGetNewStuffFolder();
		testGetMailBox();
		testGetUserInBox();
		testGetTcRootPath();
		testGetTcBinPath();
		testGetTcPortalPath();
		testGetTcPluginssPath();

		return null;
	}

	public static void testGetUser() {
		SessionUtil	.getUser()
					.orElseThrow(() -> new TestException("Not found user"));
	}

	public static void testGetCurrentGroup() {
		SessionUtil	.getCurrentGroup()
					.orElseThrow(() -> new TestException("Not found group"));
	}

	public static void testGetCurrentRole() {
		SessionUtil	.getCurrentRole()
					.orElseThrow(() -> new TestException("Not found role"));
	}

	public static void testGetHomeFolder() {
		SessionUtil	.getHomeFolder()
					.orElseThrow(() -> new TestException("Not found home folder"));
	}

	public static void testGetNewStuffFolder() {
		SessionUtil	.getNewStuffFolder()
					.orElseThrow(() -> new TestException("Not found new stuff folder"));
	}

	public static void testGetMailBox() {
		SessionUtil	.getMailBox()
					.orElseThrow(() -> new TestException("Not found mail box"));
	}

	public static void testGetUserInBox() {
		SessionUtil	.getUserInBox()
					.orElseThrow(() -> new TestException("Not found user in box"));
	}

	public static void testGetTcRootPath() {
		String tcRootPath = SessionUtil	.getTcRootPath()
										.orElseThrow(() -> new TestException("Not found tc root path"));
		System.out.println("tcRootPath = " + tcRootPath);
	}

	public static void testGetTcBinPath() {
		String tcBinPath = SessionUtil	.getTcBinPath()
										.orElseThrow(() -> new TestException("Not found tc bin path"));
		System.out.println("tcBinPath = " + tcBinPath);
	}

	public static void testGetTcPortalPath() {
		String tcPortalPath = SessionUtil	.getTcPortalPath()
											.orElseThrow(() -> new TestException("Not found tc portal path"));
		System.out.println("tcPortalPath = " + tcPortalPath);
	}

	public static void testGetTcPluginssPath() {
		String tcPluginsPath = SessionUtil	.getTcPluginsPath()
											.orElseThrow(() -> new TestException("Not found tc plugins path"));
		System.out.println("tcPluginsPath = " + tcPluginsPath);
	}

}
