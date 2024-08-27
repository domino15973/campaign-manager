package com.example.campaignmanagerbackend.controller;

import com.example.campaignmanagerbackend.service.TownService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/towns")
public class TownController {

    private final TownService townService;

    public TownController(TownService townService) {
        this.townService = townService;
    }

    @GetMapping
    public List<String> getTowns(@RequestParam String query) {
        return townService.searchTowns(query);
    }
}
