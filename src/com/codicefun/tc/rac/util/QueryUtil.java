package com.codicefun.tc.rac.util;

import java.util.Optional;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentQuery;
import com.teamcenter.rac.kernel.TCComponentQueryType;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;

public class QueryUtil {

	private static final AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
	private static final TCSession session = (TCSession) application.getSession();

	public static Optional<TCComponent[]> executeQuery(String queryName, String[] entryNames, String[] entryValues) {
		try {
			TCComponentQueryType queryType = (TCComponentQueryType) session.getTypeComponent("ImanQuery");
			TCComponentQuery query = (TCComponentQuery) queryType.find(queryName);

			if (query == null) {
				return Optional.empty();
			}

			return Optional.ofNullable(query.execute(entryNames, entryValues));
		} catch (TCException e) {
			return Optional.empty();
		}

	}

}
