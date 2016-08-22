package com.ail.revolut.app.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RemittanceTest {

	private Remittance remittance;

	@Before
	public void init() {
		remittance = new Remittance();
		remittance.setId(100L);
		remittance.setFromId(1L);
		remittance.setToId(2L);
		remittance.setAmount(300L);
		remittance.setPerformed(new Date());
	}

	@Test
	public void testClone() {
		Remittance deepClone = remittance.clone();
		assertTrue(deepClone != remittance);
		assertTrue(deepClone.getPerformed() != remittance.getPerformed());

		assertThat(deepClone.getId(), equalTo(remittance.getId()));
		assertThat(deepClone.getFromId(), equalTo(remittance.getFromId()));
		assertThat(deepClone.getToId(), equalTo(remittance.getToId()));
		assertThat(deepClone.getAmount(), equalTo(remittance.getAmount()));
		assertThat(deepClone.getPerformed(), equalTo(remittance.getPerformed()));
	}

	@Test
	public void testToString() {
		Date date = new Date();
		assertThat(remittance.toString(), equalTo("Remittance(id=100, fromId=1, toId=2, amount=300, performed=" + date.toString() + ")"));

		remittance.setPerformed(null);
		assertThat(remittance.toString(), equalTo("Remittance(id=100, fromId=1, toId=2, amount=300, performed=null)"));
	}
}
