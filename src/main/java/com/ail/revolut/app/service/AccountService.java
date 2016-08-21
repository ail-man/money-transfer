package com.ail.revolut.app.service;

import com.ail.revolut.app.model.Account;

// TODO move business logic like createAccount() to AccountEntityManager
// TODO develop AccountService, that will have business logic from Account class and this createAccount() method
public interface AccountService {

	Account createAccount();

	Account findAccount(Long id);
}
