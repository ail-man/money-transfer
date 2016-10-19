package com.ail.revolut.app.logic;

import com.ail.revolut.app.model.User;

public interface UserManager {
	User createUser(String userName);

	User getUser(Long id);
}
