package com.reto_reactivo.bootcampeventos.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import java.util.List;

@WritingConverter
public class ListLongToJsonConverter implements Converter<List<Long>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convert(List<Long> source) {
        try {
            return objectMapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing List<Long> to JSON", e);
        }
    }
}

@ReadingConverter
class JsonToListLongConverter implements Converter<String, List<Long>> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Long> convert(String source) {
        try {
            return objectMapper.readValue(source, new TypeReference<List<Long>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing JSON to List<Long>", e);
        }
    }
}

@WritingConverter
class ListStringToJsonConverter implements Converter<List<String>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convert(List<String> source) {
        try {
            return objectMapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing List<String> to JSON", e);
        }
    }
}

@ReadingConverter
class JsonToListStringConverter implements Converter<String, List<String>> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<String> convert(String source) {
        try {
            return objectMapper.readValue(source, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing JSON to List<String>", e);
        }
    }
}
