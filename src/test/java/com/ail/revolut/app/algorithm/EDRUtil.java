package com.ail.revolut.app.algorithm;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EDRUtil {

    private EDRUtil() {
        throw new AssertionError();
    }

    // TODO write description
    public static <T> Set<T> inverseOrder(Set<T> set) {
        Set<T> result = new LinkedHashSet<>();

        LinkedList<T> linkedList = new LinkedList<>(set);
        Iterator<T> iterator = linkedList.descendingIterator();
        while (iterator.hasNext()) {
            T obj = iterator.next();
            result.add(obj);
        }

        return result;
    }

    // TODO write description
    public static Object getFieldValue(Object obj, Field field) {
        try {
            return tryGetFieldValue(obj, field);
        }
        catch (IllegalAccessException e) {
            log.debug(e.getMessage());
            return null;
        }
    }

    // TODO make public and write description
    private static Object tryGetFieldValue(Object obj, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        return field.get(obj);
    }

    // TODO write description
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
                    || (field.isAnnotationPresent(OneToOne.class) && field.isAnnotationPresent(JoinColumn.class))
                    || field.isAnnotationPresent(Embedded.class)) {
                log.trace("Found dependency field: {}", field);
                result.add(field);
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
                    || field.isAnnotationPresent(ManyToMany.class)
                    || (field.isAnnotationPresent(OneToOne.class) && !field.isAnnotationPresent(JoinColumn.class))
                    || field.isAnnotationPresent(Embedded.class)) {
                log.trace("Found dependant field: {}", field);
                result.add(field);
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
