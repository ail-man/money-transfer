package com.ail.revolut.app.model;

import org.junit.Test;

public class UserTest {

	@Test
	public void testToString() {
		User user = new User();
		user.setId(123);
		user.setName("Name");
		System.out.println(user);
	}
}
