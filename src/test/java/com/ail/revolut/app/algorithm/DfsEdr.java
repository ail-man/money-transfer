package com.ail.revolut.app.algorithm;

import com.ail.revolut.app.model.Pdo;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import javax.persistence.Embedded;
import lombok.extern.slf4j.Slf4j;

import static com.ail.revolut.app.algorithm.EDRUtil.RelationType.DEPENDANT;
import static com.ail.revolut.app.algorithm.EDRUtil.RelationType.DEPENDENCY;

@Slf4j
class DfsEdr {

    private Map<Object, State> visitMap;
    private LinkedHashSet<Pdo> dependencies;
    private LinkedHashSet<Pdo> result = new LinkedHashSet<>();

    LinkedHashSet<Pdo> collectAllGraphFromPdo(Pdo pdo) {
        visitMap = new HashMap<>();
        dependencies = new LinkedHashSet<>();

        dfs(pdo, false);

        result.addAll(dependencies);

        collectDependantsGraphs();
        
        result.forEach(item -> log.info("Collected pdo: " + item));
        return result;
    }

    private void collectDependantsGraphs() {
        LinkedHashSet<Pdo> copy = new LinkedHashSet<>(result); // java.util.ConcurrentModificationException

        for (Pdo collected : copy) {
            for (Field field : EDRUtil.getFields(collected.getClass(), DEPENDANT)) {
                Object dependant = EDRUtil.getFieldValue(collected, field);

                if (dependant == null) {
                    continue;
                }

                if (dependant instanceof Iterable<?>) {
                    for (Object item : (Iterable<?>) dependant) {
                        runCollectAllGraphByCondition(item);
                    }
                }
                else {
                    runCollectAllGraphByCondition(dependant);
                }
            }
        }
    }

    private void runCollectAllGraphByCondition(Object object) {
        if (object instanceof Pdo) {
            Pdo pdo = (Pdo) object;
            if (!result.contains(pdo)) {
                collectAllGraphFromPdo(pdo);
            }
        }
    }

    private void dfs(Object object, boolean embedded) {
        if (object == null) {
            return;
        }

        if (object instanceof Iterable<?>) {
            for (Object item : (Iterable<?>) object) {
                dfs(item, false);
            }
        }

        if (embedded) {
            for (Field field : EDRUtil.getFields(object.getClass(), DEPENDENCY)) {
                dfs(EDRUtil.getFieldValue(object, field), field.isAnnotationPresent(Embedded.class));
            }
        }

        if (object instanceof Pdo) {
            Pdo pdo = (Pdo) object;

            if (dependencies.contains(pdo)) {
                return;
            }

            if (!visitMap.containsKey(pdo)) {
                visitMap.put(pdo, State.VISITED);
            }

            if (containsNotPreservedEdges(pdo)) {
                for (Field field : EDRUtil.getFields(pdo.getClass(), DEPENDENCY)) {
                    dfs(EDRUtil.getFieldValue(pdo, field), field.isAnnotationPresent(Embedded.class));
                }
            }
            else {
                visitMap.put(pdo, State.PRESERVED);
                dependencies.add(pdo);
                return;
            }

            dependencies.add(pdo);
        }
    }

    private boolean containsNotPreservedEdges(Object object) {
        for (Field field : EDRUtil.getFields(object.getClass(), DEPENDENCY)) {
            Object fieldValue = EDRUtil.getFieldValue(object, field);

            if (fieldValue == null) {
                continue;
            }

            if (fieldValue instanceof Iterable<?>) {
                for (Object item : (Iterable<?>) fieldValue) {
                    if (visitMap.get(item) != State.PRESERVED) {
                        return true;
                    }
                }
            }
            else if (field.isAnnotationPresent(Embedded.class)) {
                for (Field fieldValueField : EDRUtil.getFields(fieldValue.getClass(), DEPENDENCY)) {
                    if (visitMap.get(EDRUtil.getFieldValue(fieldValue, fieldValueField)) != State.PRESERVED) {
                        return true;
                    }
                }
            }
            else if (visitMap.get(fieldValue) != State.PRESERVED) {
                return true;
            }
        }

        return false;
    }

    private enum State {
        VISITED, PRESERVED
    }
}
