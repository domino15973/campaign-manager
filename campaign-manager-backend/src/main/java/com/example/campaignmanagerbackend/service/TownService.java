package com.example.campaignmanagerbackend.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TownService {

    private final List<String> towns = Arrays.asList("Krakow", "Warsaw", "Katowice", "Poznan", "Gdansk");

    public List<String> searchTowns(String query) {
        return towns.stream()
                .filter(town -> town.toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
