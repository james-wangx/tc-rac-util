package com.codicefun.tc.rac.exception;

import java.lang.Thread.UncaughtExceptionHandler;

public class TestExceptionHandler implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		if (e instanceof TestException) {
			System.out.println(
					"Global catch BusinessException in thread: " + t.getName() + ", message: {}" + e.getMessage());
		} else {
			System.out.println("Uncaught exception in thread: " + t.getName() + ", message: {}" + e.getMessage());
		}

		e.printStackTrace();
	}

}
