package ru.swe.skywingsexpressserver.utils;

import jakarta.persistence.Entity;
import org.springframework.stereotype.Component;
import ru.swe.skywingsexpressserver.dto.AirlineDto;
import ru.swe.skywingsexpressserver.dto.route.RouteDto;
import ru.swe.skywingsexpressserver.model.AirlineModel;
import ru.swe.skywingsexpressserver.model.operator.RouteModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class DtoModelMapper {
    public <E, D> D transform(E entity, Class<D> dtoClass) {
        try {
            if (dtoClass.isRecord()) {
                return createRecordInstance(entity, dtoClass);
            } else {
                D dto = createInstance(dtoClass);
                mapFields(entity, dto);
                return dto;
            }
        } catch (Exception e) {
            throw new RuntimeException("Mapping error", e);
        }
    }

    private <T> T createInstance(Class<T> clazz) throws Exception {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private <T> T createRecordInstance(Object source, Class<T> recordClass) throws Exception {
        Map<String, Object> fieldValues = new HashMap<>();
        for (Field sourceField : source.getClass().getDeclaredFields()) {
            sourceField.setAccessible(true);
            Object value = sourceField.get(source);
            if (isEntityField(sourceField)) {
                // Если это вложенная сущность, рекурсивно мапим ее на соответствующий DTO
                Class<?> nestedDtoClass = getDtoClassForEntity(sourceField.getType());
                if (nestedDtoClass != null) {
                    Object nestedDto = transform(value, nestedDtoClass);
                    fieldValues.put(sourceField.getName(), nestedDto);
                } else {
                    setNestedEntityId(source, sourceField, fieldValues);
                }
            } else {
                fieldValues.put(sourceField.getName(), value);
            }
        }

        Constructor<?>[] constructors = recordClass.getDeclaredConstructors();
        Constructor<?> constructor = constructors[0];
        constructor.setAccessible(true);

        Object[] params = new Object[constructor.getParameterCount()];
        int i = 0;
        for (Field recordField : recordClass.getDeclaredFields()) {
            params[i++] = fieldValues.get(recordField.getName());
        }

        return (T) constructor.newInstance(params);
    }

    private Class<?> getDtoClassForEntity(Class<?> entityClass) {
        // Возвращаем соответствующий класс DTO для заданного класса сущности
        if (entityClass.equals(AirlineModel.class)) {
            return AirlineDto.class;
        } else if (entityClass.equals(RouteModel.class)) {
            return RouteDto.class;
        }
        return null;
    }

    private <E, D> void mapFields(E source, D destination) throws Exception {
        for (Field sourceField : source.getClass().getDeclaredFields()) {
            sourceField.setAccessible(true);
            Optional<Field> destinationFieldOpt = getDestinationField(destination, sourceField);
            if (destinationFieldOpt.isPresent()) {
                Field destinationField = destinationFieldOpt.get();
                destinationField.setAccessible(true);
                destinationField.set(destination, sourceField.get(source));
            } else if (isEntityField(sourceField)) {
                setNestedEntityId(source, sourceField, destination);
            }
        }
    }

    private Optional<Field> getDestinationField(Object destination, Field sourceField) {
        try {
            return Optional.of(destination.getClass().getDeclaredField(sourceField.getName()));
        } catch (NoSuchFieldException e) {
            return Optional.empty();
        }
    }

    private void setNestedEntityId(Object source, Field sourceField, Object destination) throws Exception {
        Object nestedEntity = sourceField.get(source);
        if (nestedEntity != null) {
            Field idField = nestedEntity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            try {
                Class<?> destinationField = destination.getClass();
                var temp = destinationField.getDeclaredField(sourceField.getName() + "Id");
                temp.setAccessible(true);
                temp.set(destination, idField.get(nestedEntity));
            } catch (NoSuchFieldException e) {
                // No field found, skipping
            }
        }
    }

    private void setNestedEntityId(Object source, Field sourceField, Map<String, Object> fieldValues) throws Exception {
        Object nestedEntity = sourceField.get(source);
        if (nestedEntity != null) {
            Field idField = nestedEntity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            fieldValues.put(sourceField.getName() + "Id", idField.get(nestedEntity));
        }
    }

    private boolean isEntityField(Field field) {
        return field.getType().isAnnotationPresent(Entity.class);
    }
}
