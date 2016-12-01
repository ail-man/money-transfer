package com.ail.revolut.app.bank;

import java.math.BigDecimal;

public enum Currency {
	RUB(643, new BigDecimal("63.828")),
	RUR(810, new BigDecimal("63828")),
	USD(840, new BigDecimal("1")),
	EUR(978, new BigDecimal("0.942"));

	private final int code;
	private final BigDecimal rate;

	Currency(int code, BigDecimal rate) {
		this.code = code;
		this.rate = rate;
	}

	public int getCode() {
		return code;
	}

	public BigDecimal getRate() {
		return rate;
	}
}
