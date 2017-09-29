package com.feicent.zhang.spring.exception;

public class ParameterException extends RuntimeException{

	private static final long serialVersionUID = 4489451737170060521L;

	public ParameterException() {
		super();
	}

	public ParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParameterException(String message) {
		super(message);
	}

	public ParameterException(Throwable cause) {
		super(cause);
	}

}
