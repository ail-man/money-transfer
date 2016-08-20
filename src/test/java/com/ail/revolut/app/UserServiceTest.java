package com.ail.revolut.app;

import com.ail.revolut.app.model.User;
import com.ail.revolut.app.service.UserService;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

public class UserServiceTest {

	@Test
	@Ignore
	public void testCreateUser() {
		UserService userService = mock(UserService.class);

		User user = userService.createUser("UserName");

		User found = userService.getUser(user.getId());

		assertThat(found.getId(), equalTo(user.getId()));
		assertThat(found.getName(), equalTo(user.getName()));
	}
}
