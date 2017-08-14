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
    public void test() throws Exception {
//        Pdo pdo = PdoTestObjectBuilder.getApfTransferAgreement();
//        assertCollectNotPersistedObjectsFromPdoGraphSizeEquals(pdo, 7);
//
//        pdo = PdoTestObjectBuilder.getApfTransferAgreementWithDebtorAgreementId();
//        assertCollectNotPersistedObjectsFromPdoGraphSizeEquals(pdo, 5);
//
//        pdo = PdoTestObjectBuilder.getApfTransferAgreementWithDebtorAgreementBuyerId();
//        assertCollectNotPersistedObjectsFromPdoGraphSizeEquals(pdo, 5);
//
//        pdo = PdoTestObjectBuilder.getApfTransferAgreementWithId();
//        assertCollectNotPersistedObjectsFromPdoGraphSizeEquals(pdo, 0);
    }

    private void assertCollectNotPersistedObjectsFromPdoGraphSizeEquals(Pdo pdo, int count) {
        Set<Pdo> pdoSet = EntityDependenciesResolver.collectAllGraphFromPdo(pdo);
        assertEquals(pdoSet.size(), count);
    }

    @Test
    public void testCollect() throws Exception {
        User user1 = createUser(1);
        User user2 = createUser(2);
        User user3 = createUser(3);

        Account user1account = createAccount(user1, 1);
        user1.getAccounts().add(user1account);

        Account user2account = createAccount(user2, 2);
        user2.getAccounts().add(user2account);

        Account user3account = createAccount(user3, 3);
        user3.getAccounts().add(user3account);

        Remittance remittance1 = createRemmitance(user1account, user2account, BigDecimal.valueOf(1L));
        Remittance remittance2 = createRemmitance(user1account, user3account, BigDecimal.valueOf(2L));
        user1account.getRemittances().add(remittance1);
        user1account.getRemittances().add(remittance2);

        Remittance remittance3 = createRemmitance(user2account, user1account, BigDecimal.valueOf(3L));
        Remittance remittance4 = createRemmitance(user2account, user3account, BigDecimal.valueOf(4L));
        user2account.getRemittances().add(remittance3);
        user2account.getRemittances().add(remittance4);

        Remittance remittance5 = createRemmitance(user3account, user1account, BigDecimal.valueOf(5L));
        Remittance remittance6 = createRemmitance(user3account, user2account, BigDecimal.valueOf(6L));
        user3account.getRemittances().add(remittance5);
        user3account.getRemittances().add(remittance6);

        Set<Pdo> pdoSet = EntityDependenciesResolver.collectAllGraphFromPdo(user3);
        assertEquals(pdoSet.size(), 12);
    }

    static Remittance createRemmitance(Account fromAccount, Account toAccount, BigDecimal amount) {
        Remittance remittance = new Remittance();
        remittance.setPerformed(new Date());
        remittance.setAmount(amount);
        remittance.setFrom(fromAccount);
        remittance.setTo(toAccount);
        remittance.setFromId(random.nextLong());
        remittance.setToId(random.nextLong());
        return remittance;
    }

    static User createUser(int num) {
        User user = new User();
        user.setName("Test Username " + num);
        return user;
    }

    static Account createAccount(User user, int num) {
        Account account = new Account();
        account.setBalance(BigDecimal.TEN.add(BigDecimal.valueOf(num)));
        account.setOwner(user);
        return account;
    }

}
