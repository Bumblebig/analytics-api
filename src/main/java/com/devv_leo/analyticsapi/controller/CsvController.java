package com.devv_leo.analyticsapi.controller;

import com.devv_leo.analyticsapi.model.MerchantEvent;
import com.devv_leo.analyticsapi.service.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CsvController {

    private final CsvService csvService;

    @Autowired
    public CsvController(CsvService csvService) {
        this.csvService = csvService;
    }

    @GetMapping("/events")
    public List<MerchantEvent> getAllEvents() {
        return csvService.readAllCsvs();
    }
}