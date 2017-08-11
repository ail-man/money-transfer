package com.ail.revolut.app.algorithm;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

import static com.ail.revolut.app.algorithm.EDRUtil.RelationType.DEPENDANT;
import static com.ail.revolut.app.algorithm.EDRUtil.RelationType.DEPENDENCY;

@Slf4j
public final class EntityDependenciesResolver {

    private EntityDependenciesResolver() {
        throw new AssertionError();
    }

    public static Set<Pdo> collectNotPersistedObjectsFromPdoGraph(Pdo pdo) {
        log.debug("Collect all not persisted PDO objects from PDO: {}", pdo);

        Set<Pdo> result = new LinkedHashSet<>();

        collectNotPersisteFieldsFromPdo(pdo, result);

        return result;
    }

    private static void collectNotPersisteFieldsFromPdo(Pdo pdo, Set<Pdo> result) {
        Set<Pdo> notPersistedDependencies = new LinkedHashSet<>();
        collectNotPersistedFields(pdo, notPersistedDependencies, DEPENDENCY);
        notPersistedDependencies = revertOrder(notPersistedDependencies);

        result.addAll(notPersistedDependencies);

        Set<Pdo> notPersistedDependants = new LinkedHashSet<>();
        collectNotPersistedFields(pdo, notPersistedDependants, DEPENDANT);
        for (Pdo dependant : notPersistedDependants) {
            if (!result.contains(dependant)) {
                collectNotPersisteFieldsFromPdo(dependant, result);
            }
        }
    }

    private static void collectNotPersistedFields(Object object, Set<Pdo> result, EDRUtil.RelationType relationType) {
        if (object instanceof Iterable<?>) {
            Iterable<?> iterable = (Iterable) object;
            for (Object obj : iterable) {
                collectNotPersistedFields(obj, result, relationType);
            }
        }

        if (object instanceof Pdo) {
            Pdo pdo = (Pdo) object;

            if (pdo.getId() != null) {
                return;
            }

            if (result.contains(pdo)) {
                return;
            }

            log.debug("Add pdo: {}", pdo);
            result.add(pdo);

            Collection<Field> fieldCollection = EDRUtil.getFields(object.getClass(), relationType);
            for (Field field : fieldCollection) {
                Object fieldValue = getFieldValue(object, field);
                collectNotPersistedFields(fieldValue, result, relationType);
            }
        }
    }

    private static Object getFieldValue(Object obj, Field field) {
        try {
            field.setAccessible(true);
            return field.get(obj);
        }
        catch (IllegalAccessException e) {
            log.debug(e.getMessage());
            return null;
        }
    }

    private static Set<Pdo> revertOrder(Set<Pdo> set) {
        Set<Pdo> result = new LinkedHashSet<>();

        LinkedList<Pdo> linkedList = new LinkedList<>(set);
        Iterator<Pdo> iterator = linkedList.descendingIterator();
        while (iterator.hasNext()) {
            Pdo pdo = iterator.next();
            result.add(pdo);
        }

        return result;
    }

}
