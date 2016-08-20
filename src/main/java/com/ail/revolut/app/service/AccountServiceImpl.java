package com.ail.revolut.app.service;

import com.ail.revolut.app.model.Account;

public class AccountServiceImpl implements AccountService {

	@Override
	public Account createAccount() {
		return new Account();
	}
}
