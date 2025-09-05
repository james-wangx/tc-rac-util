package com.xcplm.tc.rac.handler;

import java.util.Arrays;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.xcplm.tc.rac.exception.TestException;
import com.xcplm.tc.rac.util.PreferenceUtil;

public class TestPreferenceUtilHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("Start TestPreferenceUtilHandler");

		testGetPreferenceValue();
		testGetPreferenceValues();

		return null;
	}

	public static void testGetPreferenceValue() {
		// Single
		String location = PreferenceUtil.getStringValue("Transient_Volume_Installation_Location")
				.orElseThrow(() -> new TestException("Not found preference"));
		System.out.println("location = " + location);

		// Multiple
		String icsTypes = PreferenceUtil.getStringValue("ICS_classifiable_types")
				.orElseThrow(() -> new TestException("Not found preference"));
		System.out.println("icsTypes = " + icsTypes);
	}

	public static void testGetPreferenceValues() {
		// Multiple
		String[] rootDir = PreferenceUtil.getStringValues("Transient_Volume_RootDir")
				.orElseThrow(() -> new TestException("Not found preference"));
		System.out.println("rootDir = " + Arrays.toString(rootDir));

		// Single
		String[] icsTypes = PreferenceUtil.getStringValues("ICS_classifiable_types")
				.orElseThrow(() -> new TestException("Not found preference"));
		System.out.println("icsTypes = " + Arrays.toString(icsTypes));
	}

}
