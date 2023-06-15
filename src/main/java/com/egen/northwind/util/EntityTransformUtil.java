package com.egen.northwind.util;


import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class EntityTransformUtil {

    public static <T> T copy(T entity) {
        Class<?> clazz = entity.getClass();
        T newEntity = null;
        try {
            newEntity = (T) entity.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        while (clazz != null) {
            try {
                copyField(entity, newEntity, clazz);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            clazz = clazz.getSuperclass();
        }
        return newEntity;
    }


    public static <X> List<X> copyList(List<X> list) {
        return list.parallelStream().map(e-> copyValueProp(e)).collect(Collectors.toList());
    }

    public static <T> T copyValueProp(T oldEntity) {
        Class<?> entityClazz = oldEntity.getClass();
        T newEntity = null;
        try {
            newEntity = (T) oldEntity.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        while (entityClazz != null) {
            try {
                copyField(oldEntity, newEntity, entityClazz);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            entityClazz = entityClazz.getSuperclass();
        }
        return newEntity;
    }

    public static <T> T copyField(T oldEntity, T newEntity, Class<?> entityClazz) throws IllegalAccessException {
        for (Field entityClazzField : entityClazz.getDeclaredFields()) {
            entityClazzField.setAccessible(true);
            if (getFieldType(entityClazzField).equals(ValueTypeEnum.ValueType)) {
                entityClazzField.set(newEntity, entityClazzField.get(oldEntity));
            }
            if (getFieldType(entityClazzField).equals(ValueTypeEnum.ListType)) {
                entityClazzField.set(newEntity, List.of());
            }
            if (getFieldType(entityClazzField).equals(ValueTypeEnum.EntityType)) {
                entityClazzField.set(newEntity, null);
            }
        }
        return newEntity;
    }

    public static <T> ValueTypeEnum getFieldType(Field entityClazzField) {
        if (
                entityClazzField.getType().isAssignableFrom(Integer.class) ||
                        entityClazzField.getType().isAssignableFrom(String.class) ||
                        entityClazzField.getType().isAssignableFrom(Boolean.class) ||
                        entityClazzField.getType().isAssignableFrom(LocalDateTime.class) ||
                        entityClazzField.getType().isAssignableFrom(LocalDate.class) ||
                        entityClazzField.getType().isAssignableFrom(LocalTime.class) ||
                        entityClazzField.getType().isAssignableFrom(Enum.class)
        ) {
            return ValueTypeEnum.ValueType;
        }
        if (entityClazzField.getType().isAssignableFrom(List.class)) {
            return ValueTypeEnum.ListType;
        }

        if (entityClazzField.getType().isAnnotationPresent(Entity.class)) {
            return ValueTypeEnum.EntityType;
        }
        return ValueTypeEnum.UnknownType;
    }

    enum ValueTypeEnum {
        ValueType,
        ListType,
        EntityType,
        UnknownType;
    }

}