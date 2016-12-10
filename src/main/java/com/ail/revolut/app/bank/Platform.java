package com.ail.revolut.app.bank;

public class Platform {

	public static Bank createBank() {
		return BankImpl.create();
	}

}
