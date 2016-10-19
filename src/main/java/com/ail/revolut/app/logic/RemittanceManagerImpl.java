package com.ail.revolut.app.logic;

import java.util.Date;

import com.ail.revolut.app.dto.TransferData;
import com.ail.revolut.app.logic.common.AbstractManager;
import com.ail.revolut.app.model.Remittance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemittanceManagerImpl extends AbstractManager implements RemittanceManager {
	private static final Logger logger = LoggerFactory.getLogger(RemittanceManagerImpl.class);

	@Override
	public Long save(TransferData transferData) throws Exception {
		logger.info("Save remittance fromId={}, toId={}, amount={} info started", transferData.getFrom(), transferData.getTo(), transferData.getAmount());
		Long remittanceId = perform(em -> {
			Remittance remittance = new Remittance();
			remittance.setFromId(transferData.getFrom());
			remittance.setToId(transferData.getTo());
			remittance.setAmount(transferData.getAmount());
			remittance.setPerformed(new Date());
			em.persist(remittance);
			return remittance;
		}).getId();
		logger.info("Remittance fromId={}, toId={} saved with id={}", transferData.getFrom(), transferData.getTo(), remittanceId);
		return remittanceId;
	}
}
