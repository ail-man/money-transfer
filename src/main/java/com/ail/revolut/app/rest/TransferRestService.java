package com.ail.revolut.app.rest;

import com.ail.revolut.app.json.RemittanceData;
import com.ail.revolut.app.json.ResponseData;
import com.ail.revolut.app.service.*;
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


	private AccountService accountService;
	private TransferService transferService;
	private RemittanceService remittanceService;

	public TransferRestService() {
		accountService = new AccountServiceImpl();
		transferService = new TransferServiceImpl();
		remittanceService = new RemittanceServiceImpl();
	}

	@POST
	@Path("/perform")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseData transfer(RemittanceData remittanceData) {
		logger.debug(remittanceData.toString());

		ResponseData responseData = new ResponseData();
		try {
			transferService.transfer(remittanceData.getFrom(), remittanceData.getTo(), remittanceData.getAmount());
			responseData.setId(remittanceService.save(remittanceData));
		} catch (Exception e) {
			String msg = "Something wrong: " + e.getMessage();
			logger.debug(msg, e);
			responseData.setMessage(msg);
		}

		return responseData;
	}
}