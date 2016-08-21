package com.ail.revolut.app.service;

import com.ail.revolut.app.json.TransferData;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class RemittanceServiceTest {

	@Test
	public void testSave() {
		RemittanceService remittanceService = new RemittanceServiceImpl();
		TransferData transferData = new TransferData();
		transferData.setFrom(1L);
		transferData.setTo(2L);
		transferData.setAmount(300L);

		Long id = remittanceService.save(transferData);
		assertThat(id, notNullValue());
	}
}
