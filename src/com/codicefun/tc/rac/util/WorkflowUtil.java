package com.codicefun.tc.rac.util;

import java.util.Optional;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCAttachmentType;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentProcess;
import com.teamcenter.rac.kernel.TCComponentProcessType;
import com.teamcenter.rac.kernel.TCComponentTaskTemplate;
import com.teamcenter.rac.kernel.TCComponentTaskTemplateType;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;

public class WorkflowUtil {

	private static AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
	private static TCSession session = (TCSession) application.getSession();

	public static Optional<TCComponentProcess> create(String templateName, TCComponent[] attachments) {
		try {
			TCComponentProcessType processType = (TCComponentProcessType) session.getTypeComponent("Job");
			TCComponentTaskTemplateType taskTemplateType = (TCComponentTaskTemplateType) session
					.getTypeComponent("EPMTaskTemplate");
			TCComponentTaskTemplate processTemplate = taskTemplateType.find(templateName, 0);

			if (processTemplate == null) {
				return Optional.empty();
			}

			int[] attachmentTypes = new int[attachments.length];
			for (int i = 0; i < attachmentTypes.length; i++) {
				attachmentTypes[i] = TCAttachmentType.TARGET;
			}
			return Optional.ofNullable((TCComponentProcess) processType.create(templateName, "", processTemplate,
					attachments, attachmentTypes, null));
		} catch (TCException e) {
			e.printStackTrace();
			return Optional.empty();
		}

	}

}
