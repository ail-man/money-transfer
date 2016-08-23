package com.ail.revolut.app.logic;

import com.ail.revolut.app.dto.TransferData;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class RemittanceManagerTest {

	@Test
	public void testSave() throws Exception {
		RemittanceManager remittanceManager = new RemittanceManagerImpl();
		TransferData transferData = new TransferData();
		transferData.setFrom(1L);
		transferData.setTo(2L);
		transferData.setAmount(300L);

		Long id = remittanceManager.save(transferData);
		assertThat(id, notNullValue());
	}
}
