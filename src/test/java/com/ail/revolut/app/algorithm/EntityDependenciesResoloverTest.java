package com.ail.revolut.app.algorithm;

import java.util.Set;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EntityDependenciesResoloverTest {

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
        Set<Pdo> pdoSet = EntityDependenciesResolver.collectNotPersistedObjectsFromPdoGraph(pdo);
        assertEquals(pdoSet.size(), count);
    }

}
