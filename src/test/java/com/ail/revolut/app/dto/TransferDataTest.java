package com.ail.revolut.app.dto;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TransferDataTest {

	@Test
	public void testToString() throws Exception {
		TransferData transferData = new TransferData();

		assertThat(transferData.toString(), equalTo("TransferData(from=null, to=null, amount=null)"));

		transferData.setFrom(132L);
		transferData.setTo(4315L);
		transferData.setAmount(6261L);
		assertThat(transferData.toString(), equalTo("TransferData(from=132, to=4315, amount=6261)"));
	}

}
