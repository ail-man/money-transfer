package com.ail.revolut.app.algorithm;

import java.lang.reflect.Field;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

@Slf4j
public class EDRUtilTest {

    @Test
    public void scanPdoMetaInfoMap() throws Exception {
//        assertDependencyAndDependantsCountForClass(ApfTransferAgreement.class, 2, 1);
//        assertDependencyAndDependantsCountForClass(ApfDebtorAgreement.class, 2, 3);
//        assertDependencyAndDependantsCountForClass(OriginatorCorporate.class, 1, 10);
//        assertDependencyAndDependantsCountForClass(DebtorDivision.class, 1, 0);
//        assertDependencyAndDependantsCountForClass(InvoicePaymentAdvice.class, 5, 0);
    }

    private void assertDependencyAndDependantsCountForClass(Class<?> clazz, int dependencyCount, int dependantCount) {
        log.debug("Class: {}", clazz);

        Collection<Field> dependencyFieldMap = EDRUtil.getFields(clazz, EDRUtil.RelationType.DEPENDENCY);
        assertNotNull(dependencyFieldMap);
        assertEquals(dependencyFieldMap.size(), dependencyCount);

        Collection<Field> dependantFieldMap = EDRUtil.getFields(clazz, EDRUtil.RelationType.DEPENDANT);
        assertNotNull(dependantFieldMap);
        assertEquals(dependantFieldMap.size(), dependantCount);
    }

}
