package com.xcplm.tc.rac.util;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCAccessControlService;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;

public class AccessControlUtil {

	private static final AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
	private static final TCSession session = (TCSession) application.getSession();
	private static final TCAccessControlService acService = session.getTCAccessControlService();

	public static boolean checkPrivileges(TCComponent component, String[] access) {
		boolean[] accessRight = null;

		try {
			TCComponent[] accessors = { session.getUser(), session.getGroup(), session.getRole() };
			accessRight = acService.checkAccessorsPrivileges(accessors, component, access);
		} catch (TCException e) {
			e.printStackTrace();
		}

		return accessRight[0];
	}

	public static boolean checkWritePrivilege(TCComponent component) {
		return checkPrivileges(component, new String[] { "WRITE" });
	}

}
