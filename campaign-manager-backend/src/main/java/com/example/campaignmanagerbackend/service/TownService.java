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
public class TownService implements InitializingBean {

    private List<String> towns;

    @Override
    public void afterPropertiesSet() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<String>> typeReference = new TypeReference<>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/towns.json");
        try {
            towns = mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load towns data", e);
        }
    }

    public List<String> searchTowns(String query) {
        return towns.stream()
                .filter(town -> town.toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
