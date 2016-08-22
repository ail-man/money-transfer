package com.ail.revolut.app.exception;

public class NotEnoughFundsException extends Exception {
	private static final long serialVersionUID = 1L;

	public NotEnoughFundsException(String message) {
		super(message);
	}
}
