package com.ail.revolut.app.service;

import com.ail.revolut.app.logic.IAccount;

public interface TransferService {

	void transfer(IAccount from, IAccount to, long amount);
}
