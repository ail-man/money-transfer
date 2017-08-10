package com.ail.revolut.app.jpa;

import java.lang.reflect.Field;
import java.util.Set;

public class EntityMetaInfo {

    private final Class<?> entityClass;
    private final Set<Field> dependencyFieldSet;
    private final Set<Field> dependantsFieldSet;

    public EntityMetaInfo(Class<?> entityClass, Set<Field> dependencyFieldSet, Set<Field> dependantsFieldSet) {
        this.entityClass = entityClass;
        this.dependencyFieldSet = dependencyFieldSet;
        this.dependantsFieldSet = dependantsFieldSet;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public Set<Field> getDependencyFieldSet() {
        return dependencyFieldSet;
    }

    public Set<Field> getDependantsFieldSet() {
        return dependantsFieldSet;
    }
}
