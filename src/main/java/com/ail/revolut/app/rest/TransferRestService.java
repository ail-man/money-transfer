package com.ail.revolut.app.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ail.revolut.app.dto.ResponseData;
import com.ail.revolut.app.dto.TransferData;
import com.ail.revolut.app.logic.RemittanceManager;
import com.ail.revolut.app.logic.RemittanceManagerImpl;
import com.ail.revolut.app.logic.TransferManager;
import com.ail.revolut.app.logic.TransferManagerImpl;
import com.ail.revolut.app.rest.common.AbstractRestService;

@Path("/transfer")
public class TransferRestService extends AbstractRestService {

	private TransferManager transferManager;
	private RemittanceManager remittanceManager;

	public TransferRestService() {
		transferManager = new TransferManagerImpl();
		remittanceManager = new RemittanceManagerImpl();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ResponseData transfer(TransferData transferData) {
		return handleRequest(() -> {
			ResponseData responseData = new ResponseData();
			logger.info("Transfer requested fromId={}, toId={}, amount={}", transferData.getFrom(), transferData.getTo(), transferData.getAmount());
			transferManager.transfer(transferData.getFrom(), transferData.getTo(), transferData.getAmount());
			responseData.setValue(remittanceManager.save(transferData).toString());
			return responseData;
		});
	}
}
