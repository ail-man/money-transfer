package com.ail.revolut.app.api;


import com.ail.revolut.app.model.Account;

import java.util.List;

public interface IUser {

	Long getId();

	String getName();

	List<Account> getAccounts();
}
