package com.xcplm.tc.rac.util;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCReservationService;
import com.teamcenter.rac.kernel.TCSession;

public class ReservationUtil {

	private static final AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
	private static final TCSession session = (TCSession) application.getSession();
	private static TCReservationService resService = session.getReservationService();

	public static void reserve(TCComponent component) throws TCException {
		resService.reserve(component);
	}

	public static void unReserve(TCComponent component) throws TCException {
		resService.unreserve(component);
	}

	public static void cancelReservation(TCComponent component) throws TCException {
		resService.cancelReservation(component);
	}

}
