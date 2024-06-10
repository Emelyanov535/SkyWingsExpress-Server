package ru.swe.skywingsexpressserver.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JsonConverter {
    private final ObjectMapper objectMapper;
    public <T> T convertStringToClass(String json, Class<T> clazz){
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
