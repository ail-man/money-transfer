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
public class EDRDfs {

    private static final int ROUNDS_COUNT = 3;

    private Map<Object, State> visitMap = new HashMap<>();
    private LinkedHashSet<Pdo> result = new LinkedHashSet<>();
    private LinkedHashSet<Pdo> dependencies;
    private int dependantsRound = 0;

    public LinkedHashSet<Pdo> collectAllGraphFromPdo(Pdo pdo) {
        log.info(">>> Collect graph for PDO: " + pdo);
        collectDependencies(pdo);
        result.addAll(dependencies);
        collectDependantsGraphs();
        result.forEach(item -> log.info("collected: " + item));
        return result;
    }

    private void collectDependencies(Object object) {
        dependencies = new LinkedHashSet<>();
        dfs(object, false);
        result.addAll(dependencies);
    }

    private void collectDependantsGraphs() {
        LinkedHashSet<Pdo> resultCopy = new LinkedHashSet<>(result); // java.util.ConcurrentModificationException

        for (Pdo collected : resultCopy) {
            for (Field field : EDRUtil.getFields(collected.getClass(), DEPENDANT)) {
                Object dependant = EDRUtil.getFieldValue(collected, field);

                if (dependant == null) {
                    continue;
                }

                if (dependant instanceof Iterable<?>) {
                    for (Object item : (Iterable<?>) dependant) {
                        collectDependencies(item);
                    }
                } else {
                    collectDependencies(dependant);
                }
            }
        }

        if (++dependantsRound < ROUNDS_COUNT) {
            collectDependantsGraphs();
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

            if (result.contains(pdo)) {
                return;
            }

            if (dependencies.contains(pdo)) {
                return;
            }

            if (visitMap.containsKey(pdo)) {
                return;
            }

            visitMap.put(pdo, State.VISITED);

            if (containsNotPreservedEdges(pdo)) {
                for (Field field : EDRUtil.getFields(pdo.getClass(), DEPENDENCY)) {
                    dfs(EDRUtil.getFieldValue(pdo, field), field.isAnnotationPresent(Embedded.class));
                }
            } else {
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
            } else if (field.isAnnotationPresent(Embedded.class)) {
                for (Field fieldValueField : EDRUtil.getFields(fieldValue.getClass(), DEPENDENCY)) {
                    if (visitMap.get(EDRUtil.getFieldValue(fieldValue, fieldValueField)) != State.PRESERVED) {
                        return true;
                    }
                }
            } else if (visitMap.get(fieldValue) != State.PRESERVED) {
                return true;
            }
        }

        return false;
    }

    private enum State {
        VISITED, PRESERVED
    }
}
