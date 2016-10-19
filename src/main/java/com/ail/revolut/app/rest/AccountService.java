package com.ail.revolut.app.rest;

import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ail.revolut.app.dto.Money;
import com.ail.revolut.app.dto.ResponseData;
import com.ail.revolut.app.dto.TransferData;
import com.ail.revolut.app.logic.AccountManager;
import com.ail.revolut.app.logic.AccountManagerImpl;
import com.ail.revolut.app.logic.RemittanceManager;
import com.ail.revolut.app.logic.RemittanceManagerImpl;
import com.ail.revolut.app.rest.common.AbstractService;

@Path("/account")
public class AccountService extends AbstractService {

	private AccountManager accountManager;
	private RemittanceManager remittanceManager;

	public AccountService() {
		accountManager = new AccountManagerImpl();
		remittanceManager = new RemittanceManagerImpl();
	}

	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/create")
	public ResponseData createAccount() {
		return handleRequest(() -> {
			ResponseData responseData = new ResponseData();
			logger.info("Create account requested");
			responseData.setValue(accountManager.createAccount().toString());
			return responseData;
		});
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/{id}/balance")
	public ResponseData getBalance(@PathParam("id") Long accountId) {
		return handleRequest(() -> {
			ResponseData responseData = new ResponseData();
			logger.info("Balance requested for account id={}", accountId);
			responseData.setValue(accountManager.getBalance(accountId).toString());
			return responseData;
		});
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/{id}/deposit")
	public ResponseData deposit(@PathParam("id") Long accountId, Money money) {
		return handleRequest(() -> {
			ResponseData responseData = new ResponseData();
			BigDecimal amount = money.getAmount();
			logger.info("Deposit requested for account id={}, amount={}", accountId, amount);
			accountManager.deposit(accountId, amount);
			TransferData transferData = new TransferData(accountId, accountId, amount);
			responseData.setValue(remittanceManager.save(transferData).toString());
			return responseData;
		});
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/{id}/withdraw")
	public ResponseData withdraw(@PathParam("id") Long accountId, Money money) {
		return handleRequest(() -> {
			ResponseData responseData = new ResponseData();
			BigDecimal amount = money.getAmount();
			logger.info("Withdraw requested for account id={}, amount={}", accountId, amount);
			accountManager.withdraw(accountId, amount);
			TransferData transferData = new TransferData(accountId, accountId, amount.negate());
			responseData.setValue(remittanceManager.save(transferData).toString());
			return responseData;
		});
	}
}
