package com.ail.revolut.app.logic;

import java.math.BigDecimal;

public interface TransferManager {

	void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) throws Exception;

}
