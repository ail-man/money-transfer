package com.ail.revolut.app.logic;

import com.ail.revolut.app.dto.TransferData;

public interface RemittanceManager {

	Long save(TransferData transferData) throws Exception;

}
