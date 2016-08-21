package com.ail.revolut.app.service;

import com.ail.revolut.app.model.Remittance;
import org.junit.Test;

public class RemittanceServiceTest {

	RemittanceService remittanceService = new RemittanceServiceImpl();

	@Test
	public void test() {
		Remittance remittance = new Remittance();
		remittance.setFrom(1L);
		remittance.setTo(2L);
		remittance.setAmount(300L);
		remittanceService.save(remittance);
	}
}
