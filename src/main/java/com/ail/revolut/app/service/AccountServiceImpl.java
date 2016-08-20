package com.ail.revolut.app.service;

import com.ail.revolut.app.model.Account;
import com.ail.revolut.app.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AccountServiceImpl implements AccountService {
	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	private AccountRepository accountRepository;

	@Override
	public Account createAccount() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2unit");
		EntityManager em = emf.createEntityManager();


		em.getTransaction().begin();
		Account account = new Account();
		logger.debug("Create new account");
		em.persist(account);
		em.getTransaction().commit();
		return new Account();
	}
}
