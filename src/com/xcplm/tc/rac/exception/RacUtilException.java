package com.xcplm.tc.rac.exception;

public class RacUtilException extends RuntimeException {

	public RacUtilException() {
	}

	public RacUtilException(String message) {
		super(message);
	}

	public RacUtilException(Throwable cause) {
		super(cause);
	}

	public RacUtilException(String message, Throwable cause) {
		super(message, cause);
	}

	public RacUtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
