package com.ail.revolut.app.algorithm;

import com.ail.revolut.app.model.Pdo;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javax.persistence.Embedded;
import lombok.extern.slf4j.Slf4j;

import static com.ail.revolut.app.algorithm.EDRUtil.RelationType.DEPENDENCY;

@Slf4j
class DfsEdr {

    private Map<Object, State> visitMap;
    private Stack<Pdo> result;

    Stack<Pdo> collectAllGraphFromPdo(Pdo pdo) {
        log.info("Collect all graph from PDO: {}", pdo.getClass());

        visitMap = new HashMap<>();
        result = new Stack<>();

        dfs(pdo, false);
        
        result.forEach(obj -> log.info("Collected pdo: {}", obj));

        return result;
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

            if (!visitMap.containsKey(pdo)) {
                visitMap.put(pdo, State.VISITED);
            }

            if (containsNotPreservedEdges(pdo)) {
                for (Field field : EDRUtil.getFields(pdo.getClass(), DEPENDENCY)) {
                    dfs(EDRUtil.getFieldValue(pdo, field), field.isAnnotationPresent(Embedded.class));
                }
            } else {
                visitMap.put(pdo, State.PRESERVED);
                result.push(pdo);
                return;
            }

            result.push(pdo);
        }
    }

    private boolean containsNotPreservedEdges(Object object) {
        for (Field field : EDRUtil.getFields(object.getClass(), DEPENDENCY)) {
            Object fieldValue = EDRUtil.getFieldValue(object, field);

            if (fieldValue != null) {
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
        }

        return false;
    }

    private enum State {
        VISITED, PRESERVED
    }
}
