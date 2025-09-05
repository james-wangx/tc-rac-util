package com.codicefun.tc.rac.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCSession;

public class TestHandler extends AbstractHandler {

	private static final AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
	private static final TCSession session = (TCSession) application.getSession();

	@Override
	public Object execute(ExecutionEvent event) {
		return null;
	}

}
