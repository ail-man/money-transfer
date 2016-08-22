package com.ail.revolut.app.logic;

import com.ail.revolut.app.json.TransferData;

public interface RemittanceManager {

	Long save(TransferData transferData);
}
