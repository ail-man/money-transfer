package com.ail.revolut.app.algorithm;

import com.ail.revolut.app.model.Account;
import com.ail.revolut.app.model.Pdo;
import com.ail.revolut.app.model.Remittance;
import com.ail.revolut.app.model.User;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EntityDependenciesResoloverTest {

    private static final Random random = new Random();

    @Test
    public void testCollect() throws Exception {
        User user1 = createUser(1, 1);
        User user2 = createUser(2, 2);
        User user3 = createUser(3, 3);

        Account user1account = createAccount(1, user1, 1);
        user1.getAccounts().add(user1account);

        Account user2account = createAccount(2, user2, 2);
        user2.getAccounts().add(user2account);

        Account user3account = createAccount(3, user3, 3);
        user3.getAccounts().add(user3account);

        Remittance remittance1 = createRemmitance(1, user1account, user2account, BigDecimal.valueOf(1));
        Remittance remittance2 = createRemmitance(2, user1account, user3account, BigDecimal.valueOf(2));
        user1account.getRemittances().add(remittance1);
        user1account.getRemittances().add(remittance2);

        Remittance remittance3 = createRemmitance(3, user2account, user1account, BigDecimal.valueOf(3));
        Remittance remittance4 = createRemmitance(4, user2account, user3account, BigDecimal.valueOf(4));
        user2account.getRemittances().add(remittance3);
        user2account.getRemittances().add(remittance4);

        Remittance remittance5 = createRemmitance(5, user3account, user1account, BigDecimal.valueOf(5));
        Remittance remittance6 = createRemmitance(6, user3account, user2account, BigDecimal.valueOf(6));
        user3account.getRemittances().add(remittance5);
        user3account.getRemittances().add(remittance6);

        Set<Pdo> pdoSet = EntityDependenciesResolver.collectAllGraphFromPdo(user1);
        assertEquals(pdoSet.size(), 12);

        pdoSet = EntityDependenciesResolver.collectAllGraphFromPdo(user2);
        assertEquals(pdoSet.size(), 12);

        pdoSet = EntityDependenciesResolver.collectAllGraphFromPdo(user3);
        assertEquals(pdoSet.size(), 12);

        pdoSet = EntityDependenciesResolver.collectAllGraphFromPdo(user1account);
        assertEquals(pdoSet.size(), 12);

        pdoSet = EntityDependenciesResolver.collectAllGraphFromPdo(user2account);
        assertEquals(pdoSet.size(), 12);

        pdoSet = EntityDependenciesResolver.collectAllGraphFromPdo(user3account);
        assertEquals(pdoSet.size(), 12);

        pdoSet = EntityDependenciesResolver.collectAllGraphFromPdo(remittance1);
        assertEquals(pdoSet.size(), 12);

        pdoSet = EntityDependenciesResolver.collectAllGraphFromPdo(remittance2);
        assertEquals(pdoSet.size(), 12);

        pdoSet = EntityDependenciesResolver.collectAllGraphFromPdo(remittance3);
        assertEquals(pdoSet.size(), 12);

        pdoSet = EntityDependenciesResolver.collectAllGraphFromPdo(remittance4);
        assertEquals(pdoSet.size(), 12);

        pdoSet = EntityDependenciesResolver.collectAllGraphFromPdo(remittance5);
        assertEquals(pdoSet.size(), 12);

        pdoSet = EntityDependenciesResolver.collectAllGraphFromPdo(remittance6);
        assertEquals(pdoSet.size(), 12);
    }

    static Remittance createRemmitance(long id, Account fromAccount, Account toAccount, BigDecimal amount) {
        Remittance remittance = new Remittance();
        remittance.setId(id);
        remittance.setPerformed(new Date());
        remittance.setAmount(amount);
        remittance.setFrom(fromAccount);
        remittance.setTo(toAccount);
        remittance.setFromId(random.nextLong());
        remittance.setToId(random.nextLong());
        return remittance;
    }

    static User createUser(long id, int num) {
        User user = new User();
        user.setId(id);
        user.setName("Test Username " + num);
        return user;
    }

    static Account createAccount(long id, User user, int num) {
        Account account = new Account();
        account.setId(id);
        account.setBalance(BigDecimal.TEN.add(BigDecimal.valueOf(num)));
        account.setOwner(user);
        return account;
    }

}
