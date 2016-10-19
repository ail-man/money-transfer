package com.ail.revolut.app.logic;

import java.math.BigInteger;

import com.ail.revolut.app.dto.TransferData;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.Test;

public class RemittanceManagerTest {

	@Test
	public void testSave() throws Exception {
		RemittanceManager remittanceManager = new RemittanceManagerImpl();
		TransferData transferData = new TransferData();
		transferData.setFrom(1L);
		transferData.setTo(2L);
		transferData.setAmount(new BigInteger("300"));

		Long id = remittanceManager.save(transferData);
		assertThat(id, notNullValue());
	}
}
