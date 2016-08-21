package com.ail.revolut.app.model;

import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RemmitanceTest {

	@Test
	public void testClone() {
		Remittance source = new Remittance();
		source.setId(100L);
		source.setFromId(1L);
		source.setToId(2L);
		source.setAmount(300L);
		source.setPerformed(new Date());

		Remittance deepClone = source.clone();
		assertTrue(deepClone != source);
		assertTrue(deepClone.getPerformed() != source.getPerformed());

		assertThat(deepClone.getId(), equalTo(source.getId()));
		assertThat(deepClone.getFromId(), equalTo(source.getFromId()));
		assertThat(deepClone.getToId(), equalTo(source.getToId()));
		assertThat(deepClone.getAmount(), equalTo(source.getAmount()));
		assertThat(deepClone.getPerformed(), equalTo(source.getPerformed()));
	}
}
