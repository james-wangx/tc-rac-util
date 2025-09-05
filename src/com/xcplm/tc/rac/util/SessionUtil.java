package com.xcplm.tc.rac.util;

import java.io.File;
import java.util.Optional;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentGroup;
import com.teamcenter.rac.kernel.TCComponentRole;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;

public class SessionUtil {

	private static final AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
	private static final TCSession session = (TCSession) application.getSession();

	public static Optional<TCComponentUser> getUser() {
		return Optional.ofNullable(session.getUser());
	}

	public static Optional<TCComponentGroup> getCurrentGroup() {
		return Optional.ofNullable(session.getCurrentGroup());
	}

	public static Optional<TCComponentRole> getCurrentRole() {
		return Optional.ofNullable(session.getCurrentRole());
	}

	public static Optional<TCComponentFolder> getHomeFolder() {
		Optional<TCComponentUser> userOpt = getUser();
		if (!userOpt.isPresent()) {
			return Optional.empty();
		}

		try {
			TCComponentUser user = userOpt.get();
			return Optional.ofNullable(user.getHomeFolder());
		} catch (TCException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public static Optional<TCComponentFolder> getNewStuffFolder() {
		Optional<TCComponentUser> userOpt = getUser();
		if (!userOpt.isPresent()) {
			return Optional.empty();
		}

		try {
			TCComponentUser user = userOpt.get();
			return Optional.ofNullable(user.getNewStuffFolder());
		} catch (TCException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public static Optional<TCComponentFolder> getMailBox() {
		Optional<TCComponentUser> userOpt = getUser();
		if (!userOpt.isPresent()) {
			return Optional.empty();
		}

		try {
			TCComponentUser user = userOpt.get();
			return Optional.ofNullable(user.getMailBox());
		} catch (TCException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public static Optional<TCComponent> getUserInBox() {
		Optional<TCComponentUser> userOpt = getUser();
		if (!userOpt.isPresent()) {
			return Optional.empty();
		}

		try {
			TCComponentUser user = userOpt.get();
			return Optional.ofNullable(user.getUserInBox());
		} catch (TCException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public static Optional<String> getTcRootPath() {
		try {
			return Optional.ofNullable(session.getServerConfigInfo()[3]);
		} catch (TCException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public static Optional<String> getTcBinPath() {
		Optional<String> tcRootPathOpt = getTcRootPath();
		if (!tcRootPathOpt.isPresent()) {
			return Optional.empty();
		}
		String tcRootPath = tcRootPathOpt.get();
		if (tcRootPath.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(tcRootPath + File.separator + "bin");
	}

	public static Optional<String> getTcPortalPath() {
		Optional<String> tcRootPathOpt = getTcRootPath();
		if (!tcRootPathOpt.isPresent()) {
			return Optional.empty();
		}
		String tcRootPath = tcRootPathOpt.get();
		if (tcRootPath.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(tcRootPath + File.separator + "portal");
	}

	public static Optional<String> getTcPluginsPath() {
		Optional<String> tcPortalPathOpt = getTcPortalPath();
		if (!tcPortalPathOpt.isPresent()) {
			return Optional.empty();
		}
		String tcPortalPath = tcPortalPathOpt.get();
		if (tcPortalPath.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(tcPortalPath + File.separator + "plugins");
	}

}
