package com.ail.revolut.app.rest;

import com.ail.revolut.app.json.ResponseData;
import com.ail.revolut.app.json.TransferData;
import com.ail.revolut.app.logic.AccountManager;
import com.ail.revolut.app.logic.AccountManagerImpl;
import com.ail.revolut.app.logic.RemittanceManager;
import com.ail.revolut.app.logic.RemittanceManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/account")
public class AccountService {

	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	private AccountManager accountManager;
	private RemittanceManager remittanceManager;

	public AccountService() {
		accountManager = new AccountManagerImpl();
		remittanceManager = new RemittanceManagerImpl();
	}

	@PUT
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/create")
	public ResponseData createAccount() {
		ResponseData responseData = new ResponseData();
		try {
			logger.info("Create account requested");
			responseData.setValue(accountManager.createAccount().toString());
		} catch (Exception e) {
			String msg = "Something wrong: " + e.getClass() + ": " + e.getMessage();
			logger.trace(msg, e);
			responseData.setMessage(msg);
		}

		return responseData;
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/{id}/balance")
	public ResponseData getBalance(@PathParam("id") String accountId) {
		ResponseData responseData = new ResponseData();
		try {
			logger.info("Balance requested for account id={}", accountId);
			responseData.setValue(accountManager.getBalance(Long.parseLong(accountId)).toString());
		} catch (Exception e) {
			String msg = "Something wrong: " + e.getClass() + ": " + e.getMessage();
			logger.trace(msg, e);
			responseData.setMessage(msg);
		}

		return responseData;
	}

	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/{id}/deposit")
	public ResponseData deposit(@PathParam("id") Long accountId, Long amount) {
		ResponseData responseData = new ResponseData();
		try {
			logger.info("Deposit requested for account id={}, amount={}", accountId, amount);
			accountManager.deposit(accountId, amount);
			TransferData transferData = new TransferData(accountId, accountId, amount);
			responseData.setValue(remittanceManager.save(transferData).toString());
		} catch (Exception e) {
			String msg = "Something wrong: " + e.getClass() + ": " + e.getMessage();
			logger.trace(msg, e);
			responseData.setMessage(msg);
		}
		return responseData;
	}

	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/{id}/withdraw")
	public ResponseData withdraw(@PathParam("id") Long accountId, Long amount) {
		ResponseData responseData = new ResponseData();
		try {
			logger.info("Withdraw requested for account id={}, amount={}", accountId, amount);
			accountManager.withdraw(accountId, amount);
			TransferData transferData = new TransferData(accountId, accountId, -amount);
			responseData.setValue(remittanceManager.save(transferData).toString());
		} catch (Exception e) {
			String msg = "Something wrong: " + e.getClass() + ": " + e.getMessage();
			logger.trace(msg, e);
			responseData.setMessage(msg);
		}
		return responseData;
	}
}
