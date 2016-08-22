package com.ail.revolut.app.logic;

import com.ail.revolut.app.model.User;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

public class UserManagerTest {

	@Test
	@Ignore
	// TODO
	public void testCreateUser() {
		UserManager userManager = mock(UserManager.class);

		User user = userManager.createUser("UserName");

		User found = userManager.getUser(user.getId());

		assertThat(found.getId(), equalTo(user.getId()));
		assertThat(found.getName(), equalTo(user.getName()));
	}

	@Test
	@Ignore
	// TODO
	public void testGetUser() {
	}
}
