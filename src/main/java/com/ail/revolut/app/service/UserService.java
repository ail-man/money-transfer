package com.ail.revolut.app.service;

import com.ail.revolut.app.model.User;

public interface UserService {
	User createUser(String userName);

	User getUser(Long id);
}

