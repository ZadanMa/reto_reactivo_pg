package com.reto_reactivo.capacidad.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.util.Set;

@WritingConverter
public class SetLongToJsonConverter implements Converter<Set<Long>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convert(Set<Long> source) {
        try {
            return objectMapper.writeValueAsString(source);
        } catch (Exception e) {
            throw new RuntimeException("Error converting Set<Long> to JSON", e);
        }
    }
}
