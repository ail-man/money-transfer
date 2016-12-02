package com.ail.revolut.app.bank;

public class Person {

	private String name;

	public Person withName(String name) {
		this.name = name;
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
