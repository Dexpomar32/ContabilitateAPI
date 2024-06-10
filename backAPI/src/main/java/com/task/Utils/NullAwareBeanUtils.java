package com.task.Utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyAccessorFactory;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class NullAwareBeanUtils {
    public static void copyNonNullProperties(Object source, Object target) {
        var targetAccessor = PropertyAccessorFactory.forBeanPropertyAccess(target);
        var targetPd = targetAccessor.getPropertyDescriptors();

        for (var pd : targetPd) {
            try {
                var value = PropertyAccessorFactory.forBeanPropertyAccess(source).getPropertyValue(pd.getName());
                if (value != null) {
                    BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
                }
            } catch (BeansException ignored) {}
        }
    }

    private static String[] getNullPropertyNames(Object source) {
        Set<String> emptyNames = new HashSet<>();
        Field[] allFields = source.getClass().getDeclaredFields();
        for (Field field : allFields) {
            field.setAccessible(true);
            try {
                Object value = field.get(source);
                if (value == null) {
                    emptyNames.add(field.getName());
                }
            } catch (IllegalAccessException ignored) {}
        }
        return emptyNames.toArray(new String[0]);
    }
}

