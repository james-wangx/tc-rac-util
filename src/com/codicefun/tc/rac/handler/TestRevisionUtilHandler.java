package com.codicefun.tc.rac.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.codicefun.tc.rac.exception.TestException;
import com.codicefun.tc.rac.util.RevisionUtil;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;

public class TestRevisionUtilHandler extends AbstractHandler {

	private static final AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();

	private static final TCComponentItemRevision selectedRevision;
	static {
		TCComponent component = (TCComponent) application.getTargetComponent();
		if (!(component instanceof TCComponentItemRevision)) {
			throw new TestException("Found target component not item revision");
		}
		selectedRevision = (TCComponentItemRevision) component;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("Start TestRevisionUtilHandler");

		testRevise();

		return null;
	}

	public static void testRevise() {
		try {
			TCComponentItemRevision revision = RevisionUtil	.revise(selectedRevision, null, "Test", "")
															.orElseThrow(() -> new TestException("Test revise failed"));
			String revisionId = revision.getStringProperty("item_revision_id");
			System.out.println("Revise revision id: " + revisionId);
		} catch (TCException e) {
			e.printStackTrace();
		}
	}

}
