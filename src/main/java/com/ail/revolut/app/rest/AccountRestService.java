package com.ail.revolut.app.rest;

import com.ail.revolut.app.model.Response;
import com.ail.revolut.app.service.AccountService;
import com.ail.revolut.app.service.AccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/account")
public class AccountRestService {

	private static final Logger logger = LoggerFactory.getLogger(AccountRestService.class);

	private AccountService accountService;

	public AccountRestService() {
		accountService = new AccountServiceImpl();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Got it!";
	}

	@GET
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAccount() {
		logger.debug("Create account requested");

		Response response = new Response();
		try {
			response.setId(accountService.createAccount());
		} catch (Exception e) {
			String msg = "Something wrong: " + e.getMessage();
			logger.debug(msg, e);
			response.setMessage(msg);
		}

		return response;
	}
}
