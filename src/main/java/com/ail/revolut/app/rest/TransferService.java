package com.ail.revolut.app.rest;

import com.ail.revolut.app.json.ResponseData;
import com.ail.revolut.app.json.TransferData;
import com.ail.revolut.app.logic.RemittanceManager;
import com.ail.revolut.app.logic.RemittanceManagerImpl;
import com.ail.revolut.app.logic.TransferManager;
import com.ail.revolut.app.logic.TransferManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/transfer")
public class TransferService {
	private static final Logger logger = LoggerFactory.getLogger(TransferService.class);

	private TransferManager transferManager;
	private RemittanceManager remittanceManager;

	public TransferService() {
		transferManager = new TransferManagerImpl();
		remittanceManager = new RemittanceManagerImpl();
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ResponseData transfer(TransferData transferData) {
		logger.debug(transferData.toString());

		ResponseData responseData = new ResponseData();
		try {
			transferManager.transfer(transferData.getFrom(), transferData.getTo(), transferData.getAmount());
			responseData.setValue(remittanceManager.save(transferData).toString());
		} catch (Exception e) {
			String msg = "Something wrong: " + e.getMessage();
			logger.debug(msg, e);
			responseData.setMessage(msg);
		}

		return responseData;
	}
}