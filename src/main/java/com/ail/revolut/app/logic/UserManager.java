package com.ail.revolut.app.logic;

import com.ail.revolut.app.model.User;

public interface UserManager {

	User createUser(String userName) throws Exception;

	User getUser(Long id) throws Exception;

}
