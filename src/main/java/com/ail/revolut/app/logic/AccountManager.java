package com.ail.revolut.app.logic;

import java.math.BigDecimal;

public interface AccountManager {

	Long createAccount() throws Exception;

	BigDecimal getBalance(Long id) throws Exception;

	void deposit(Long id, BigDecimal amount) throws Exception;

	void withdraw(Long id, BigDecimal amount) throws Exception;

}
