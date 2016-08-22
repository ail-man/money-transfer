package com.ail.revolut.app.rest;

import com.ail.revolut.app.json.ResponseData;
import com.ail.revolut.app.json.TransferData;
import com.ail.revolut.app.service.RemittanceService;
import com.ail.revolut.app.service.RemittanceServiceImpl;
import com.ail.revolut.app.service.TransferService;
import com.ail.revolut.app.service.TransferServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/transfer")
public class TransferRestService {
	private static final Logger logger = LoggerFactory.getLogger(TransferRestService.class);

	private TransferService transferService;
	private RemittanceService remittanceService;

	public TransferRestService() {
		transferService = new TransferServiceImpl();
		remittanceService = new RemittanceServiceImpl();
	}

	@POST
	@Path("/perform")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public ResponseData transfer(TransferData transferData) {
		logger.debug(transferData.toString());

		ResponseData responseData = new ResponseData();
		try {
			transferService.transfer(transferData.getFrom(), transferData.getTo(), transferData.getAmount());
			responseData.setValue(remittanceService.save(transferData).toString());
		} catch (Exception e) {
			String msg = "Something wrong: " + e.getMessage();
			logger.debug(msg, e);
			responseData.setMessage(msg);
		}

		return responseData;
	}
}