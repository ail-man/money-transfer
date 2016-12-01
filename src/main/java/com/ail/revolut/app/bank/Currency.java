package com.ail.revolut.app.bank;

public enum Currency {
	RUB(643),
	RUR(810),
	USD(840),
	EUR(978);

	private final int code;

	Currency(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
