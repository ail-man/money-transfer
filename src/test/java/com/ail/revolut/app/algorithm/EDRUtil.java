package com.ail.revolut.app.algorithm;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EDRUtil {

    private EDRUtil() {
        throw new AssertionError();
    }

    public static Set<Field> getFields(Class<?> clazz, RelationType relationType) {
        switch (relationType) {
            case DEPENDENCY:
                return EDRUtil.scanDependencyFields(clazz);
            case DEPENDANT:
                return EDRUtil.scanDependantFields(clazz);
            default:
                return Collections.emptySet();
        }
    }

    private static Set<Field> scanDependencyFields(Class<?> clazz) {
        Set<Field> result = new HashSet<>();

        scanDependencyFields(clazz, result);

        return result;
    }

    private static void scanDependencyFields(Class<?> clazz, Set<Field> result) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ManyToOne.class)
                    || (field.isAnnotationPresent(OneToOne.class) && field.isAnnotationPresent(JoinColumn.class))) {
                result.add(field);
                log.debug("Dependency Field: {}", field);
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            scanDependencyFields(superClass, result);
        }
    }

    private static Set<Field> scanDependantFields(Class<?> clazz) {
        Set<Field> result = new HashSet<>();

        scanDependantFields(clazz, result);

        return result;
    }

    private static void scanDependantFields(Class<?> clazz, Set<Field> result) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(OneToMany.class)
                    || (field.isAnnotationPresent(OneToOne.class) && !field.isAnnotationPresent(JoinColumn.class))) {
                result.add(field);
                log.debug("Dependant Field: {}", field);
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            scanDependantFields(superClass, result);
        }
    }

    public enum RelationType {
        DEPENDENCY,
        DEPENDANT
    }

}
