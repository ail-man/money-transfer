package com.ail.revolut.app.algorithm;

import com.ail.revolut.app.model.Pdo;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

import static com.ail.revolut.app.algorithm.EDRUtil.RelationType.DEPENDANT;
import static com.ail.revolut.app.algorithm.EDRUtil.RelationType.DEPENDENCY;

@Slf4j
public final class EntityDependenciesResolver {

    private EntityDependenciesResolver() {
        throw new AssertionError();
    }

    public static Set<Pdo> collectAllGraphFromPdo(Pdo pdo) {
        log.info("Collect all graph from PDO: {}", pdo.getClass());
        Set<Pdo> result = collectAllGraph(pdo, new LinkedHashSet<>());
        result.forEach(obj -> log.info("Collected pdo: {}", obj));
        return result;
    }

    private static Set<Pdo> collectAllGraph(Object object, Set<Pdo> result) {
        if (object == null) {
            return result;
        }

        if (object instanceof Iterable<?>) {
            for (Object item : (Iterable<?>) object) {
                collectAllGraph(item, result);
            }
        }

        if (object instanceof Pdo) {
            Pdo pdo = (Pdo) object;

//            if (pdo.getId() != null) {
//                return;
//            }

            if (result.contains(pdo)) {
                return result;
            }

            result.addAll(EDRUtil.inverseOrder(collectAllDependencies(pdo, new LinkedHashSet<>())));

            for (Pdo item : new LinkedHashSet<>(result)) {
                for (Field field : EDRUtil.getFields(item.getClass(), DEPENDANT)) {
                    Object fieldValue = EDRUtil.getFieldValue(item, field);
                    collectAllGraph(fieldValue, result);
                }
            }
        }

        return result;
    }

    private static Set<Pdo> collectAllDependencies(Object object, Set<Pdo> result) {
        if (object == null) {
            return result;
        }

        if (object instanceof Iterable<?>) {
            for (Object obj : (Iterable<?>) object) {
                collectAllDependencies(obj, result);
            }
        }

        if (object instanceof Pdo) {
            Pdo pdo = (Pdo) object;

//            if (pdo.getId() != null) {
//                return;
//            }

            if (result.contains(pdo)) {
                return result;
            }

            result.add(pdo);

            Collection<Field> fields = EDRUtil.getFields(object.getClass(), DEPENDENCY);
            for (Field field : fields) {
                Object fieldValue = EDRUtil.getFieldValue(object, field);
                collectAllDependencies(fieldValue, result);
            }
        }

        return result;
    }

}

