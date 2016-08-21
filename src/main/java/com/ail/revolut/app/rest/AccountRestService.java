package com.ail.revolut.app.rest;

import com.ail.revolut.app.model.Remittance;
import com.ail.revolut.app.model.Response;
import com.ail.revolut.app.service.AccountService;
import com.ail.revolut.app.service.AccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
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

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response transfer(Remittance remittance) {
		logger.debug(remittance.toString());

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
