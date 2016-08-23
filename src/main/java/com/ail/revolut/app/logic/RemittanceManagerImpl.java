package com.ail.revolut.app.logic;

import com.ail.revolut.app.json.TransferData;
import com.ail.revolut.app.model.Remittance;
import com.ail.revolut.app.db.HibernateContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Date;

public class RemittanceManagerImpl implements RemittanceManager {
	private static final Logger logger = LoggerFactory.getLogger(RemittanceManagerImpl.class);

	@Override
	public Long save(TransferData transferData) {
		Long number = null;

		EntityManager em = HibernateContextHolder.createEntityManager();

		EntityTransaction tx = null;
		try {
			logger.info("Save remittance fromId={}, toId={}, amount={} info started", transferData.getFrom(), transferData.getTo(), transferData.getAmount());
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

			logger.info("Remittance fromId={}, toId={} saved with id={}",  transferData.getFrom(), transferData.getTo(), number);
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) tx.rollback();
			logger.trace(e.getMessage(), e);
			throw e;
		} finally {
			em.close();
		}

		return number;
	}
}
