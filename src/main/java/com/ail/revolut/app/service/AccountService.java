package com.ail.revolut.app.service;

import com.ail.revolut.app.api.IAccount;

public interface AccountService {

	IAccount createAccount();

	IAccount findAccount(Long id);
}
