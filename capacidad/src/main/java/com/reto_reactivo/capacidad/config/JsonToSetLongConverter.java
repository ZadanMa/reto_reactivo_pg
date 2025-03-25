package com.reto_reactivo.capacidad.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.Set;

@ReadingConverter
public class JsonToSetLongConverter implements Converter<String, Set<Long>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Set<Long> convert(String source) {
        try {
            return objectMapper.readValue(source, new TypeReference<Set<Long>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error converting JSON to Set<Long>", e);
        }
    }
}
