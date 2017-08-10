package com.ail.revolut.app.jpa;

import com.ail.revolut.app.db.HibernateContextHolder;
import com.ail.revolut.app.model.Account;
import com.ail.revolut.app.model.Remittance;
import com.ail.revolut.app.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
public class EntityAutoSaveTest {
    private static final Random random = new Random();

    @Test
    public void testAutoSave() throws Exception {
        EntityManager em = HibernateContextHolder.getInstance().createEntityManager();

        User user1 = createUser(1);
        User user2 = createUser(2);
        User user3 = createUser(3);

        Account user1account = createAccount(user1, 1);
        Account user2account = createAccount(user2, 2);
        Account user3account = createAccount(user3, 3);

        Remittance remittance1 = createRemmitance(user1account, user2account, BigDecimal.valueOf(1L));
        Remittance remittance2 = createRemmitance(user1account, user3account, BigDecimal.valueOf(2L));
        Remittance remittance3 = createRemmitance(user2account, user1account, BigDecimal.valueOf(3L));
        Remittance remittance4 = createRemmitance(user2account, user3account, BigDecimal.valueOf(4L));
        Remittance remittance5 = createRemmitance(user3account, user1account, BigDecimal.valueOf(5L));
        Remittance remittance6 = createRemmitance(user3account, user2account, BigDecimal.valueOf(6L));

        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();

            em.persist(remittance6);
            em.persist(remittance5);
            em.persist(remittance4);
            em.persist(remittance3);
            em.persist(remittance2);
            em.persist(remittance1);

            em.persist(user3account);
            em.persist(user2account);
            em.persist(user1account);

            // if comment one of user or account persistence the exception will be thrown:
            // org.hibernate.TransientPropertyValueException: object references an unsaved transient instance

            em.persist(user3);
            em.persist(user2);
            em.persist(user1);

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            log.trace(e.getMessage(), e);
            throw e;
        }

        Account account = em.find(Account.class, 1L);
        List<Remittance> remittances = account.getRemittances();
        log.info(remittances.toString());

        em.close();
    }

    private Remittance createRemmitance(Account fromAccount, Account toAccount, BigDecimal amount) {
        Remittance remittance = new Remittance();
        remittance.setPerformed(new Date());
        remittance.setAmount(amount);
        remittance.setFrom(fromAccount);
        remittance.setTo(toAccount);
        remittance.setFromId(random.nextLong());
        remittance.setToId(random.nextLong());
        return remittance;
    }

    private User createUser(int num) {
        User user = new User();
        user.setName("Test Username " + num);
        return user;
    }

    private Account createAccount(User user, int num) {
        Account account = new Account();
        account.setBalance(BigDecimal.TEN.add(BigDecimal.valueOf(num)));
        account.setOwner(user);
        return account;
    }

}
