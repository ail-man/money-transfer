package com.ail.revolut.app.algorithm;

import com.ail.revolut.app.model.Pdo;
import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Embedded;
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

        Set<Pdo> result = collectAllGraph(pdo, new LinkedHashSet<>(), false);

        // TODO remove all items with ID != null

        result.forEach(obj -> log.info("Collected pdo: {}", obj));

        return result;
    }

    private static Set<Pdo> collectAllGraph(Object object, Set<Pdo> result, boolean embedded) {
        if (object == null) {
            return result;
        }

        if (object instanceof Iterable<?>) {
            for (Object item : (Iterable<?>) object) {
                collectAllGraph(item, result, false);
            }
        }

        if (embedded) {
            for (Field field : EDRUtil.getFields(object.getClass(), DEPENDANT)) {
                collectAllGraph(EDRUtil.getFieldValue(object, field), result, field.isAnnotationPresent(Embedded.class));
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

            result.addAll(EDRUtil.inverseOrder(collectAllDependencies(pdo, new LinkedHashSet<>(), embedded)));

            for (Pdo item : new LinkedHashSet<>(result)) {
                for (Field field : EDRUtil.getFields(item.getClass(), DEPENDANT)) {
                    collectAllGraph(EDRUtil.getFieldValue(item, field), result, field.isAnnotationPresent(Embedded.class));
                }
            }
        }

        return result;
    }

    private static Set<Pdo> collectAllDependencies(Object object, Set<Pdo> result, boolean embedded) {
        if (object == null) {
            return result;
        }

        if (object instanceof Iterable<?>) {
            for (Object obj : (Iterable<?>) object) {
                collectAllDependencies(obj, result, false);
            }
        }

        if (embedded) {
            for (Field field : EDRUtil.getFields(object.getClass(), DEPENDENCY)) {
                collectAllDependencies(EDRUtil.getFieldValue(object, field), result, field.isAnnotationPresent(Embedded.class));
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

            for (Field field : EDRUtil.getFields(object.getClass(), DEPENDENCY)) {
                collectAllDependencies(EDRUtil.getFieldValue(object, field), result, field.isAnnotationPresent(Embedded.class));
            }
        }

        return result;
    }

}

