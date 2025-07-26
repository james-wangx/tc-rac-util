package com.codicefun.tc.rac.util;

import java.util.Optional;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCPreferenceService;
import com.teamcenter.rac.kernel.TCSession;

public class PreferenceUtil {

	private static final AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
	private static final TCSession session = (TCSession) application.getSession();
	private static final TCPreferenceService prefService = session.getPreferenceService();

	public static Optional<String> getStringValue(String prefName) {
		return Optional.ofNullable(prefService.getStringValue(prefName));
	}

	public static Optional<String[]> getStringValues(String prefName) {
		return Optional.ofNullable(prefService.getStringValues(prefName));
	}

}
