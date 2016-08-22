package com.ail.revolut.app.rest;

import com.ail.revolut.app.json.ResponseData;
import com.ail.revolut.app.service.AccountService;
import com.ail.revolut.app.service.AccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/account")
public class AccountRestService {

	private static final Logger logger = LoggerFactory.getLogger(AccountRestService.class);

	private AccountService accountService;

	public AccountRestService() {
		accountService = new AccountServiceImpl();
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/{id}/balance")
	public ResponseData getBalance(@PathParam("id") String id) {
		logger.info("Balance requested for account value=" + id);

		ResponseData responseData = new ResponseData();
		try {
			responseData.setId(accountService.getBalance(Long.parseLong(id)));
		} catch (Exception e) {
			String msg = "Something wrong: " + e.getClass() + ": " + e.getMessage();
			logger.debug(msg, e);
			responseData.setMessage(msg);
		}

		return responseData;
	}

	@PUT
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/create")
	public ResponseData createAccount() {
		logger.info("Create account requested");

		ResponseData responseData = new ResponseData();
		try {
			responseData.setId(accountService.createAccount());
		} catch (Exception e) {
			String msg = "Something wrong: " + e.getClass() + ": " + e.getMessage();
			logger.debug(msg, e);
			responseData.setMessage(msg);
		}

		return responseData;
	}
}
