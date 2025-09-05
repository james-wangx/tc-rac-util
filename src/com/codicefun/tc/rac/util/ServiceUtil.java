package com.codicefun.tc.rac.util;

import java.util.Arrays;

import com.teamcenter.soa.client.model.ServiceData;

/**
 * Utility class for service operations
 * 
 * TODO: Use log4j or logback
 */
public class ServiceUtil {

	/**
	 * Catch partial errors
	 *
	 * @param serviceData service data
	 * @return true if there are partial errors
	 */
	public static boolean catchPartialErrors(ServiceData serviceData) {
		if (serviceData == null) {
			System.out.println("Catch partial error: serviceData is null");
			return true;
		}

		if (serviceData.sizeOfPartialErrors() > 0) {
			for (int i = 0; i < serviceData.sizeOfPartialErrors(); i++) {
				System.out.println(
						"Catch partial error: " + Arrays.toString(serviceData.getPartialError(i).getMessages()));
			}
			return true;
		}

		return false;
	}

}
