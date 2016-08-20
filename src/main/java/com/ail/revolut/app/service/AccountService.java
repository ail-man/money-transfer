package com.ail.revolut.app.service;

import com.ail.revolut.app.logic.IAccount;

public interface AccountService {

	IAccount createAccount();

	IAccount findAccount(Long id);
}
