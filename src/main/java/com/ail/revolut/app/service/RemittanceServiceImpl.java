package com.ail.revolut.app.service;

import com.ail.revolut.app.json.TransferData;
import com.ail.revolut.app.model.Remittance;
import com.ail.revolut.app.db.HibernateContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Date;

public class RemittanceServiceImpl implements RemittanceService {
	private static final Logger logger = LoggerFactory.getLogger(RemittanceServiceImpl.class);

	@Override
	public Long save(TransferData transferData) {
		Long number = null;

		EntityManager em = HibernateContextHolder.createEntityManager();

		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();

			Remittance remittance = new Remittance();
			remittance.setFromId(transferData.getFrom());
			remittance.setToId(transferData.getTo());
			remittance.setAmount(transferData.getAmount());
			remittance.setPerformed(new Date());
			em.persist(remittance);

			tx.commit();

			number = remittance.getId();

			logger.info("Remittance saved with id = " + number);
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			em.close();
		}

		return number;
	}
}
