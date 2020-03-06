package com.benjamin.manga.util;

import com.benjamin.manga.model.AbstractModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;

import java.lang.reflect.Field;
import java.util.Collection;

public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String castFromObject(Object obj){
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static <T extends AbstractModel> String castFromEntity(T entity, Collection<String> fields){
        try {
            Field[] currentField = entity.getClass().getDeclaredFields();
            for (Field field: currentField){
                field.setAccessible(true);
                if (fields.contains(field.getName())){
                    field.set(entity, null);
                }
            }
            return OBJECT_MAPPER.writeValueAsString(entity);
        } catch (JsonProcessingException | IllegalAccessException e) {
            return null;
        }
    }

    public static  <T extends AbstractModel> String castFromEntities(Page<T> entities){
        try {
            return OBJECT_MAPPER.writeValueAsString(entities);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
