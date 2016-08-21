package com.ail.revolut.app.service;

import com.ail.revolut.app.json.RemittanceData;
import org.junit.Test;

public class RemittanceServiceTest {

	@Test
	public void test() {
		RemittanceService remittanceService = new RemittanceServiceImpl();
		RemittanceData remittanceData = new RemittanceData();
		remittanceData.setFrom(1L);
		remittanceData.setTo(2L);
		remittanceData.setAmount(300L);
		remittanceService.save(remittanceData);
	}
}
