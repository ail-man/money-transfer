package com.ail.revolut.app.bank;

public enum Currency {
	RUB(643, "63.828"),
	RUR(810, "63828"),
	USD(840, "1"),
	EUR(978, "0.942");

	private final int code;
	private final String rate;

	Currency(int code, String rate) {
		this.code = code;
		this.rate = rate;
	}

	public static Currency defaultCurrency() {
		return USD;
	}

	public int getCode() {
		return code;
	}

	public String getRate() {
		return rate;
	}
}
