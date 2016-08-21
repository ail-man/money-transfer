package com.ail.revolut.app.service;

import com.ail.revolut.app.json.TransferData;

public interface RemittanceService {

	Long save(TransferData transferData);
}
