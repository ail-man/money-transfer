package com.ail.revolut.app.bank;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Person {

	private static final String ANONYMOUS = "ANONYMOUS";
	private static final Map<String, Person> map = new HashMap<>();
	private static long counter = 0;

	private final String id;
	private String name;

	private Person(String id) {
		this.id = id;
		this.name = ANONYMOUS;
	}

	public static Person create() {
		Person person = new Person(String.valueOf(counter++));
		map.put(person.getId(), person);
		return person;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		Validate.notNull(name);
		this.name = name;
	}

	public Person withName(String name) {
		this.setName(name);
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Person person = (Person) o;

		return new EqualsBuilder()
				.append(id, person.id)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.toHashCode();
	}

	@Override
	public String toString() {
		return name + "(" + id + ")";
	}
}
