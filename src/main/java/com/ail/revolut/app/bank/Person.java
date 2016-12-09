package com.ail.revolut.app.bank;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Person {

	private static final String ANONYMOUS = "ANONYMOUS";

	private final String id;
	private String name;

	private Person(String id) {
		this.id = id;
		this.name = ANONYMOUS;
	}

	public Person(Person owner) {
		this.id = owner.id;
		this.setName(owner.name);
	}

	public static Person create(String id) {
		Validate.notNull(id);
		return new Person(id);
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
