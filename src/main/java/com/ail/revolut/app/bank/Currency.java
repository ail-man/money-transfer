package com.ail.revolut.app.bank;

public enum Currency {
	RUR(840);

	private final int code;

	Currency(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
