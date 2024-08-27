package com.example.campaignmanagerbackend.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeywordService {

    private final List<String> keywords = Arrays.asList("Electronics", "Fashion", "Books", "Fitness", "Toys");

    public List<String> searchKeywords(String query) {
        return keywords.stream()
                .filter(keyword -> keyword.toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
