package com.example.campaignmanagerbackend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeywordService implements InitializingBean {

    private List<String> keywords;

    @Override
    public void afterPropertiesSet() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<String>> typeReference = new TypeReference<>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/keywords.json");
        try {
            keywords = mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load keywords data", e);
        }
    }

    public List<String> searchKeywords(String query) {
        return keywords.stream()
                .filter(keyword -> keyword.toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
